// Main4A.java

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class Main4A {

    public static void main(String args[]) {

        if (args.length == 0) {
            System.out.println("Usage: java Main4A <filename>");
            return;
        }

        try {
            File file = new File(args[0]);

            Scanner scL = new Scanner(file);
            GraphL4A graphL = new GraphL4A(scL);
            scL.close();

            Scanner scM = new Scanner(file);
            GraphM4A graphM = new GraphM4A(scM);
            scM.close();

            // --- TESTS DFS ET CLASSIFICATION DES ARCS SUR LE GRAPHE DU FICHIER ---
            System.out.println("\n--- DFS et classification des arcs (liste d'adjacence) ---");
            graphL.DFSNum();
            System.out.println("\n--- Détection de cycle (liste d'adjacence) ---");
            if (graphL.containsCycle()) {
                System.out.println("Le graphe (liste) contient un cycle.");
            } else {
                System.out.println("Le graphe (liste) ne contient pas de cycle.");
            }

            System.out.println("\n--- DFS et classification des arcs (matrice d'adjacence) ---");

            graphM.DFSNum();
            System.out.println("\n--- Détection de cycle (matrice d'adjacence) ---");
            graphM.print();
            if (graphM.containsCycle()) {
                System.out.println("Le graphe (matrice) contient un cycle.");
            } else {
                System.out.println("Le graphe (matrice) ne contient pas de cycle.");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
