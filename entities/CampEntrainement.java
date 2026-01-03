package com.isil.strategygame.entities;

import com.isil.strategygame.resources.ResourceType;

/**
 * Bâtiment permettant de créer des unités militaires
 */
public class CampEntrainement extends Building {
    
    public CampEntrainement(String owner) {
        super("Camp d'Entraînement", 2, owner);
        
        // Définition du coût
        cout.put(ResourceType.OR, 60);
        cout.put(ResourceType.BOIS, 40);
    }
    
    @Override
    public char getSymbol() {
        return 'B'; // B pour Barracks
    }
    
    @Override
    public String getType() {
        return "Camp d'Entraînement";
    }
    
    @Override
    public void processTurn() {
        // La création d'unités se fait manuellement par le joueur
    }
}
