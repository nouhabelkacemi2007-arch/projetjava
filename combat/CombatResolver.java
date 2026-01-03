package com.isil.strategygame.combat;

import com.isil.strategygame.entities.Unit;
import com.isil.strategygame.map.Tile;
import java.util.Random;

/**
 * Gère la résolution des combats entre unités
 */
public class CombatResolver {
    private static final Random random = new Random();
    
    /**
     * Résout un combat entre deux unités
     * @param attacker L'unité attaquante
     * @param defender L'unité défenseur
     * @param defenderTile La case où se trouve le défenseur (pour les bonus)
     * @return true si le défenseur est mort, false sinon
     */
    public static boolean resolveCombat(Unit attacker, Unit defender, Tile defenderTile) {
        if (attacker == null || defender == null) {
            return false;
        }
        
        // Vérification que l'attaquant peut attaquer
        if (attacker.hasAttacked()) {
            System.out.println("Cette unité a déjà attaqué ce tour !");
            return false;
        }
        
        // Calcul des dégâts de base
        int baseDamage = attacker.getAttaque();
        
        // Facteur aléatoire (80% à 120% des dégâts)
        double randomFactor = 0.8 + (random.nextDouble() * 0.4);
        int damage = (int)(baseDamage * randomFactor);
        
        // Réduction par la défense
        int defenseValue = defender.getDefense();
        
        // Bonus de terrain
        if (defenderTile != null) {
            int terrainBonus = defenderTile.getDefenseBonus();
            defenseValue += (defenseValue * terrainBonus) / 100;
        }
        
        // Calcul des dégâts finaux
        damage = Math.max(damage - defenseValue, 1); // Minimum 1 dégât
        
        // Application des dégâts
        defender.takeDamage(damage);
        
        // Affichage du résultat
        System.out.println("\n=== COMBAT ===");
        System.out.println(attacker.getNom() + " attaque " + defender.getNom());
        System.out.println("Dégâts infligés: " + damage);
        System.out.println(defender.getNom() + " PV: " + defender.getPointsDeVie() + "/" + defender.getPointsDeVieMax());
        
        // Marquer l'attaquant comme ayant attaqué
        attacker.setHasAttacked(true);
        
        // Vérifier si le défenseur est mort
        if (!defender.isAlive()) {
            System.out.println(defender.getNom() + " a été vaincu !");
            return true;
        }
        
        return false;
    }
    
    /**
     * Vérifie si une unité peut attaquer une autre selon la portée
     */
    public static boolean canAttack(Unit attacker, Unit target) {
        if (attacker == null || target == null) {
            return false;
        }
        
        if (attacker.getOwner().equals(target.getOwner())) {
            return false; // Ne peut pas attaquer ses propres unités
        }
        
        if (attacker.hasAttacked()) {
            return false; // A déjà attaqué ce tour
        }
        
        int distance = attacker.getPosition().distanceTo(target.getPosition());
        return distance <= attacker.getPortee();
    }
}
