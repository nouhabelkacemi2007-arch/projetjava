package com.isil.strategygame.core;

import com.isil.strategygame.entities.Building;
import com.isil.strategygame.entities.Unit;
import com.isil.strategygame.resources.ResourceManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un joueur dans le jeu
 */
public class Player {
    private final String nom;
    private final ResourceManager resourceManager;
    private final List<Unit> units;
    private final List<Building> buildings;
    private final boolean isAI;
    
    public Player(String nom, boolean isAI) {
        this.nom = nom;
        this.isAI = isAI;
        this.resourceManager = new ResourceManager();
        this.units = new ArrayList<>();
        this.buildings = new ArrayList<>();
    }
    
    public void addUnit(Unit unit) {
        units.add(unit);
    }
    
    public void removeUnit(Unit unit) {
        units.remove(unit);
    }
    
    public void addBuilding(Building building) {
        buildings.add(building);
    }
    
    public void removeBuilding(Building building) {
        buildings.remove(building);
    }
    
    /**
     * Exécute les actions de début de tour
     */
    public void startTurn() {
        // Réinitialiser les actions des unités
        for (Unit unit : units) {
            unit.resetTurn();
        }
        
        // Avancer la construction des bâtiments
        for (Building building : buildings) {
            if (!building.isBuilt()) {
                building.advanceConstruction();
                if (building.isBuilt()) {
                    System.out.println(building.getNom() + " a été construit !");
                }
            }
        }
        
        // Produire les ressources
        for (Building building : buildings) {
            building.processTurn();
        }
    }
    
    public String getNom() {
        return nom;
    }
    
    public ResourceManager getResourceManager() {
        return resourceManager;
    }
    
    public List<Unit> getUnits() {
        return new ArrayList<>(units);
    }
    
    public List<Building> getBuildings() {
        return new ArrayList<>(buildings);
    }
    
    public boolean isAI() {
        return isAI;
    }
    
    public void displayStatus() {
        System.out.println("\n=== Statut de " + nom + " ===");
        System.out.println(resourceManager);
        System.out.println("Unités: " + units.size());
        for (Unit unit : units) {
            System.out.println("  - " + unit);
        }
        System.out.println("Bâtiments: " + buildings.size());
        for (Building building : buildings) {
            System.out.println("  - " + building);
        }
    }
}
