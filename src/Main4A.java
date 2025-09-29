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

            // --- PART 1: Reading graph using Adjacency Matrix representation ---
            System.out.println("==============================================");
            System.out.println("TESTING WITH ADJACENCY MATRIX");
            System.out.println("==============================================");
            Scanner scM = new Scanner(file);
            GraphM4A graphM = new GraphM4A(scM);
            scM.close();

            System.out.println("\n--- Original Graph (Matrix) ---");
            graphM.print();


            // --- PART 2: Reading graph using Adjacency List representation ---
            System.out.println("\n==============================================");
            System.out.println("TESTING WITH ADJACENCY LIST");
            System.out.println("==============================================");
            Scanner scL = new Scanner(file);
            GraphL4A graphL = new GraphL4A(scL);
            scL.close();

            System.out.println("\n--- Original Graph (List) ---");
            graphL.print();


            /// EX 1
            System.out.println("\n==============================================");
            System.out.println("RESPONSE TO EXERCISE 1");
            System.out.println("==============================================");

            if (graphM.getType() == 1) {
                System.out.println("\n--- 1. Transpose Matrix -> Matrix ---");
                GraphM4A g_t_MM = graphM.transposeToMatrix();
                g_t_MM.print();

                System.out.println("\n--- 2. Transpose List -> List ---");
                GraphL4A g_t_LL = graphL.transposeToList();
                g_t_LL.print();

                System.out.println("\n--- 3. Transpose Matrix -> List ---");
                GraphL4A g_t_ML = graphM.transposeToList();
                g_t_ML.print();

                System.out.println("\n--- 4. Transpose List -> Matrix ---");
                GraphM4A g_t_LM = graphL.transposeToMatrix();
                g_t_LM.print();
            } else {
                System.out.println("\nSkipping Exercise 1: Graph is undirected.");
            }


            /// EX 2
            System.out.println("\n==============================================");
            System.out.println("RESPONSE TO EXERCISE 2");
            System.out.println("==============================================");

            int[] path1 = {1, 10, 12}; // Should be true
            int[] path2 = {1, 27};     // Should be true
            int[] path3 = {1, 10, 2};  // Should be true
            int[] path4 = {1, 12};     // Should be false
            int[] path5 = {16, 1};     // Should be false
            int[] path6 = {1};         // Should be true

            System.out.println("\n--- Testing paths with Matrix representation ---");
            System.out.println("Is path {1, 10, 12}? -> " + graphM.isPath(path1));
            System.out.println("Is path {1, 27}? -> " + graphM.isPath(path2));
            System.out.println("Is path {1, 10, 2}? -> " + graphM.isPath(path3));
            System.out.println("Is path {1, 12}? -> " + graphM.isPath(path4));
            System.out.println("Is path {16, 1}? -> " + graphM.isPath(path5));
            System.out.println("Is path {1}? -> " + graphM.isPath(path6));

            System.out.println("\n--- Testing paths with List representation ---");
            System.out.println("Is path {1, 10, 12}? -> " + graphL.isPath(path1));
            System.out.println("Is path {1, 27}? -> " + graphL.isPath(path2));
            System.out.println("Is path {1, 10, 2}? -> " + graphL.isPath(path3));
            System.out.println("Is path {1, 12}? -> " + graphL.isPath(path4));
            System.out.println("Is path {16, 1}? -> " + graphL.isPath(path5));
            System.out.println("Is path {1}? -> " + graphL.isPath(path6));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
