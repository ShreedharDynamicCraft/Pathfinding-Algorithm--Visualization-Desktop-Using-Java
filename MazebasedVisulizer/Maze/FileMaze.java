package Maze;

import Backend.Node;
import GraphicalUI.Panel;
import Helper.MazeAlgo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class FileMaze extends MazeAlgo {
    private File selectedFile;

    public FileMaze(Panel panel, File file) {
        super(panel);
        this.selectedFile = file;
    }

    @Override
    protected void generateMaze() {
        if (selectedFile != null) {
            Node[][] nodesGrid = readMazeFromFile(selectedFile);
            if (nodesGrid != null) {
                panel.setMaze(nodesGrid);
            }
        } else {
            showError("No file selected for maze generation.");
        }
    }

    private Node[][] readMazeFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line != null) {
                String[] dimensions = line.split(" ");
                int numRow = Integer.parseInt(dimensions[0]);
                int numCol = Integer.parseInt(dimensions[1]);

                Node[][] nodesGrid = new Node[numRow][numCol];

                int row = 0;
                while ((line = br.readLine()) != null && row < numRow) {
                    for (int col = 0; col < numCol; col++) {
                        nodesGrid[row][col] = new Node(row, col, line.charAt(col) == '1');
                    }
                    row++;
                }

                if (row < numRow) {
                    showError("The maze file does not contain enough rows.");
                    return null;
                } else {
                    return nodesGrid;
                }
            } else {
                showError("The maze file is empty.");
                return null;
            }
        } catch (IOException e) {
            showError("Error reading the maze file: " + e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            showError("Invalid number format in maze dimensions.");
            return null;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(panel, message, "Maze Generation Error", JOptionPane.ERROR_MESSAGE);
    }
}
