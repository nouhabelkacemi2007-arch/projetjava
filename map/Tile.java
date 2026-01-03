package com.isil.strategygame.map;

import com.isil.strategygame.entities.Building;
import com.isil.strategygame.entities.Unit;
import com.isil.strategygame.utils.Position;

/**
 * Représente une case sur la carte de jeu
 */
public class Tile {
    private final Position position;
    private final TileType type;
    private Unit unit; // Unité présente sur la case
    private Building building; // Bâtiment présent sur la case
    private String owner; // Propriétaire de la case (null si neutre)
    
    public Tile(Position position, TileType type) {
        this.position = position;
        this.type = type;
        this.unit = null;
        this.building = null;
        this.owner = null;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public TileType getType() {
        return type;
    }
    
    public boolean isAccessible() {
        return type.isAccessible() && unit == null;
    }
    
    public Unit getUnit() {
        return unit;
    }
    
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    public void removeUnit() {
        this.unit = null;
    }
    
    public boolean hasUnit() {
        return unit != null;
    }
    
    public Building getBuilding() {
        return building;
    }
    
    public void setBuilding(Building building) {
        this.building = building;
    }
    
    public boolean hasBuilding() {
        return building != null;
    }
    
    public String getOwner() {
        return owner;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public int getDefenseBonus() {
        return type.getDefenseBonusPercent();
    }
    
    @Override
    public String toString() {
        if (unit != null) {
            return "[" + unit.getSymbol() + "]";
        } else if (building != null) {
            return "[" + building.getSymbol() + "]";
        } else {
            return " " + type.getSymbol() + " ";
        }
    }
}
