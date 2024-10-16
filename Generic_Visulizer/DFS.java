package Generic_Visulizer;


import java.util.*;

class DFS {
    public static List<Node> findPath(Graph graph, Node start, Node end, List<Node> visitedNodes) {
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parentMap = new HashMap<>();
        Stack<Node> stack = new Stack<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            visitedNodes.add(current);  // Add this line to track visited nodes


            if (current == end) {
                return reconstructPath(parentMap, start, end);
            }

            if (!visited.contains(current)) {
                visited.add(current);

                for (Edge edge : current.getEdges()) {
                    Node neighbor = edge.getDestination();
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                        parentMap.put(neighbor, current);
                    }
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
