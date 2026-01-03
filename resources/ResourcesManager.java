package com.isil.strategygame.resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Gère les ressources d'un joueur
 */
public class ResourceManager {
    private final Map<ResourceType, Integer> resources;
    
    public ResourceManager() {
        resources = new HashMap<>();
        // Initialisation des ressources de départ
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, 100); // Chaque joueur commence avec 100 de chaque ressource
        }
    }
    
    /**
     * Ajoute une quantité de ressource
     */
    public void addResource(ResourceType type, int amount) {
        resources.put(type, resources.get(type) + amount);
    }
    
    /**
     * Retire une quantité de ressource
     * @return true si l'opération a réussi, false si pas assez de ressources
     */
    public boolean removeResource(ResourceType type, int amount) {
        int current = resources.get(type);
        if (current >= amount) {
            resources.put(type, current - amount);
            return true;
        }
        return false;
    }
    
    /**
     * Vérifie si le joueur a suffisamment de ressources
     */
    public boolean hasResources(Map<ResourceType, Integer> cost) {
        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            if (resources.get(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Consomme des ressources selon un coût donné
     */
    public boolean consumeResources(Map<ResourceType, Integer> cost) {
        if (!hasResources(cost)) {
            return false;
        }
        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            removeResource(entry.getKey(), entry.getValue());
        }
        return true;
    }
    
    public int getResource(ResourceType type) {
        return resources.get(type);
    }
    
    public Map<ResourceType, Integer> getAllResources() {
        return new HashMap<>(resources);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Ressources: ");
        for (Map.Entry<ResourceType, Integer> entry : resources.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" | ");
        }
        return sb.toString();
    }
}
