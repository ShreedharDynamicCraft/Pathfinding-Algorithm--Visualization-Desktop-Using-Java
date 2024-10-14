package Algo;

import Backend.Node;
import GraphicalUI.CustomPopup;
import GraphicalUI.Panel;
import Helper.SearchAlgo;

import static GraphicalUI.Panel.*;
import java.util.PriorityQueue;

public class aStar extends SearchAlgo {
    private final Node startNode;
    private final Node endNode;
    private final PriorityQueue<Node> openSet = new PriorityQueue<>();
    private Node currentNode;
    boolean pathFound = false;

    public aStar(Panel panel) {
        super(panel);
        this.startNode = START_NODE;
        this.endNode = END_NODE;
        this.currentNode = START_NODE;
    }

    public void find() {
        openSet.add(startNode);
        searchTime = 0;
        startTime = System.currentTimeMillis();
        while (!pathFound) {
            currentNode = openSet.poll();
            assert currentNode != null;
            currentNode.setAsChecked();
            for (Node neighbor : currentNode.getNeighbors()) {
                if (neighbor.isChecked() || neighbor.isOpen()) {
                    continue;
                }
                if (neighbor.equals(endNode)) {
                    pathFound = true;
                    endNode.setParent(currentNode);
                   System.out.println("Path found");
                   CustomPopup.showPopupMessage("Path Found SucessFully", 1100);

                //    tracePath();
                    break;
                }
                openNode(neighbor);
            }
            process();
            if (openSet.isEmpty()) {
                System.out.println("No path found");
                        CustomPopup.showPopupMessage("No Path Found", 1100);

                break;
            }
        }
    }

    public void openNode(Node node) {
        if (!node.isOpen() && !node.isChecked()) {
            node.setAsOpen();
            node.setParent(currentNode);
            openSet.add(node);
            numNodesExpanded++;
        }
    }

    protected void initializeNodes() {
        for(int i = 0; i < numRow; i++){
            for(int j = 0; j < numCol; j++){
                nodesGrid[i][j].setCost();
                nodesGrid[i][j].addNeighbors(false);
            }
        }
    }
}
