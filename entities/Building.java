package com.isil.strategygame.entities;

import com.isil.strategygame.resources.ResourceType;
import com.isil.strategygame.utils.Position;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe abstraite représentant un bâtiment
 */
public abstract class Building {
    protected String nom;
    protected Position position;
    protected String owner;
    protected Map<ResourceType, Integer> cout;
    protected int tempsConstruction;
    protected int tempsRestant;
    protected boolean isBuilt;
    
    public Building(String nom, int tempsConstruction, String owner) {
        this.nom = nom;
        this.tempsConstruction = tempsConstruction;
        this.tempsRestant = tempsConstruction;
        this.owner = owner;
        this.cout = new HashMap<>();
        this.isBuilt = false;
    }
    
    public abstract char getSymbol();
    public abstract String getType();
    
    /**
     * Méthode appelée à chaque tour pour gérer la production/fonction du bâtiment
     */
    public abstract void processTurn();
    
    /**
     * Avance la construction du bâtiment
     */
    public void advanceConstruction() {
        if (!isBuilt) {
            tempsRestant--;
            if (tempsRestant <= 0) {
                isBuilt = true;
                tempsRestant = 0;
            }
        }
    }
    
    // Getters et Setters
    public String getNom() {
        return nom;
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
    
    public int getTempsConstruction() {
        return tempsConstruction;
    }
    
    public int getTempsRestant() {
        return tempsRestant;
    }
    
    public boolean isBuilt() {
        return isBuilt;
    }
    
    @Override
    public String toString() {
        if (isBuilt) {
            return nom + " (Construit)";
        } else {
            return nom + " (Construction: " + tempsRestant + " tours restants)";
        }
    }
}
