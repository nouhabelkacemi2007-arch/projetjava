package com.isil.strategygame.map;

/**
 * Types de cases sur la carte
 */
public enum TileType {
    GRASS("Herbe", true, 0, 0),
    WATER("Eau", false, 0, 0),
    MOUNTAIN("Montagne", true, 1, -1),
    FOREST("Forêt", true, 0, 1);
    
    private final String nom;
    private final boolean accessible;
    private final int defenseBonusPercent; // Bonus de défense en pourcentage
    private final int movementCost; // Coût de mouvement supplémentaire
    
    TileType(String nom, boolean accessible, int defenseBonusPercent, int movementCost) {
        this.nom = nom;
        this.accessible = accessible;
        this.defenseBonusPercent = defenseBonusPercent;
        this.movementCost = movementCost;
    }
    
    public String getNom() {
        return nom;
    }
    
    public boolean isAccessible() {
        return accessible;
    }
    
    public int getDefenseBonusPercent() {
        return defenseBonusPercent;
    }
    
    public int getMovementCost() {
        return movementCost;
    }
    
    public char getSymbol() {
        switch (this) {
            case GRASS: return '.';
            case WATER: return '~';
            case MOUNTAIN: return '^';
            case FOREST: return 'T';
            default: return '?';
        }
    }
}
