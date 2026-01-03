package com.isil.strategygame.entities;

import com.isil.strategygame.resources.ResourceType;

/**
 * Bâtiment principal du joueur
 */
public class CentreCommandement extends Building {
    
    public CentreCommandement(String owner) {
        super("Centre de Commandement", 3, owner);
        
        // Définition du coût
        cout.put(ResourceType.OR, 100);
        cout.put(ResourceType.BOIS, 80);
        cout.put(ResourceType.PIERRE, 60);
    }
    
    @Override
    public char getSymbol() {
        return 'H'; // H pour Headquarters
    }
    
    @Override
    public String getType() {
        return "Centre de Commandement";
    }
    
    @Override
    public void processTurn() {
        // Le centre de commandement ne produit rien mais est nécessaire
    }
}
