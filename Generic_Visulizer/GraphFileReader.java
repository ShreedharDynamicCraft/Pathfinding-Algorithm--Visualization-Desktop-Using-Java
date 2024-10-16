package Generic_Visulizer;


import java.io.*;
import java.util.*;

import Common.CustomPopup;

public class GraphFileReader {
    public static Graph readFromFile(File file) throws IOException {
        Graph graph = new Graph();
        Map<String, Node> nodeMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String sourceId = parts[0];
                    String destId = parts[1];
                    double weight = Double.parseDouble(parts[2]);

                    Node source = nodeMap.computeIfAbsent(sourceId, id -> new Node(id, 0, 0));
                    Node dest = nodeMap.computeIfAbsent(destId, id -> new Node(id, 0, 0));

                    if (!graph.getNodes().contains(source)) {
                        graph.addNode(source);
                    }
                    if (!graph.getNodes().contains(dest)) {
                        graph.addNode(dest);
                    }

                    graph.addEdge(source, dest, weight);
                }
            }
            CustomPopup.showPopupMessage("Graph Generated Sucessfully",1100);

        }

        // Assign coordinates in a circular layout
        int centerX = 500;
        int centerY = 350;
        int radius = 200;
        List<Node> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            double angle = 2 * Math.PI * i / nodes.size();
            int x = centerX + (int)(radius * Math.cos(angle));
            int y = centerY + (int)(radius * Math.sin(angle));
            nodes.get(i).setX(x);
            nodes.get(i).setY(y);
        }

        return graph;
    }
}
