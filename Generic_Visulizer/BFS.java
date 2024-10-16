
package Generic_Visulizer;

import java.util.*;


public class BFS {
    public static List<Node> findPath(Graph graph, Node start, Node end, List<Node> visitedNodes) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parentMap = new HashMap<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            visitedNodes.add(current);  // Add this line to track visited nodes
            if (current == end) {
                return reconstructPath(parentMap, start, end);
            }

            for (Edge edge : current.getEdges()) {
                Node neighbor = edge.getDestination();
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        return null; // No path found
    }

    private static List<Node> reconstructPath(Map<Node, Node> parentMap, Node start, Node end) {
        List<Node> path = new ArrayList<>();
        Node current = end;
        while (current != null) {
            path.add(0, current);
            if (current == start) break;
            current = parentMap.get(current);
        }
        return path;
    }
}
 
