package Algorithms.Create;

import Util.Node;
import Visualization.Panel;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileMaze extends MazeAlgo {
    private final List<Node> nodesList = new ArrayList<>();

    private File selectedFile;
    private int numRow, numCol;
    private Node[][] nodesGrid;

    public FileMaze(Panel panel) {
        super(panel);
    }

    public void setSelectedFile(File file) {
        this.selectedFile = file;
    }

    @Override
    protected void generateMaze() {
        readMazeFromFile(selectedFile);
    }

    private void readMazeFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line != null) {
                String[] dimensions = line.split(" ");
                if (dimensions.length != 2) {
                    showError("Invalid maze dimensions.");
                    return;
                }
                numRow = Integer.parseInt(dimensions[0]);
                numCol = Integer.parseInt(dimensions[1]);

                nodesGrid = new Node[numRow][numCol];

                int row = 0;
                while ((line = br.readLine()) != null && row < numRow) {
                    for (int col = 0; col < numCol; col++) {
                        if (line.charAt(col) == '1') {
                            nodesGrid[row][col] = new Node(row, col, true); // Wall
                        } else {
                            nodesGrid[row][col] = new Node(row, col, false); // Path
                        }
                    }
                    row++;
                }
                
                if (row < numRow) {
                    showError("The maze file does not contain enough rows.");
                    return;
                }
                primMazeGeneration();
            } else {
                showError("The maze file is empty.");
            }
        } catch (IOException e) {
            showError("Error reading the maze file: " + e.getMessage());
        } catch (NumberFormatException e) {
            showError("Invalid number format in maze dimensions.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(panel, message, "Maze Generation Error", JOptionPane.ERROR_MESSAGE);
    }

    private void primMazeGeneration() {
        for (int row = 0; row < numRow; row++) {
            for (int col = 0; col < numCol; col++) {
                nodesGrid[row][col].addNeighbors(true);
            }
        }
        List<NodePairs> nodePairs = new ArrayList<>();
        Random random = new Random();
        Node startNode = nodesGrid[random.nextInt(numRow)][random.nextInt(numCol)];
        startNode.setAsVisited();

        for (Node node : startNode.getNeighbors()) {
            nodePairs.add(new NodePairs(startNode, node));
        }

        NodePairs currentPair;
        while (!nodePairs.isEmpty()) {
            currentPair = nodePairs.get(random.nextInt(nodePairs.size()));
            currentPair.getNode1().setBackgroundColor(Panel.CHECKED_COLOR);
            currentPair.getNode2().setBackgroundColor(Panel.CHECKED_COLOR);
            if ((currentPair.getNode1().isVisited() && currentPair.getNode2().isVisited())
                    || (!currentPair.getNode1().isVisited() && !currentPair.getNode2().isVisited())) {
                nodePairs.remove(currentPair);
                continue;
            }
            currentPair.getNode1().setType(Node.NodeType.VISITED);
            currentPair.getNode2().setType(Node.NodeType.VISITED);
            removeWall(currentPair.getNode1(), currentPair.getNode2());
            nodePairs.remove(currentPair);
            for (Node node : currentPair.getNode1().getNeighbors()) {
                nodePairs.add(new NodePairs(currentPair.getNode1(), node));
            }
            for (Node node : currentPair.getNode2().getNeighbors()) {
                nodePairs.add(new NodePairs(currentPair.getNode2(), node));
            }
            process();
        }
    }

    static class NodePairs {
        private final Node node1;
        private final Node node2;

        public NodePairs(Node node1, Node node2) {
            this.node1 = node1;
            this.node2 = node2;
        }

        public Node getNode1() {
            return node1;
        }

        public Node getNode2() {
            return node2;
        }
    }
}