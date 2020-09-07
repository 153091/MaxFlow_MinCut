public class FlowEdge {
    private final int v, w;
    private double flow;
    private final double capacity;

    // constructor
    public FlowEdge(int v, int w, double capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
    }

    // vertex this edge points from
    public int from() {
        return v;
    }

    // vertex this edge points to
    public int to() {
        return w;
    }

    // other vertex
    public int other(int vertex) {
        if (vertex == v) return w;
        else             return v;
    }

    // capacity of this edge
    public double capacity() {
        return capacity;
    }

    // flow of this edge
    public double flow() {
        return flow;
    }

    // residual capacity toward v
    public double residualCapacity(int vertex) {
        if (vertex == v) return flow;                 // backward edge
        else                 return capacity - flow; // forward edge
    }

    // add delta flow toward v
    public void addResidualCapacityTo(int vertex, double delta) {
        if (vertex == v) flow -= delta;      // backward edge
        else if (vertex == w) flow += delta; // forward edge
    }

    // string representation
    public String toString() {
        return v + "->" + w + " " + flow + "/" + capacity;
    }

    public static void main(String[] args) {
        FlowEdge e = new FlowEdge(12, 23, 4.56);
        System.out.println(e);
    }
}
