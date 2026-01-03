package com.isil.strategygame.entities;

import com.isil.strategygame.resources.ResourceType;

/**
 * Unité à distance avec grande portée mais faible défense
 */
public class Archer extends Unit {
    
    public Archer(String owner) {
        super("Archer", 70, 20, 5, 3, owner);
        
        // Définition du coût
        cout.put(ResourceType.OR, 40);
        cout.put(ResourceType.BOIS, 30);
        cout.put(ResourceType.NOURRITURE, 15);
    }
    
    @Override
    public char getSymbol() {
        return 'A';
    }
    
    @Override
    public String getType() {
        return "Archer";
    }
}
