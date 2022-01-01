/**
 * Dec/2021     Deyang Zhong
 * University of Washington, TCSS 543 final project
 * 
 * This class is mainly to obtain max-flow of a graph by Scaling-Ford-Fulkerson method
 * 
 * The file you input should be edges-information of a graph.
 * Eeach line should looks like "first-node second-node capacity".
 * It still works even though there are more whiteblank characters before first-node or after capacity or between two strings.
 * 
 * 
 */

import graph_model.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class scalingFordFulkerson {
    private LinkedList<Vertex> VertexList;
    private LinkedList<Edge> EdgeList;
    private Vertex source, target;
    private HashMap<Vertex, Edge> path; // temp path from source to target, each element contains the node and its previous edge in this path.
    private double maxFlow; // max flow of this graph

    public scalingFordFulkerson(String filename) throws FileNotFoundException {
        VertexList = new LinkedList<>();
        EdgeList = new LinkedList<>();

        MakeResidualGraph(filename); // according to the file, get the residual graph
        double delta = DeltaMaxCapacity(); // initialize the delta as the max power result of 2, which is restricted no greater than the max capacity of all edges.
        maxFlow = 0; // initialize flow
        while (delta >= 1) { // the core part of scaling Ford-Fulkerson algorithm
            while (hasDeltaAugmentPath(delta)) { // if exist Augment Path no less than deltea
                graph_update(delta);            // update the flow and flow (in fact, the residule graph contains all information of flow, we just modift the resiful graph)
                maxFlow += delta;               // max flow add delta as long as find a new simple s-t path
            }
            delta /= 2;                         // delta = delta / 2
        }
        printFinalGraph(); // print the final flow of graph. use it or not depend on you
    }

    /**
     * Create the residule graph. Because the initialized flow in each edge is 0, so
     * the residual graph is the same with initialized flow
     * 
     * @param filename the name of the graph file
     * @throws FileNotFoundException
     */
    public void MakeResidualGraph(String filename) throws FileNotFoundException {
        HashMap<String, Vertex> Vs = new HashMap<>(); // used for record all vertices

        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) { // scan the whole file line by line
            String line = scanner.nextLine();
            String[] info = line.trim().split("\\s+"); // cut whitespace at both ends or in between. info = [first-node-name, second-node-name, capacity between them]

            if (!Vs.containsKey(info[0])) { // check whether contain first node
                Vertex ver = new Vertex(info[0]);
                VertexList.add(ver);
                Vs.put(ver.getName(), ver);
            }

            if (!Vs.containsKey(info[1])) { // check whether contain second node
                Vertex ver = new Vertex(info[1]);
                VertexList.add(ver);
                Vs.put(ver.getName(), ver);
            }

            Edge edge = new Edge(Vs.get(info[0]), Vs.get(info[1]), 0, Double.parseDouble(info[2]), true); // create new edge
            Vs.get(info[0]).addEdge(edge); // add this edge to the incident edge of first node
            EdgeList.add(edge);
        }
        scanner.close();

        source = Vs.get("s"); // the source node
        target = Vs.get("t"); // the target node
    }

    // compute the initialized delta = the max power result of 2 && no greater than max-capacity of all edges
    private double DeltaMaxCapacity() {
        double MaxCapacity = 0, DeltaMax = 0;
        for (Edge edge : EdgeList) {                    // find max capacity
            MaxCapacity = Math.max(MaxCapacity, edge.getCapacity());
        }
        if (MaxCapacity > 0) {                          // find the desired delta
            DeltaMax = 1.;
            while (DeltaMax * 2 <= MaxCapacity) {
                DeltaMax *= 2;
            }
        }
        return DeltaMax;
    }

    //check whether there exist a simple delta s-t path, which contains no repeating nodes and all residuals in this path are no less than delta.
    private boolean hasDeltaAugmentPath(double delta) {
        path = new HashMap<>();
        HashSet<Vertex> Visited = new HashSet<Vertex>(); // record visited nodes

        // the following is a common BFS method
        Queue<Vertex> q = new LinkedList<>();
        q.add(source);
        Visited.add(source);
        while (!q.isEmpty()) {
            Vertex v = q.poll();
            Iterator<Entry<Vertex, Edge>> iterator = v.getIncidentEdges().entrySet().iterator(); // Iterator used for go through v's incident edges and vertices
            while (iterator.hasNext()) { // go through
                Entry<Vertex, Edge> entry = iterator.next();
                if (entry.getValue().getResidual() >= delta && !Visited.contains(entry.getKey())) { // if the residual >= delta && never visited it before, visit it
                    path.put(entry.getKey(), entry.getValue()); // update path
                    q.add(entry.getKey()); // add vertex to queue
                    Visited.add(entry.getKey()); // mark visited
                    if (entry.getKey().equals(target)) { // stop going through when find target node
                        break;
                    }
                }
            }
        }
        return Visited.contains(target);
    }

    // Update the graph according to the path and delta.
    private void graph_update(double delta) {
        printPath(); // print the s-t path. use it or not depends on you, this line will not affect max_flow
        Vertex v = target;
        while (!v.equals(source)) {             // go through the path from target to the source
            Edge e = path.get(v);               // the edge in path is w ------> v
            Vertex w = e.getOtherVertex(v);
            if (e.isFwdEdge()) {                // edge is forward, then add flow, the reverse edge decrease capacity
                e.addFlow(delta);
                if (!v.getIncidentEdges().keySet().contains(w)) {
                    Edge edge = new Edge(v, w, 0, e.getFlow(), false);
                    EdgeList.add(edge);
                    v.addEdge(edge);
                } else
                    v.to(w).addCapacity(delta);
            } else {                            // edge is backward, then add capacity, the reverse edge decrease delta
                e.addCapacity(delta);
                v.to(w).addFlow(-delta);
            }
            v = w;
        }
    }

    private void printPath() {
        Vertex v = target;
        LinkedList<String> res = new LinkedList<>();
        while (!v.equals(source)) {
            Edge e = path.get(v);
            Vertex w = e.getOtherVertex(v);
            res.addFirst(w.getName() + " -> " + v.getName() + ", residule is " + e.getResidual());
            v = w;
        }
        System.out.println("The simple s-t delta path is as follows");
        for (String temp : res) {
            System.out.println(temp);
        }
        System.out.println("\n");
    }

    public double getMaxFlow() {
        return maxFlow;
    }
    
    public void printFinalGraph() {
        System.out.println("the final flow is as following:");
        System.err.println("first      ->  second     : flow   /  capacity");
        for (Edge edge : EdgeList) {
            if (edge.isFwdEdge()) {
                System.out.printf("%-10s ->  %-10s :  %-5.1f /  %-5.1f \n", edge.getFirstNode().getName(),
                        edge.getSecondNode().getName(), edge.getFlow(), edge.getCapacity());
            }
        }
    }

    public static void main(String[] argv) throws FileNotFoundException {
        scalingFordFulkerson SFF = new scalingFordFulkerson("TestSet/BipartiteGraph/100l-90r-0.8maxPro-5minCapa-20maxCapa.txt");
        System.out.println("MaxFlow is " + SFF.getMaxFlow());
    }
}
