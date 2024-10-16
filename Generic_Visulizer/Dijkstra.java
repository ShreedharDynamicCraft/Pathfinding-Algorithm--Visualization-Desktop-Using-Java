package Generic_Visulizer;

import java.util.*;

public class Dijkstra {
    public static List<Node> findPath(Graph graph, Node start, Node end, List<Node> visitedNodes) {
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(nd -> nd.distance));
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();

        for (Node node : graph.getNodes()) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }
        distances.put(start, 0.0);
        pq.offer(new NodeDistance(start, 0.0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            Node currentNode = current.node;
            visitedNodes.add(currentNode);  // Add this line to track visited nodes

            if (currentNode == end) {
                break;
            }

            if (current.distance > distances.get(currentNode)) {
                continue;
            }

            for (Edge edge : currentNode.getEdges()) {
                Node neighbor = edge.getDestination();
                double newDistance = distances.get(currentNode) + getDistance(currentNode, neighbor);

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previousNodes.put(neighbor, currentNode);
                    pq.offer(new NodeDistance(neighbor, newDistance));
                }
            }
        }

        return reconstructPath(previousNodes, end);
    }

    private static double getDistance(Node a, Node b) {
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }

    private static List<Node> reconstructPath(Map<Node, Node> previousNodes, Node end) {
        List<Node> path = new ArrayList<>();
        Node current = end;
        while (current != null) {
            path.add(0, current);
            current = previousNodes.get(current);
        }
        return path;
    }

    private static class NodeDistance {
        Node node;
        double distance;

        NodeDistance(Node node, double distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}
