package com.isil.strategygame.core;

import com.isil.strategygame.ai.AIPlayer;
import com.isil.strategygame.combat.CombatResolver;
import com.isil.strategygame.entities.*;
import com.isil.strategygame.map.GameMap;
import com.isil.strategygame.map.Tile;
import com.isil.strategygame.resources.ResourceType;
import com.isil.strategygame.utils.Position;

import java.util.List;
import java.util.Scanner;

/**
 * Classe principale du jeu qui gère la boucle de jeu
 */
public class Game {
    private final GameMap map;
    private final Player humanPlayer;
    private final Player aiPlayer;
    private final AIPlayer ai;
    private final Scanner scanner;
    private int turnNumber;
    private boolean gameRunning;
    
    public Game() {
        this.map = new GameMap(15, 15);
        this.humanPlayer = new Player("Joueur", false);
        this.aiPlayer = new Player("IA", true);
        this.ai = new AIPlayer(aiPlayer, map);
        this.scanner = new Scanner(System.in);
        this.turnNumber = 1;
        this.gameRunning = true;
        
        initializeGame();
    }
    
    /**
     * Initialise le jeu avec les unités et bâtiments de départ
     */
    private void initializeGame() {
        // Position de départ du joueur (coin bas gauche)
        Position humanStart = new Position(2, 12);
        
        // Centre de commandement du joueur
        CentreCommandement humanHQ = new CentreCommandement(humanPlayer.getNom());
        humanHQ.setPosition(humanStart);
        map.getTile(humanStart).setBuilding(humanHQ);
        humanPlayer.addBuilding(humanHQ);
        
        // Unité de départ du joueur
        Soldat humanSoldier = new Soldat(humanPlayer.getNom());
        Position soldierPos = new Position(3, 12);
        humanSoldier.setPosition(soldierPos);
        map.getTile(soldierPos).setUnit(humanSoldier);
        humanPlayer.addUnit(humanSoldier);
        
        // Position de départ de l'IA (coin haut droit)
        Position aiStart = new Position(12, 2);
        
        // Centre de commandement de l'IA
        CentreCommandement aiHQ = new CentreCommandement(aiPlayer.getNom());
        aiHQ.setPosition(aiStart);
        map.getTile(aiStart).setBuilding(aiHQ);
        aiPlayer.addBuilding(aiHQ);
        
        // Unité de départ de l'IA
        Soldat aiSoldier = new Soldat(aiPlayer.getNom());
        Position aiSoldierPos = new Position(11, 2);
        aiSoldier.setPosition(aiSoldierPos);
        map.getTile(aiSoldierPos).setUnit(aiSoldier);
        aiPlayer.addUnit(aiSoldier);
    }
    
    /**
     * Boucle principale du jeu
     */
    public void run() {
        System.out.println("==============================================");
        System.out.println("       BIENVENUE DANS STRATEGY GAME           ");
        System.out.println("==============================================\n");
        
        while (gameRunning) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("TOUR " + turnNumber);
            System.out.println("=".repeat(50));
            
            // Tour du joueur
            humanPlayer.startTurn();
            playerTurn();
            
            // Vérifier la victoire
            if (checkVictory()) {
                break;
            }
            
            // Tour de l'IA
            aiPlayer.startTurn();
            ai.playTurn(humanPlayer);
            
            // Vérifier la victoire
            if (checkVictory()) {
                break;
            }
            
            turnNumber++;
        }
        
        scanner.close();
    }
    
    /**
     * Gère le tour du joueur humain
     */
    private void playerTurn() {
        boolean endTurn = false;
        
        while (!endTurn) {
            displayMenu();
            int choice = getIntInput("Votre choix: ");
            
            switch (choice) {
                case 1:
                    map.display();
                    break;
                case 2:
                    humanPlayer.displayStatus();
                    break;
                case 3:
                    moveUnit();
                    break;
                case 4:
                    attackWithUnit();
                    break;
                case 5:
                    buildBuilding();
                    break;
                case 6:
                    trainUnit();
                    break;
                case 7:
                    endTurn = true;
                    break;
                case 0:
                    gameRunning = false;
                    endTurn = true;
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        }
    }
    
    private void displayMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Afficher la carte");
        System.out.println("2. Voir mon statut");
        System.out.println("3. Déplacer une unité");
        System.out.println("4. Attaquer");
        System.out.println("5. Construire un bâtiment");
        System.out.println("6. Entraîner une unité");
        System.out.println("7. Terminer le tour");
        System.out.println("0. Quitter le jeu");
    }
    
    private void moveUnit() {
        List<Unit> units = humanPlayer.getUnits();
        if (units.isEmpty()) {
            System.out.println("Vous n'avez aucune unité !");
            return;
        }
        
        System.out.println("\nVos unités:");
        for (int i = 0; i < units.size(); i++) {
            Unit u = units.get(i);
            System.out.println(i + ". " + u + " à " + u.getPosition() +
                             (u.hasMoved() ? " [DÉPLACÉE]" : ""));
        }
        
        int unitIndex = getIntInput("Numéro de l'unité à déplacer: ");
        if (unitIndex < 0 || unitIndex >= units.size()) {
            System.out.println("Unité invalide !");
            return;
        }
        
        Unit unit = units.get(unitIndex);
        if (unit.hasMoved()) {
            System.out.println("Cette unité a déjà bougé ce tour !");
            return;
        }
        
        int x = getIntInput("Nouvelle position X: ");
        int y = getIntInput("Nouvelle position Y: ");
        Position newPos = new Position(x, y);
        
        if (!map.isValidPosition(newPos)) {
            System.out.println("Position invalide !");
            return;
        }
        
        // Vérifier la distance (pour simplifier, on autorise 1 case)
        if (unit.getPosition().distanceTo(newPos) != 1) {
            System.out.println("Vous ne pouvez vous déplacer que d'une case !");
            return;
        }
        
        Tile newTile = map.getTile(newPos);
        if (!newTile.isAccessible()) {
            System.out.println("Cette case n'est pas accessible !");
            return;
        }
        
        // Déplacer l'unité
        Tile oldTile = map.getTile(unit.getPosition());
        oldTile.removeUnit();
        newTile.setUnit(unit);
        unit.setPosition(newPos);
        unit.setHasMoved(true);
        
        System.out.println("Unité déplacée avec succès !");
    }
    
    private void attackWithUnit() {
        List<Unit> units = humanPlayer.getUnits();
        if (units.isEmpty()) {
            System.out.println("Vous n'avez aucune unité !");
            return;
        }
        
        System.out.println("\nVos unités:");
        for (int i = 0; i < units.size(); i++) {
            Unit u = units.get(i);
            System.out.println(i + ". " + u + " à " + u.getPosition() +
                             (u.hasAttacked() ? " [A ATTAQUÉ]" : ""));
        }
        
        int unitIndex = getIntInput("Numéro de l'unité qui attaque: ");
        if (unitIndex < 0 || unitIndex >= units.size()) {
            System.out.println("Unité invalide !");
            return;
        }
        
        Unit attacker = units.get(unitIndex);
        if (attacker.hasAttacked()) {
            System.out.println("Cette unité a déjà attaqué ce tour !");
            return;
        }
        
        List<Unit> enemies = aiPlayer.getUnits();
        if (enemies.isEmpty()) {
            System.out.println("Il n'y a pas d'ennemis !");
            return;
        }
        
        System.out.println("\nUnités ennemies:");
        for (int i = 0; i < enemies.size(); i++) {
            Unit e = enemies.get(i);
            System.out.println(i + ". " + e + " à " + e.getPosition());
        }
        
        int targetIndex = getIntInput("Numéro de la cible: ");
        if (targetIndex < 0 || targetIndex >= enemies.size()) {
            System.out.println("Cible invalide !");
            return;
        }
        
        Unit target = enemies.get(targetIndex);
        
        if (!CombatResolver.canAttack(attacker, target)) {
            System.out.println("Vous ne pouvez pas attaquer cette cible (hors de portée) !");
            return;
        }
        
        Tile targetTile = map.getTile(target.getPosition());
        boolean killed = CombatResolver.resolveCombat(attacker, target, targetTile);
        
        if (killed) {
            targetTile.removeUnit();
            aiPlayer.removeUnit(target);
        }
    }
    
    private void buildBuilding() {
        System.out.println("\nChoisissez un bâtiment:");
        System.out.println("1. Mine (Or: 40, Bois: 20)");
        System.out.println("2. Ferme (Or: 30, Bois: 25)");
        System.out.println("3. Scierie (Or: 35, Pierre: 20)");
        System.out.println("4. Camp d'Entraînement (Or: 60, Bois: 40)");
        
        int choice = getIntInput("Votre choix: ");
        Building building = null;
        
        switch (choice) {
            case 1:
                building = new Mine(humanPlayer.getNom(), humanPlayer.getResourceManager());
                break;
            case 2:
                building = new Ferme(humanPlayer.getNom(), humanPlayer.getResourceManager());
                break;
            case 3:
                building = new Scierie(humanPlayer.getNom(), humanPlayer.getResourceManager());
                break;
            case 4:
                building = new CampEntrainement(humanPlayer.getNom());
                break;
            default:
                System.out.println("Choix invalide !");
                return;
        }
        
        if (!humanPlayer.getResourceManager().hasResources(building.getCout())) {
            System.out.println("Ressources insuffisantes !");
            return;
        }
        
        int x = getIntInput("Position X: ");
        int y = getIntInput("Position Y: ");
        Position pos = new Position(x, y);
        
        if (!map.isValidPosition(pos)) {
            System.out.println("Position invalide !");
            return;
        }
        
        Tile tile = map.getTile(pos);
        if (!tile.getType().isAccessible() || tile.hasBuilding() || tile.hasUnit()) {
            System.out.println("Cette case n'est pas disponible pour construire !");
            return;
        }
        
        humanPlayer.getResourceManager().consumeResources(building.getCout());
        building.setPosition(pos);
        tile.setBuilding(building);
        humanPlayer.addBuilding(building);
        
        System.out.println("Construction de " + building.getNom() + " commencée !");
    }
    
    private void trainUnit() {
        // Vérifier si le joueur a un camp d'entraînement
        boolean hasBarracks = false;
        for (Building building : humanPlayer.getBuildings()) {
            if (building instanceof CampEntrainement && building.isBuilt()) {
                hasBarracks = true;
                break;
            }
        }
        
        if (!hasBarracks) {
            System.out.println("Vous devez d'abord construire un Camp d'Entraînement !");
            return;
        }
        
        System.out.println("\nChoisissez une unité:");
        System.out.println("1. Soldat (Or: 50, Nourriture: 20)");
        System.out.println("2. Archer (Or: 40, Bois: 30, Nourriture: 15)");
        System.out.println("3. Cavalier (Or: 80, Nourriture: 40)");
        
        int choice = getIntInput("Votre choix: ");
        Unit unit = null;
        
        switch (choice) {
            case 1:
                unit = new Soldat(humanPlayer.getNom());
                break;
            case 2:
                unit = new Archer(humanPlayer.getNom());
                break;
            case 3:
                unit = new Cavalier(humanPlayer.getNom());
                break;
            default:
                System.out.println("Choix invalide !");
                return;
        }
        
        if (!humanPlayer.getResourceManager().hasResources(unit.getCout())) {
            System.out.println("Ressources insuffisantes !");
            return;
        }
        
        int x = getIntInput("Position X: ");
        int y = getIntInput("Position Y: ");
        Position pos = new Position(x, y);
        
        if (!map.isValidPosition(pos)) {
            System.out.println("Position invalide !");
            return;
        }
        
        Tile tile = map.getTile(pos);
        if (!tile.isAccessible()) {
            System.out.println("Cette case n'est pas disponible !");
            return;
        }
        
        humanPlayer.getResourceManager().consumeResources(unit.getCout());
        unit.setPosition(pos);
        tile.setUnit(unit);
        humanPlayer.addUnit(unit);
        
        System.out.println(unit.getType() + " entraîné avec succès !");
    }
    
    private boolean checkVictory() {
        if (humanPlayer.getUnits().isEmpty() && aiPlayer.getUnits().isEmpty()) {
            System.out.println("\n=== MATCH NUL ===");
            gameRunning = false;
            return true;
        }
        
        boolean humanHasHQ = false;
        for (Building b : humanPlayer.getBuildings()) {
            if (b instanceof CentreCommandement) {
                humanHasHQ = true;
                break;
            }
        }
        
        boolean aiHasHQ = false;
        for (Building b : aiPlayer.getBuildings()) {
            if (b instanceof CentreCommandement) {
                aiHasHQ = true;
                break;
            }
        }
        
        if (!humanHasHQ || humanPlayer.getUnits().isEmpty()) {
            System.out.println("\n=== DÉFAITE ===");
            System.out.println("L'IA a gagné !");
            gameRunning = false;
            return true;
        }
        
        if (!aiHasHQ || aiPlayer.getUnits().isEmpty()) {
            System.out.println("\n=== VICTOIRE ===");
            System.out.println("Vous avez gagné !");
            gameRunning = false;
            return true;
        }
        
        return false;
    }
    
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Entrez un nombre valide: ");
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne
        return value;
    }
}
