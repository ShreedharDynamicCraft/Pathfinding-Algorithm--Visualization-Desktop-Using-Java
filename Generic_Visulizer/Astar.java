package Generic_Visulizer;


import java.util.*;

public class Astar {
    public static List<Node> findPath(Graph graph, Node start, Node end, List<Node> visitedNodes) {
        PriorityQueue<NodeFScore> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fScore));
        Set<Node> closedSet = new HashSet<>();
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Double> gScore = new HashMap<>();
        Map<Node, Double> fScore = new HashMap<>();

        gScore.put(start, 0.0);
        fScore.put(start, heuristic(start, end));
        openSet.offer(new NodeFScore(start, fScore.get(start)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll().node;
            visitedNodes.add(current);  // Add this line to track visited nodes

            if (current == end) {
                return reconstructPath(cameFrom, end);
            }

            closedSet.add(current);

            for (Edge edge : current.getEdges()) {
                Node neighbor = edge.getDestination();
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                double tentativeGScore = gScore.get(current) + getDistance(current, neighbor);

                if (!openSet.contains(new NodeFScore(neighbor, 0)) || tentativeGScore < gScore.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    double f = gScore.get(neighbor) + heuristic(neighbor, end);
                    fScore.put(neighbor, f);

                    if (!openSet.contains(new NodeFScore(neighbor, f))) {
                        openSet.offer(new NodeFScore(neighbor, f));
                    }
                }
            }
        }

        return null; // No path found
    }

    private static double heuristic(Node a, Node b) {
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }

    private static double getDistance(Node a, Node b) {
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }

    private static List<Node> reconstructPath(Map<Node, Node> cameFrom, Node end) {
        List<Node> path = new ArrayList<>();
        Node current = end;
        while (current != null) {
            path.add(0, current);
            current = cameFrom.get(current);
        }
        return path;
    }

    private static class NodeFScore {
        Node node;
        double fScore;

        NodeFScore(Node node, double fScore) {
            this.node = node;
            this.fScore = fScore;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeFScore that = (NodeFScore) o;
            return node.equals(that.node);
        }

        @Override
        public int hashCode() {
            return Objects.hash(node);
        }
    }
}
