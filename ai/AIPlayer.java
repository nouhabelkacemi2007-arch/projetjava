package com.isil.strategygame.ai;

import com.isil.strategygame.core.Player;
import com.isil.strategygame.entities.*;
import com.isil.strategygame.map.GameMap;
import com.isil.strategygame.map.Tile;
import com.isil.strategygame.combat.CombatResolver;
import com.isil.strategygame.utils.Position;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Intelligence artificielle pour l'adversaire
 */
public class AIPlayer {
    private final Player player;
    private final GameMap map;
    private final Random random;
    
    public AIPlayer(Player player, GameMap map) {
        this.player = player;
        this.map = map;
        this.random = new Random();
    }
    
    /**
     * Exécute le tour de l'IA
     */
    public void playTurn(Player opponent) {
        System.out.println("\n=== Tour de l'IA ===");
        
        // 1. Construire des bâtiments si possible
        tryBuildBuildings();
        
        // 2. Entraîner des unités si possible
        tryTrainUnits();
        
        // 3. Déplacer et attaquer avec les unités
        moveAndAttackUnits(opponent);
    }
    
    private void tryBuildBuildings() {
        // L'IA essaie de construire un bâtiment aléatoire si elle a assez de ressources
        if (random.nextInt(100) < 30) { // 30% de chance de construire
            int buildingType = random.nextInt(4);
            Building building = null;
            
            switch (buildingType) {
                case 0:
                    building = new Mine(player.getNom(), player.getResourceManager());
                    break;
                case 1:
                    building = new Ferme(player.getNom(), player.getResourceManager());
                    break;
                case 2:
                    building = new Scierie(player.getNom(), player.getResourceManager());
                    break;
                case 3:
                    building = new CampEntrainement(player.getNom());
                    break;
            }
            
            if (building != null && player.getResourceManager().hasResources(building.getCout())) {
                // Trouver une position libre
                Position pos = findFreePosition();
                if (pos != null) {
                    Tile tile = map.getTile(pos);
                    if (tile.getType().isAccessible() && !tile.hasBuilding()) {
                        player.getResourceManager().consumeResources(building.getCout());
                        building.setPosition(pos);
                        tile.setBuilding(building);
                        player.addBuilding(building);
                        System.out.println("IA construit: " + building.getNom());
                    }
                }
            }
        }
    }
    
    private void tryTrainUnits() {
        // Vérifier si l'IA a un camp d'entraînement
        boolean hasBarracks = false;
        for (Building building : player.getBuildings()) {
            if (building instanceof CampEntrainement && building.isBuilt()) {
                hasBarracks = true;
                break;
            }
        }
        
        if (!hasBarracks) {
            return;
        }
        
        // L'IA essaie d'entraîner une unité si elle a assez de ressources
        if (random.nextInt(100) < 40) { // 40% de chance d'entraîner
            int unitType = random.nextInt(3);
            Unit unit = null;
            
            switch (unitType) {
                case 0:
                    unit = new Soldat(player.getNom());
                    break;
                case 1:
                    unit = new Archer(player.getNom());
                    break;
                case 2:
                    unit = new Cavalier(player.getNom());
                    break;
            }
            
            if (unit != null && player.getResourceManager().hasResources(unit.getCout())) {
                Position pos = findFreePosition();
                if (pos != null) {
                    Tile tile = map.getTile(pos);
                    if (tile.isAccessible()) {
                        player.getResourceManager().consumeResources(unit.getCout());
                        unit.setPosition(pos);
                        tile.setUnit(unit);
                        player.addUnit(unit);
                        System.out.println("IA entraîne: " + unit.getType());
                    }
                }
            }
        }
    }
    
    private void moveAndAttackUnits(Player opponent) {
        for (Unit unit : player.getUnits()) {
            // Chercher un ennemi à proximité
            Unit target = findNearestEnemy(unit, opponent);
            
            if (target != null) {
                // Essayer d'attaquer si à portée
                if (CombatResolver.canAttack(unit, target)) {
                    Tile targetTile = map.getTile(target.getPosition());
                    boolean killed = CombatResolver.resolveCombat(unit, target, targetTile);
                    
                    if (killed) {
                        targetTile.removeUnit();
                        opponent.removeUnit(target);
                    }
                } else if (!unit.hasMoved()) {
                    // Sinon, se déplacer vers l'ennemi
                    moveTowards(unit, target.getPosition());
                }
            } else if (!unit.hasMoved()) {
                // Déplacement aléatoire si pas d'ennemi
                randomMove(unit);
            }
        }
    }
    
    private Unit findNearestEnemy(Unit myUnit, Player opponent) {
        Unit nearest = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (Unit enemyUnit : opponent.getUnits()) {
            int distance = myUnit.getPosition().distanceTo(enemyUnit.getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = enemyUnit;
            }
        }
        
        return nearest;
    }
    
    private void moveTowards(Unit unit, Position target) {
        Position current = unit.getPosition();
        List<Tile> neighbors = map.getNeighbors(current);
        
        Tile bestTile = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (Tile neighbor : neighbors) {
            if (neighbor.isAccessible()) {
                int distance = neighbor.getPosition().distanceTo(target);
                if (distance < minDistance) {
                    minDistance = distance;
                    bestTile = neighbor;
                }
            }
        }
        
        if (bestTile != null) {
            Tile currentTile = map.getTile(current);
            currentTile.removeUnit();
            bestTile.setUnit(unit);
            unit.setPosition(bestTile.getPosition());
            unit.setHasMoved(true);
        }
    }
    
    private void randomMove(Unit unit) {
        Position current = unit.getPosition();
        List<Tile> neighbors = map.getNeighbors(current);
        
        // Filtrer les voisins accessibles
        List<Tile> accessible = new ArrayList<>();
        for (Tile neighbor : neighbors) {
            if (neighbor.isAccessible()) {
                accessible.add(neighbor);
            }
        }
        
        if (!accessible.isEmpty()) {
            Tile randomTile = accessible.get(random.nextInt(accessible.size()));
            Tile currentTile = map.getTile(current);
            currentTile.removeUnit();
            randomTile.setUnit(unit);
            unit.setPosition(randomTile.getPosition());
            unit.setHasMoved(true);
        }
    }
    
    private Position findFreePosition() {
        // Chercher une position libre pour construire/placer une unité
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(map.getWidth());
            int y = random.nextInt(map.getHeight());
            Position pos = new Position(x, y);
            Tile tile = map.getTile(pos);
            
            if (tile.getType().isAccessible() && !tile.hasUnit() && !tile.hasBuilding()) {
                return pos;
            }
        }
        return null;
    }
}
