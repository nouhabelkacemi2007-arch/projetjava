package com.isil.strategygame.resources;

/**
 * Types de ressources disponibles dans le jeu
 */
public enum ResourceType {
    OR("Or"),
    BOIS("Bois"),
    PIERRE("Pierre"),
    NOURRITURE("Nourriture");
    
    private final String nom;
    
    ResourceType(String nom) {
        this.nom = nom;
    }
    
    public String getNom() {
        return nom;
    }
    
    @Override
    public String toString() {
        return nom;
    }
}
