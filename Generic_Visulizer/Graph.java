package Generic_Visulizer;


import java.util.*;

public class Graph {
    private List<Node> nodes;

    public Graph() {
        nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }
    

    public void addEdge(Node source, Node destination, double weight) {
        source.addEdge(new Edge(source, destination, weight));
        destination.addEdge(new Edge(destination, source, weight)); // For undirected graph
    }

    public List<Node> getNodes() {
        return nodes;
    }
}

class Edge {
    private Node source;
    private Node destination;
    private double weight;

    public Edge(Node source, Node destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Node getSource() { return source; }
    public Node getDestination() { return destination; }
    public double getWeight() { return weight; }
}
 class Node {
    private String id;
    private int x;
    private int y;
    private List<Edge> edges;

    public Node(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    

    public String getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public List<Edge> getEdges() { return edges; }
}
