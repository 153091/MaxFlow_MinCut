import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  Ford-Fulkerson algorithm for computing a max flow and
 *  a min cut using shortest augmenting path rule -
 *  based on Queue(BFS).
 *  */

public class FordFulkerson {
    private boolean[] marked;
    private FlowEdge[] edgeTo;
    private double value;

    // constructor
    public FordFulkerson(FlowNetwork G, int s, int t) {
        value = 0.0;

        while (hasAugmentingPath(G, s, t)) {
            double bottle = Double.POSITIVE_INFINITY; // bottleneck of augmenting path

            // compute bottleneck
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                assert edgeTo != null;
                bottle = Math.min(bottle, edgeTo[v].residualCapacity(v));
            }

            // increase flow ont that path by bottleneck capacity
            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualCapacityTo(v, bottle);

            value += bottle;
        }
    }

    // has augmenting path?
    // based on BFS
    private boolean hasAugmentingPath(FlowNetwork G , int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];
        Queue<Integer> queue = new ArrayDeque<>();

        queue.add(s);
        marked[s] = true;

        while(!queue.isEmpty()) {
            int v = queue.remove();

            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                // found a path from s to w int the residual network?
                if (e.residualCapacity(w) > 0 && !marked[w]) {
                    marked[w] = true;       // save last edge on path to w
                    edgeTo[w] = e;          // mark w
                    queue.add(w);           // add w to queue
                }
            }
        }
        // is there an augmenting path?
        return marked[t];
    }

    // value of the flow
    public double value() {
        return value;
    }

    // is v reachable from s in residual network
    public boolean inCut(int v) {
        return marked[v];
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        int t = Integer.parseInt(args[2]);

        FlowNetwork G = new FlowNetwork(in);
        FordFulkerson FF = new FordFulkerson(G, s ,t);

        // compute maximum flow and minimum cut
        StdOut.println("Max Flow from " + s + "to " + t);
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v))
                if (v == e.from() && e.flow() > 0)
                    StdOut.println("   " + e);
        }

        // compute Min Cut
        for (int v = 0; v < G.V(); v++)
            if (FF.inCut(v))
                StdOut.print(v + "  ");
        StdOut.println();
        StdOut.println("Max flow value = " + FF.value());
    }
}
