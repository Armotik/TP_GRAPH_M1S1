// GraphM4A.java

import java.util.Scanner;

public class GraphM4A {

    private int n;
    private int type; //0 if undirected, 1 if directed
    private int weighted; // 0 if unweighted, 1 otherwise
    float[][] adjmat;

    // Constructor for the creation of a graph manually
    public GraphM4A(int n, int type, int weighted) {
        this.n = n;
        this.type = type;
        this.weighted = weighted;
        this.adjmat = new float[this.n][this.n];
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++)
                adjmat[i][j] = 0;
    }

    public GraphM4A(Scanner sc) {
        String[] firstline = sc.nextLine().split(" ");
        this.n = Integer.parseInt(firstline[0]);
        System.out.println("Number of vertices " + this.n);
        if (firstline[1].equals("undirected"))
            this.type = 0;
        else
            this.type = 1;
        System.out.println("Type= " + this.type);
        if (firstline[2].equals("unweighted"))
            this.weighted = 0;
        else
            this.weighted = 1;
        System.out.println("Weighted= " + this.weighted);

        this.adjmat = new float[this.n][this.n];
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++)
                adjmat[i][j] = 0; // replace 0 with something else if the weights can be 0

        for (int k = 0; k < this.n; k++) {
            String[] line = sc.nextLine().split(" : ");
            int i = Integer.parseInt(line[0]); //the vertex "source"
            if (weighted == 0) {
                if ((line.length > 1) && (line[1].charAt(0) != ' ')) {
                    String[] successors = line[1].split(", ");
                    for (int h = 0; h < successors.length; h++) {
                        this.adjmat[i - 1][Integer.parseInt(successors[h]) - 1] = 1;
                    }
                }
            } else {
                line = line[1].split(" // ");
                if ((line.length == 2) && (line[1].charAt(0) != ' ')) {// if there really are somme successors, then we must have something different from " " after "// "
                    String[] successors = line[0].split(", ");
                    String[] theirweights = line[1].split(", ");
                    for (int h = 0; h < successors.length; h++)
                        this.adjmat[i - 1][Integer.parseInt(successors[h]) - 1] = Float.parseFloat(theirweights[h]);
                }
            }
        }
    }

    //method to be applied only when type=0
    public int[] degree() {
        int[] tmp = new int[this.n];
        for (int i = 0; i < this.n; i++)
            tmp[i] = 0;
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++)
                if (this.adjmat[i][j] != 0)
                    tmp[i] = tmp[i] + 1;
        return tmp;

    }

    //method to be applied only when type=1
    public TwoArrays4A degrees() {
        int[] tmp1 = new int[this.n]; //indegrees
        int[] tmp2 = new int[this.n]; //outdegrees
        for (int i = 0; i < this.n; i++) {
            tmp1[i] = 0;
            tmp2[i] = 0;
        }
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++)
                if (this.adjmat[i][j] != 0) {
                    tmp2[i] = tmp2[i] + 1;
                    tmp1[j] = tmp1[j] + 1;
                }
        return (new TwoArrays4A(tmp1, tmp2));

    }

    public int getType() {
        return this.type;
    }

    public void print() {
        System.out.println("Adjacency Matrix:");
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print(adjmat[i][j] + "\t");
            }
            System.out.println();
        }
    }

    ///  EX 1

    /**
     * Computes the transposed graph.
     * Input: Adjacency Matrix
     * Output: Adjacency Matrix
     * Running time : O(n^2) where n is the number of vertices.
     * We need to iterate through the entire n x n matrix.
     */
    public GraphM4A transposeToMatrix() {
        GraphM4A transposedGraph = new GraphM4A(this.n, this.type, this.weighted);
        // fill transposedGraph with the transpose of this graph
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                transposedGraph.adjmat[j][i] = this.adjmat[i][j];
            }
        }
        return transposedGraph;
    }

    /**
     * Computes the transposed graph.
     * Input: Adjacency Matrix
     * Output: Adjacency List
     * Running time : O(n^2) where n is the number of vertices.
     * We need to iterate through the entire n x n matrix to find all edges.
     */
    public GraphL4A transposeToList() {
        GraphL4A transposedGraph = new GraphL4A(this.n, this.type, this.weighted);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.adjmat[i][j] != 0) { // Edge i -> j exists
                    // Add edge j -> i in the transposed graph
                    if (this.weighted == 0) {
                        Node4A newNode = new Node4A(i, transposedGraph.getAdjlist()[j]);
                        transposedGraph.getAdjlist()[j] = newNode;
                    } else {
                        WeightedNode4A newNode = new WeightedNode4A(i, transposedGraph.getAdjlistW()[j], this.adjmat[i][j]);
                        transposedGraph.getAdjlistW()[j] = newNode;
                    }
                }
            }
        }
        return transposedGraph;
    }

    ///  EX 2

    /**
     * Tests whether a sequence of vertices is a path in the graph.
     * Input representation: Adjacency Matrix.
     * Running time : O(k) where k is the length of the sequence.
     * Each edge check is O(1).
     */
    public boolean isPath(int[] sequence) {
        if (sequence == null || sequence.length < 2) {
            return true; // A single vertex or empty sequence is trivially a path.
        }
        for (int i = 0; i < sequence.length - 1; i++) {
            int u = sequence[i];
            int v = sequence[i + 1];

            // Vertices are 1-based, matrix is 0-based
            if (u < 1 || u > n || v < 1 || v > n) {
                System.out.println("Error: Vertex out of bounds.");
                return false;
            }

            if (adjmat[u - 1][v - 1] == 0) {
                System.out.println("No edge from " + u + " to " + v);
                return false; // Edge does not exist
            }
        }
        return true;
    }

}