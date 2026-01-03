package com.isil.strategygame;

import com.isil.strategygame.core.Game;
import java.util.Scanner;

/**
 * Classe principale pour lancer le jeu
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            System.out.print("Votre choix: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne
            
            switch (choice) {
                case 1:
                    // Nouvelle partie
                    Game game = new Game();
                    game.run();
                    break;
                case 2:
                    // Charger (non implémenté dans cette version)
                    System.out.println("Fonctionnalité non implémentée dans cette version.");
                    break;
                case 3:
                    displayHelp();
                    break;
                case 0:
                    System.out.println("Merci d'avoir joué !");
                    running = false;
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        }
        
        scanner.close();
    }
    
    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("              STRATEGY GAME - ISIL 25/26");
        System.out.println("=".repeat(50));
        System.out.println("1. Nouvelle Partie");
        System.out.println("2. Charger une Partie");
        System.out.println("3. Aide");
        System.out.println("0. Quitter");
        System.out.println("=".repeat(50));
    }
    
    private static void displayHelp() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                      AIDE");
        System.out.println("=".repeat(50));
        System.out.println("\nOBJECTIF:");
        System.out.println("Détruire le Centre de Commandement ennemi ou éliminer");
        System.out.println("toutes les unités ennemies pour gagner la partie.");
        
        System.out.println("\nRESSOURCES:");
        System.out.println("- Or: Monnaie principale pour construire");
        System.out.println("- Bois: Nécessaire pour certains bâtiments");
        System.out.println("- Pierre: Utilisée pour les constructions solides");
        System.out.println("- Nourriture: Nécessaire pour entraîner des unités");
        
        System.out.println("\nBÂTIMENTS:");
        System.out.println("- Centre de Commandement [H]: Bâtiment principal");
        System.out.println("- Camp d'Entraînement [B]: Permet de créer des unités");
        System.out.println("- Mine [M]: Produit de l'or et de la pierre");
        System.out.println("- Ferme [F]: Produit de la nourriture");
        System.out.println("- Scierie [W]: Produit du bois");
        
        System.out.println("\nUNITÉS:");
        System.out.println("- Soldat [S]: Unité de mêlée équilibrée");
        System.out.println("- Archer [A]: Unité à distance (portée 3)");
        System.out.println("- Cavalier [C]: Unité puissante mais coûteuse");
        
        System.out.println("\nTERRAIN:");
        System.out.println("- Herbe [.]: Terrain normal");
        System.out.println("- Eau [~]: Infranchissable");
        System.out.println("- Montagne [^]: Bonus de défense");
        System.out.println("- Forêt [T]: Bonus de défense");
        
        System.out.println("\nCONSEILS:");
        System.out.println("- Construisez des bâtiments de production rapidement");
        System.out.println("- Protégez votre Centre de Commandement");
        System.out.println("- Variez vos unités (mêlée et distance)");
        System.out.println("- Utilisez le terrain à votre avantage");
        System.out.println("=".repeat(50));
    }
}
