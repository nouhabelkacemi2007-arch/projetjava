package com.isil.strategygame.map;

import com.isil.strategygame.utils.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Représente la carte de jeu
 */
public class GameMap {
    private final int width;
    private final int height;
    private final Tile[][] tiles;
    
    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[height][width];
        generateMap();
    }
    
    /**
     * Génère la carte de manière procédurale
     */
    private void generateMap() {
        Random random = new Random();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Position pos = new Position(x, y);
                TileType type;
                
                // Génération procédurale simple
                int rand = random.nextInt(100);
                if (rand < 60) {
                    type = TileType.GRASS;
                } else if (rand < 75) {
                    type = TileType.FOREST;
                } else if (rand < 85) {
                    type = TileType.MOUNTAIN;
                } else {
                    type = TileType.WATER;
                }
                
                tiles[y][x] = new Tile(pos, type);
            }
        }
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public Tile getTile(Position position) {
        if (isValidPosition(position)) {
            return tiles[position.getY()][position.getX()];
        }
        return null;
    }
    
    public Tile getTile(int x, int y) {
        return getTile(new Position(x, y));
    }
    
    public boolean isValidPosition(Position position) {
        return position.getX() >= 0 && position.getX() < width &&
               position.getY() >= 0 && position.getY() < height;
    }
    
    /**
     * Retourne les cases voisines d'une position
     */
    public List<Tile> getNeighbors(Position position) {
        List<Tile> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        
        for (int[] dir : directions) {
            Position neighborPos = new Position(
                position.getX() + dir[0],
                position.getY() + dir[1]
            );
            if (isValidPosition(neighborPos)) {
                neighbors.add(getTile(neighborPos));
            }
        }
        
        return neighbors;
    }
    
    /**
     * Affiche la carte en mode texte
     */
    public void display() {
        System.out.println("\n=== CARTE DE JEU ===");
        System.out.print("   ");
        for (int x = 0; x < width; x++) {
            System.out.print(String.format("%2d ", x));
        }
        System.out.println();
        
        for (int y = 0; y < height; y++) {
            System.out.print(String.format("%2d ", y));
            for (int x = 0; x < width; x++) {
                System.out.print(tiles[y][x].toString());
            }
            System.out.println();
        }
        System.out.println("\nLégende: . = Herbe, ~ = Eau, ^ = Montagne, T = Forêt");
    }
}
