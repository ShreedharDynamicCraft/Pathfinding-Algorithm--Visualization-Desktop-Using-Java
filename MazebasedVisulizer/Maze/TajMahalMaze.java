package Maze;

import Backend.Node;
import GraphicalUI.Panel;
import Helper.MazeAlgo;

import static GraphicalUI.Panel.*;
import java.util.Random;

public class TajMahalMaze extends MazeAlgo {
    public TajMahalMaze(Panel panel) {
        super(panel);
    }

    @Override
    protected void generateMaze() {
        for (int row = 0; row < numRow; row++) {
            for (int col = 0; col < numCol; col++) {
                // Create walls based on a symmetrical pattern
                if ((row % 3 == 0 && col % 2 == 0) || (col == numCol / 2)) {
                    nodesGrid[row][col].setType(Node.NodeType.WALL);
                } else {
                    nodesGrid[row][col].setType(Node.NodeType.PATH);
                }
            }
        }

        // Create pathways to mimic the gardens of the Taj Mahal
        for (int row = 1; row < numRow - 1; row++) {
            for (int col = 1; col < numCol - 1; col++) {
                if (new Random().nextBoolean()) {
                    removeWall(nodesGrid[row][col], nodesGrid[row + 1][col]);
                }
                if (new Random().nextBoolean()) {
                    removeWall(nodesGrid[row][col], nodesGrid[row][col + 1]);
                }
            }
        }
    }
}
