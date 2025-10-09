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

    ///
    /// TP2
    ///

    /// EX 1

    private int[] visited;
    private int[] disc;
    private int[] fin;
    private int[] pred;
    private int time;

    /**
     * Performs DFS traversal and timestamps each vertex. Classifies arcs as Tree or Back arcs.
     * Input representation: Adjacency Matrix.
     * Running time : O(n^2) where n is the number of vertices.
     * We need to iterate through the entire n x n matrix to find all edges.
     */
    public void DFSNum() {
        visited = new int[n];
        disc = new int[n];
        fin = new int[n];
        pred = new int[n];
        time = 0;

        for (int i = 0; i < n; i++) {
            visited[i] = 0; // 0 means unvisited
            pred[i] = -1;   // -1 means no predecessor
        }

        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) {
                DFSVisit(i);
            }
        }

        System.out.println("Vertex\tDiscovery\tFinish\tPredecessor");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t" + disc[i] + "\t\t" + fin[i] + "\t" + (pred[i] == -1 ? "NULL" : (pred[i] + 1)));
        }
    }

    /**
     * Helper method for DFS traversal.
     * Classifies arcs as Tree or Back arcs.
     * @param u the current vertex being visited
     */
    private void DFSVisit(int u) {
        visited[u] = 1; // Mark as visiting
        time++;
        disc[u] = time;

        for (int v = 0; v < n; v++) {
            if (adjmat[u][v] != 0) { // There is an edge u -> v
                if (visited[v] == 0) {
                    System.out.println("Tree arc: (" + (u + 1) + ", " + (v + 1) + ")");
                    pred[v] = u;
                    DFSVisit(v);
                } else if (visited[v] == 1) {
                    System.out.println("Back arc: (" + (u + 1) + ", " + (v + 1) + ")");
                }
                // Forward and Cross arcs are not classified here
            }
        }

        visited[u] = 2; // Mark as fully visited
        time++;
        fin[u] = time;
    }

    /**
     * Detects and prints the vertices of a cycle in the graph (adjacency matrix).
     * If a cycle is found, prints the sequence of vertices forming the cycle.
     * Works for both directed and undirected graphs.
     * Returns true if a cycle is found, false otherwise.
     */
    public boolean containsCycle() {
        boolean[] visited = new boolean[n]; // Tracks visited vertices
        boolean[] recStack = new boolean[n]; // Tracks recursion stack for directed graphs
        java.util.List<Integer> path = new java.util.ArrayList<>(); // Stores the current DFS path
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                // Choose the correct DFS depending on graph type
                if (type == 1) { // Directed
                    if (dfsCycleDirected(i, visited, recStack, path)) return true;
                } else { // Undirected
                    if (dfsCycleUndirected(i, visited, -1, path)) return true;
                }
            }
        }
        return false;
    }

    /**
     * DFS for directed graphs. Prints the cycle when found.
     * @param v Current vertex
     * @param visited Visited array
     * @param recStack Recursion stack array
     * @param path Current DFS path
     * @return true if a cycle is found
     */
    private boolean dfsCycleDirected(int v, boolean[] visited, boolean[] recStack, java.util.List<Integer> path) {
        visited[v] = true;
        recStack[v] = true;
        path.add(v);
        for (int u = 0; u < n; u++) {
            if (adjmat[v][u] != 0) {
                if (!visited[u]) {
                    if (dfsCycleDirected(u, visited, recStack, path)) return true;
                } else if (recStack[u]) {
                    // Cycle detected: print the cycle vertices
                    int idx = path.indexOf(u);
                    if (idx != -1) {
                        System.out.print("Cycle found: ");
                        for (int i = idx; i < path.size(); i++) {
                            System.out.print((path.get(i)+1) + " ");
                        }
                        System.out.println((u+1)); // Close the cycle
                    }
                    return true;
                }
            }
        }
        recStack[v] = false;
        path.remove(path.size()-1);
        return false;
    }

    /**
     * DFS for undirected graphs. Prints the cycle when found.
     * @param v Current vertex
     * @param visited Visited array
     * @param parent Parent vertex in DFS
     * @param path Current DFS path
     * @return true if a cycle is found
     */
    private boolean dfsCycleUndirected(int v, boolean[] visited, int parent, java.util.List<Integer> path) {
        visited[v] = true;
        path.add(v);
        for (int u = 0; u < n; u++) {
            if (adjmat[v][u] != 0) {
                if (!visited[u]) {
                    if (dfsCycleUndirected(u, visited, v, path)) return true;
                } else if (u != parent) {
                    // Cycle detected: print the cycle vertices
                    int idx = path.indexOf(u);
                    if (idx != -1) {
                        System.out.print("Cycle found: ");
                        for (int i = idx; i < path.size(); i++) {
                            System.out.print((path.get(i)+1) + " ");
                        }
                        System.out.println((u+1)); // Close the cycle
                    }
                    return true;
                }
            }
        }
        path.remove(path.size()-1);
        return false;
    }
}