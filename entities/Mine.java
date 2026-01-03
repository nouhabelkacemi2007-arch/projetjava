package com.isil.strategygame.entities;

import com.isil.strategygame.resources.ResourceType;
import com.isil.strategygame.resources.ResourceManager;

/**
 * Bâtiment de production d'or et de pierre
 */
public class Mine extends Building {
    private ResourceManager resourceManager;
    private final int productionOr = 15;
    private final int productionPierre = 10;
    
    public Mine(String owner, ResourceManager resourceManager) {
        super("Mine", 2, owner);
        this.resourceManager = resourceManager;
        
        // Définition du coût
        cout.put(ResourceType.OR, 40);
        cout.put(ResourceType.BOIS, 20);
    }
    
    @Override
    public char getSymbol() {
        return 'M';
    }
    
    @Override
    public String getType() {
        return "Mine";
    }
    
    @Override
    public void processTurn() {
        if (isBuilt) {
            resourceManager.addResource(ResourceType.OR, productionOr);
            resourceManager.addResource(ResourceType.PIERRE, productionPierre);
        }
    }
}
