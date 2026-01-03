package com.isil.strategygame.entities;

import com.isil.strategygame.resources.ResourceType;
import com.isil.strategygame.utils.Position;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe abstraite représentant une unité militaire
 */
public abstract class Unit {
    protected String nom;
    protected int pointsDeVie;
    protected int pointsDeVieMax;
    protected int attaque;
    protected int defense;
    protected int portee;
    protected Position position;
    protected String owner; // Propriétaire de l'unité
    protected Map<ResourceType, Integer> cout;
    protected boolean hasMoved; // A déjà bougé ce tour
    protected boolean hasAttacked; // A déjà attaqué ce tour
    
    public Unit(String nom, int pointsDeVie, int attaque, int defense, int portee, String owner) {
        this.nom = nom;
        this.pointsDeVie = pointsDeVie;
        this.pointsDeVieMax = pointsDeVie;
        this.attaque = attaque;
        this.defense = defense;
        this.portee = portee;
        this.owner = owner;
        this.cout = new HashMap<>();
        this.hasMoved = false;
        this.hasAttacked = false;
    }
    
    public abstract char getSymbol();
    public abstract String getType();
    
    public void takeDamage(int damage) {
        pointsDeVie -= damage;
        if (pointsDeVie < 0) {
            pointsDeVie = 0;
        }
    }
    
    public void heal(int amount) {
        pointsDeVie += amount;
        if (pointsDeVie > pointsDeVieMax) {
            pointsDeVie = pointsDeVieMax;
        }
    }
    
    public boolean isAlive() {
        return pointsDeVie > 0;
    }
    
    public void resetTurn() {
        hasMoved = false;
        hasAttacked = false;
    }
    
    // Getters et Setters
    public String getNom() {
        return nom;
    }
    
    public int getPointsDeVie() {
        return pointsDeVie;
    }
    
    public int getPointsDeVieMax() {
        return pointsDeVieMax;
    }
    
    public int getAttaque() {
        return attaque;
    }
    
    public int getDefense() {
        return defense;
    }
    
    public int getPortee() {
        return portee;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public String getOwner() {
        return owner;
    }
    
    public Map<ResourceType, Integer> getCout() {
        return new HashMap<>(cout);
    }
    
    public boolean hasMoved() {
        return hasMoved;
    }
    
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    
    public boolean hasAttacked() {
        return hasAttacked;
    }
    
    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s) - PV: %d/%d, ATK: %d, DEF: %d, Portée: %d",
            nom, getType(), pointsDeVie, pointsDeVieMax, attaque, defense, portee);
    }
}
