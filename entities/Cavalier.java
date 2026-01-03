package com.isil.strategygame.entities;

import com.isil.strategygame.resources.ResourceType;

/**
 * Unité rapide et puissante mais coûteuse
 */
public class Cavalier extends Unit {
    
    public Cavalier(String owner) {
        super("Cavalier", 120, 25, 8, 1, owner);
        
        // Définition du coût
        cout.put(ResourceType.OR, 80);
        cout.put(ResourceType.NOURRITURE, 40);
    }
    
    @Override
    public char getSymbol() {
        return 'C';
    }
    
    @Override
    public String getType() {
        return "Cavalier";
    }
}
