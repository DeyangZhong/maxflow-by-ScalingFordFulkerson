package graph_model;

public class Edge {
    private Vertex v1;
    private Vertex v2;
    private double flow;
    private double capacity;
    private boolean isForwardEdge; // whether it's a forward edge

    public Edge(Vertex v1, Vertex v2, double flow, double capacity, boolean isFwdEdge) {
        this.v1 = v1;
        this.v2 = v2;
        this.flow = flow;
        this.capacity = capacity;
        this.isForwardEdge = isFwdEdge;
    }

    public Vertex getFirstNode() {
        return v1;
    }

    public Vertex getSecondNode() {
        return v2;
    }

    public double getFlow() {
        return flow;
    }

    public double getCapacity() {
        return capacity;
    }

    public boolean isFwdEdge() {
        return isForwardEdge;
    }

    public double getResidual() {
        return capacity - flow;
    }

    public void addFlow(double d) {
        flow += d;
    }

    public Vertex getOtherVertex(Vertex v) { // get the other vertex except v in this edge
        return (v.equals(v1)) ? v2 : v1;
    }

    public void addCapacity(double d) {
        this.capacity += d;
    }
}