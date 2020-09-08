import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Queue;

public class FlowNetwork {
    private final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Queue<FlowEdge>[] adj;

    // create empty graph with V vertices
    public FlowNetwork(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");

        this.V = V;
        this.E = 0;
        adj = (Queue<FlowEdge>[]) new Queue[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayDeque<>();
    }

    // create flow network from input stream
    public FlowNetwork(In in) {
        this(in.readInt()); // выполняет предыдущий конструктор
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double capacity = in.readDouble();
            addEdge(new FlowEdge(v, w, capacity));
        }
    }

    // add edge e
    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    // forward and backward edges incident to v
    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }

    // all edges
    public Iterable<FlowEdge> edges() {
        Queue<FlowEdge> list = new ArrayDeque<>();
        for (int v = 0; v < V; v++)
            for (FlowEdge e : adj[v])
                if (e.to() != v)
                    list.add(e);
        return list;
    }

    // number of vertices
    public int V() {
        return V;
    }

    // number of edges
    public int E() {
        return E;
    }

    // string representation
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (FlowEdge e : adj[v]) {
                if (e.to() != v) s.append(e + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        FlowNetwork G = new FlowNetwork(in);
        StdOut.println(G);
    }
}
