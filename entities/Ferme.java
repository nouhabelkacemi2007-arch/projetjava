package com.isil.strategygame.entities;

import com.isil.strategygame.resources.ResourceType;
import com.isil.strategygame.resources.ResourceManager;

/**
 * Bâtiment de production de nourriture
 */
public class Ferme extends Building {
    private ResourceManager resourceManager;
    private final int productionNourriture = 20;
    
    public Ferme(String owner, ResourceManager resourceManager) {
        super("Ferme", 1, owner);
        this.resourceManager = resourceManager;
        
        // Définition du coût
        cout.put(ResourceType.OR, 30);
        cout.put(ResourceType.BOIS, 25);
    }
    
    @Override
    public char getSymbol() {
        return 'F';
    }
    
    @Override
    public String getType() {
        return "Ferme";
    }
    
    @Override
    public void processTurn() {
        if (isBuilt) {
            resourceManager.addResource(ResourceType.NOURRITURE, productionNourriture);
        }
    }
}
