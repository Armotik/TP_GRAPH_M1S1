// GraphL4A.java

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
                DFS_visit(i);
            }
        }

        System.out.println("\n--- Final Timestamps ---");
        System.out.println("Vertex\tDisc\tFin");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t" + this.disc[i] + "\t" + this.fin[i]);
        }
    }

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

        this.visited[u] = 2; // 2 = NOIR
        this.time++;
        this.fin[u] = this.time;
    }
}
		
	

	 