package graph_model;
import java.util.HashMap;

public class Vertex {
    private String name;
    private HashMap<Vertex, Edge> incidentEdges;

    public Vertex(String name) {
        this.name = name;
        incidentEdges = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public HashMap<Vertex, Edge> getIncidentEdges() {
        return incidentEdges;
    }

    public void addEdge(Edge e) {
        incidentEdges.put(e.getOtherVertex(this), e);
    }

    public Edge to(Vertex v) { // return the edge from this to v
        return incidentEdges.get(v);
    }
}
