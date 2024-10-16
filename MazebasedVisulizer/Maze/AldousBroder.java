package Maze;

import Backend.Node;
import GraphicalUI.Panel;
import Helper.MazeAlgo;

import static GraphicalUI.Panel.*;
import java.util.ArrayList;
import java.util.Random;

public class AldousBroder extends MazeAlgo{
    public AldousBroder(Panel panel) {
        super(panel);
    }

    @Override
    protected void generateMaze() {
        ArrayList<Node> nodesList = new ArrayList<>();
        for (int row = 0; row < numRow; row++) {
            for (int col = 0; col < numCol; col++) {
                nodesList.add(nodesGrid[row][col]);
            }
        }
        Random random = new Random();
        Node currentNode = nodesList.get(random.nextInt(nodesList.size()));
        while (!nodesList.isEmpty()) {
            currentNode.setType(Node.NodeType.VISITED);
            nodesList.remove(currentNode);
            Node neighbor = currentNode.getNeighbors().get(random.nextInt(currentNode.getNeighbors().size()));
            if(!neighbor.istVisited()) {
                removeWall(currentNode, neighbor);
            }
            currentNode.setBackgroundColor(PATH_COLOR);
            process();
            currentNode.setType(Node.NodeType.VISITED);
            currentNode = neighbor;
        }
    }
}
