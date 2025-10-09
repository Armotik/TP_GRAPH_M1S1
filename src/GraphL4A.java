// GraphL4A.java

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class GraphL4A {

    private int n;
    private int type; //0 if undirected, 1 if directed
    private int weighted; // 0 if unweighted, 1 otherwise
    private WeightedNode4A[] adjlistW; //one of adjlistW and adjlist is null, depending on the type of the graph
    private Node4A[] adjlist;

    // Constructor for the creation of a graph manually
    public GraphL4A(int n, int type, int weighted) {
        this.n = n;
        this.type = type;
        this.weighted = weighted;
        if (this.weighted == 0) {
            this.adjlist = new Node4A[this.n];
            for (int i = 0; i < this.n; i++)
                adjlist[i] = null;
            adjlistW = null;
        } else {
            this.adjlistW = new WeightedNode4A[this.n];
            for (int i = 0; i < this.n; i++)
                adjlistW[i] = null;
            adjlist = null;
        }
    }

    public GraphL4A(Scanner sc) {
        String[] firstline = sc.nextLine().split(" ");
        this.n = Integer.parseInt(firstline[0]);
        System.out.println(this.n);
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
        if (this.weighted == 0) {
            this.adjlist = new Node4A[this.n];
            for (int i = 0; i < this.n; i++)
                adjlist[i] = null;
            adjlistW = null;
        } else {
            this.adjlistW = new WeightedNode4A[this.n];
            for (int i = 0; i < this.n; i++)
                adjlistW[i] = null;
            adjlist = null;
        }


        for (int k = 0; k < this.n; k++) {
            String[] line = sc.nextLine().split(" : ");
            int i = Integer.parseInt(line[0]); //the vertex "source"
            if (weighted == 0) {
                if ((line.length > 1) && (line[1].charAt(0) != ' ')) {
                    String[] successors = line[1].split(", ");
                    for (int h = 0; h < successors.length; h++) {
                        Node4A node = new Node4A(Integer.parseInt(successors[h]) - 1, null);
                        node.setNext(adjlist[i - 1]);
                        adjlist[i - 1] = node;
                    }
                }
            } else {
                line = line[1].split(" // ");
                if ((line.length == 2) && (line[1].charAt(0) != ' ')) {// if there really are somme successors, then we must have something different from " " after "// "
                    String[] successors = line[0].split(", ");
                    String[] theirweights = line[1].split(", ");
                    for (int h = 0; h < successors.length; h++) {
                        WeightedNode4A nodeW = new WeightedNode4A(Integer.parseInt(successors[h]) - 1, null, Float.parseFloat(theirweights[h]));
                        nodeW.setNext(adjlistW[i - 1]);
                        adjlistW[i - 1] = nodeW;
                    }

                }
            }
        }

    }


    //method to be applied only when type=0 and weighted=0
    public int[] degree() {
        int[] tmp = new int[this.n];
        for (int i = 0; i < this.n; i++)
            tmp[i] = 0;
        for (int i = 0; i < this.n; i++) {
            Node4A p = adjlist[i];
            while (p != null) {
                tmp[i] = tmp[i] + 1;
                p = p.getNext();
            }
        }
        return (tmp);
    }

    //method to be applied only when type=0 and weighted=1
    public int[] degreeW() {
        int[] tmp = new int[this.n];
        for (int i = 0; i < this.n; i++)
            tmp[i] = 0;
        for (int i = 0; i < this.n; i++) {
            WeightedNode4A p = adjlistW[i];
            while (p != null) {
                tmp[i] = tmp[i] + 1;
                p = p.getNext();
            }
        }
        return (tmp);
    }


    //method to be applied only when type=1 and weighted=0
    public TwoArrays4A degrees() {
        int[] tmp1 = new int[this.n]; //indegrees
        int[] tmp2 = new int[this.n]; //outdegrees
        for (int i = 0; i < this.n; i++) {
            tmp1[i] = 0;
            tmp2[i] = 0;
        }
        for (int i = 0; i < this.n; i++) {
            Node4A p = adjlist[i];
            while (p != null) {
                tmp2[i] = tmp2[i] + 1;
                tmp1[p.getVal()] = tmp1[p.getVal()] + 1;
                p = p.getNext();
            }
        }
        return (new TwoArrays4A(tmp1, tmp2));
    }

    //method to be applied only when type=1 and weighted=1
    public TwoArrays4A degreesW() {
        int[] tmp1 = new int[this.n]; //indegrees
        int[] tmp2 = new int[this.n]; //outdegrees
        for (int i = 0; i < this.n; i++) {
            tmp1[i] = 0;
            tmp2[i] = 0;
        }
        for (int i = 0; i < this.n; i++) {
            WeightedNode4A p = adjlistW[i];
            while (p != null) {
                tmp2[i] = tmp2[i] + 1;
                tmp1[p.getVal()] = tmp1[p.getVal()] + 1;
                p = p.getNext();
            }
        }
        return (new TwoArrays4A(tmp1, tmp2));
    }


    public int getType() {
        return this.type;
    }

    public Node4A[] getAdjlist() {
        return adjlist;
    }

    public WeightedNode4A[] getAdjlistW() {
        return adjlistW;
    }

    public void print() {
        System.out.println("Adjacency List:");
        if (weighted == 0) {
            for (int i = 0; i < n; i++) {
                System.out.print((i + 1) + " -> ");
                Node4A p = adjlist[i];
                while (p != null) {
                    System.out.print((p.getVal() + 1) + " ");
                    p = p.getNext();
                }
                System.out.println();
            }
        } else {
            for (int i = 0; i < n; i++) {
                System.out.print((i + 1) + " -> ");
                WeightedNode4A p = adjlistW[i];
                while (p != null) {
                    System.out.print((p.getVal() + 1) + " [" + p.getWeight() + "] ");
                    p = p.getNext();
                }
                System.out.println();
            }
        }
    }

    ///  EX 1

    /**
     * Computes the transposed graph.
     * Input: Adjacency List
     * Output: Adjacency List
     * Running time : O(n+m) where n is the number of vertices
     * and m is the number of edges. We visit each vertex and each edge once.
     */
    public GraphL4A transposeToList() {
        GraphL4A transposedGraph = new GraphL4A(this.n, this.type, this.weighted);
        if (this.weighted == 0) {
            for (int i = 0; i < this.n; i++) {
                Node4A p = this.adjlist[i];
                while (p != null) {
                    int j = p.getVal(); // Edge i -> j exists
                    // Add edge j -> i to the transposed graph
                    Node4A newNode = new Node4A(i, transposedGraph.adjlist[j]);
                    transposedGraph.adjlist[j] = newNode;
                    p = p.getNext();
                }
            }
        } else {
            for (int i = 0; i < this.n; i++) {
                WeightedNode4A p = this.adjlistW[i];
                while (p != null) {
                    int j = p.getVal(); // Edge i -> j exists
                    // Add edge j -> i to the transposed graph
                    WeightedNode4A newNode = new WeightedNode4A(i, transposedGraph.adjlistW[j], p.getWeight());
                    transposedGraph.adjlistW[j] = newNode;
                    p = p.getNext();
                }
            }
        }
        return transposedGraph;
    }

    /**
     * Computes the transposed graph.
     * Input: Adjacency List
     * Output: Adjacency Matrix
     * Running time : O(n+m) where n is the number of vertices
     * and m is the number of edges. We visit each vertex and each edge once.
     */
    public GraphM4A transposeToMatrix() {
        GraphM4A transposedGraph = new GraphM4A(this.n, this.type, this.weighted);
        if (this.weighted == 0) {
            for (int i = 0; i < this.n; i++) {
                Node4A p = this.adjlist[i];
                while (p != null) {
                    int j = p.getVal(); // Edge i -> j exists
                    // Add edge j -> i to the transposed graph's matrix
                    transposedGraph.adjmat[j][i] = 1;
                    p = p.getNext();
                }
            }
        } else {
            for (int i = 0; i < this.n; i++) {
                WeightedNode4A p = this.adjlistW[i];
                while (p != null) {
                    int j = p.getVal(); // Edge i -> j exists
                    // Add edge j -> i to the transposed graph's matrix
                    transposedGraph.adjmat[j][i] = p.getWeight();
                    p = p.getNext();
                }
            }
        }
        return transposedGraph;
    }

    /// EX 2

    /**
     * Tests whether a sequence of vertices is a path in the graph.
     * Input representation: Adjacency List.
     * Running time : O(k * deg_max) where k is the length of the sequence
     * and deg_max is the maximum out-degree of a vertex.
     */
    public boolean isPath(int[] sequence) {
        if (sequence == null || sequence.length < 2) {
            return true;
        }

        for (int i = 0; i < sequence.length - 1; i++) {
            int u = sequence[i];
            int v = sequence[i + 1];

            if (u < 1 || u > n || v < 1 || v > n) {
                System.out.println("Error: Vertex out of bounds.");
                return false;
            }

            boolean edgeFound = false;
            if (weighted == 0) {
                Node4A p = adjlist[u - 1];
                while (p != null) {
                    if (p.getVal() == v - 1) {
                        edgeFound = true;
                        break;
                    }
                    p = p.getNext();
                }
            } else {
                WeightedNode4A p = adjlistW[u - 1];
                while (p != null) {
                    if (p.getVal() == v - 1) {
                        edgeFound = true;
                        break;
                    }
                    p = p.getNext();
                }
            }

            if (!edgeFound) {
                System.out.println("No edge from " + u + " to " + v);
                return false;
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
     * Input representation: Adjacency List.
     * Running time : O(n + m) where n is the number of vertices and m is the number of edges.
     * Each vertex and edge is visited once.
     * Output: Prints arc classifications and timestamps.
     */
    public void DFSNum() {
        this.visited = new int[n];
        this.disc = new int[n];
        this.fin = new int[n];
        this.pred = new int[n];
        this.time = 0;

        for (int i = 0; i < n; i++) {
            this.visited[i] = 0;
            this.pred[i] = -1;
        }

        System.out.println("--- Arc Classification ---");
        for (int i = 0; i < n; i++) {
            if (this.visited[i] == 0) {
                if (weighted == 0) {
                    DFS_visit(i);
                } else {
                    DFS_visitW(i);
                }
            }
        }

        System.out.println("\n--- Final Timestamps ---");
        System.out.println("Vertex\tDisc\tFin");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t" + this.disc[i] + "\t" + this.fin[i]);
        }
    }

    /**
     * Helper method for DFS traversal and arc classification (non-weighted).
     * @param u The current vertex being visited.
     */
    private void DFS_visit(int u) {
        this.visited[u] = 1;
        this.time++;
        this.disc[u] = this.time;

        Node4A p = this.adjlist[u];
        while (p != null) {
            int v = p.getVal();
            if (this.visited[v] == 0) {
                System.out.println("Tree arc: (" + (u + 1) + "," + (v + 1) + ")");
                this.pred[v] = u;
                DFS_visit(v);
            } else if (this.visited[v] == 1) {
                if (this.pred[u] != v) {
                    System.out.println("Back arc: (" + (u + 1) + "," + (v + 1) + ") -> CYCLE DETECTED!");
                }
            }
            p = p.getNext();
        }
        this.visited[u] = 2;
        this.time++;
        this.fin[u] = this.time;
    }

    /**
     * Helper method for DFS traversal and arc classification (weighted).
     * @param u The current vertex being visited.
     */
    private void DFS_visitW(int u) {
        this.visited[u] = 1;
        this.time++;
        this.disc[u] = this.time;

        WeightedNode4A p = this.adjlistW[u];
        while (p != null) {
            int v = p.getVal();
            if (this.visited[v] == 0) {
                System.out.println("Tree arc: (" + (u + 1) + "," + (v + 1) + ")");
                this.pred[v] = u;
                DFS_visitW(v);
            } else if (this.visited[v] == 1) {
                if (this.pred[u] != v) {
                    System.out.println("Back arc: (" + (u + 1) + "," + (v + 1) + ") -> CYCLE DETECTED!");
                }
            }
            p = p.getNext();
        }
        this.visited[u] = 2;
        this.time++;
        this.fin[u] = this.time;
    }

    /**
     * Detects and prints the vertices of a cycle in the graph (adjacency list).
     * If a cycle is found, prints the sequence of vertices forming the cycle.
     * Works for both directed and undirected, weighted and unweighted graphs.
     * Returns true if a cycle is found, false otherwise.
     */
    public boolean containsCycle() {
        boolean[] visited = new boolean[n]; // Tracks visited vertices
        boolean[] recStack = new boolean[n]; // Tracks recursion stack for directed graphs
        List<Integer> path = new ArrayList<>(); // To store the current path
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                // Choose the correct DFS depending on graph type and weighting
                if (type == 1) { // Directed
                    if (weighted == 0) {
                        if (dfsCycleDirected(i, visited, recStack, path)) return true;
                    } else {
                        if (dfsCycleDirectedW(i, visited, recStack, path)) return true;
                    }
                } else { // Undirected
                    if (weighted == 0) {
                        if (dfsCycleUndirected(i, visited, -1, path)) return true;
                    } else {
                        if (dfsCycleUndirectedW(i, visited, -1, path)) return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * DFS for directed, unweighted graphs. Prints the cycle when found.
     * @param v Current vertex
     * @param visited Visited array
     * @param recStack Recursion stack array
     * @param path Current DFS path
     * @return true if a cycle is found
     */
    private boolean dfsCycleDirected(int v, boolean[] visited, boolean[] recStack, List<Integer> path) {
        visited[v] = true;
        recStack[v] = true;
        path.add(v);
        Node4A node = adjlist[v];
        while (node != null) {
            int u = node.getVal();
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
            node = node.getNext();
        }
        recStack[v] = false;
        path.remove(path.size()-1);
        return false;
    }

    /**
     * DFS for directed, weighted graphs. Prints the cycle when found.
     * @param v Current vertex
     * @param visited Visited array
     * @param recStack Recursion stack array
     * @param path Current DFS path
     * @return true if a cycle is found
     */
    private boolean dfsCycleDirectedW(int v, boolean[] visited, boolean[] recStack, List<Integer> path) {
        visited[v] = true;
        recStack[v] = true;
        path.add(v);
        WeightedNode4A node = adjlistW[v];
        while (node != null) {
            int u = node.getVal();
            if (!visited[u]) {
                if (dfsCycleDirectedW(u, visited, recStack, path)) return true;
            } else if (recStack[u]) {
                int idx = path.indexOf(u);
                if (idx != -1) {
                    System.out.print("Cycle found: ");
                    for (int i = idx; i < path.size(); i++) {
                        System.out.print((path.get(i)+1) + " ");
                    }
                    System.out.println((u+1));
                }
                return true;
            }
            node = node.getNext();
        }
        recStack[v] = false;
        path.remove(path.size()-1);
        return false;
    }

    /**
     * DFS for undirected, unweighted graphs. Prints the cycle when found.
     * @param v Current vertex
     * @param visited Visited array
     * @param parent Parent vertex in DFS
     * @param path Current DFS path
     * @return true if a cycle is found
     */
    private boolean dfsCycleUndirected(int v, boolean[] visited, int parent, List<Integer> path) {
        visited[v] = true;
        path.add(v);
        Node4A node = adjlist[v];
        while (node != null) {
            int u = node.getVal();
            if (!visited[u]) {
                if (dfsCycleUndirected(u, visited, v, path)) return true;
            } else if (u != parent) {
                int idx = path.indexOf(u);
                if (idx != -1) {
                    System.out.print("Cycle found: ");
                    for (int i = idx; i < path.size(); i++) {
                        System.out.print((path.get(i)+1) + " ");
                    }
                    System.out.println((u+1));
                }
                return true;
            }
            node = node.getNext();
        }
        path.remove(path.size()-1);
        return false;
    }

    /**
     * DFS for undirected, weighted graphs. Prints the cycle when found.
     * @param v Current vertex
     * @param visited Visited array
     * @param parent Parent vertex in DFS
     * @param path Current DFS path
     * @return true if a cycle is found
     */
    private boolean dfsCycleUndirectedW(int v, boolean[] visited, int parent, List<Integer> path) {
        visited[v] = true;
        path.add(v);
        WeightedNode4A node = adjlistW[v];
        while (node != null) {
            int u = node.getVal();
            if (!visited[u]) {
                if (dfsCycleUndirectedW(u, visited, v, path)) return true;
            } else if (u != parent) {
                int idx = path.indexOf(u);
                if (idx != -1) {
                    System.out.print("Cycle found: ");
                    for (int i = idx; i < path.size(); i++) {
                        System.out.print((path.get(i)+1) + " ");
                    }
                    System.out.println((u+1));
                }
                return true;
            }
            node = node.getNext();
        }
        path.remove(path.size()-1);
        return false;
    }
}
