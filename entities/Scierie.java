package com.isil.strategygame.entities;

import com.isil.strategygame.resources.ResourceType;
import com.isil.strategygame.resources.ResourceManager;

/**
 * Bâtiment de production de bois
 */
public class Scierie extends Building {
    private ResourceManager resourceManager;
    private final int productionBois = 18;
    
    public Scierie(String owner, ResourceManager resourceManager) {
        super("Scierie", 1, owner);
        this.resourceManager = resourceManager;
        
        // Définition du coût
        cout.put(ResourceType.OR, 35);
        cout.put(ResourceType.PIERRE, 20);
    }
    
    @Override
    public char getSymbol() {
        return 'W'; // W pour Woodmill
    }
    
    @Override
    public String getType() {
        return "Scierie";
    }
    
    @Override
    public void processTurn() {
        if (isBuilt) {
            resourceManager.addResource(ResourceType.BOIS, productionBois);
        }
    }
}
