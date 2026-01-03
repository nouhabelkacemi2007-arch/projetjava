package com.isil.strategygame.entities;

import com.isil.strategygame.resources.ResourceType;

/**
 * Unité de mêlée basique avec bonne défense
 */
public class Soldat extends Unit {
    
    public Soldat(String owner) {
        super("Soldat", 100, 15, 10, 1, owner);
        
        // Définition du coût
        cout.put(ResourceType.OR, 50);
        cout.put(ResourceType.NOURRITURE, 20);
    }
    
    @Override
    public char getSymbol() {
        return 'S';
    }
    
    @Override
    public String getType() {
        return "Soldat";
    }
}
