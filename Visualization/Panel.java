package Visualization;

import Util.Node;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Panel extends JPanel {
    public static Color BACKGROUND_COLOR = Color.WHITE;
    public static Color WALKABLE_COLOR = Color.WHITE;
    public static Color WALL_COLOR = new Color(0, 0, 0, 200);
    public static Color START_COLOR = new Color(255, 165, 0, 100);
    public static Color END_COLOR = new Color(121, 231, 90, 100);
    public static Color CHECKED_COLOR = new Color(0, 255, 255, 100);
    public static Color VISITED_COLOR = new Color(255, 236, 166, 100);
    public static Color PATH_COLOR = new Color(113, 39, 173, 255);
    public final SettingsPanel settingsPanel = new SettingsPanel(this);
    private boolean isDarkMode = false;

    public boolean isDarkMode() {
        return isDarkMode;
    }

    public static int WIDTH = 1500;
    public static int HEIGHT = 900;
    public static int SETTINGS_WIDTH = 400;
    public static int SETTINGS_HEIGHT = HEIGHT;
    public static int GRID_WIDTH = WIDTH - SETTINGS_WIDTH;
    public static int GRID_HEIGHT = HEIGHT;
    public static int nodeSize = 20;
    public static int numRow = GRID_HEIGHT / nodeSize;
    public static int numCol = GRID_WIDTH / nodeSize;
    public static int solvingSpeed = 50;
    public static int pathLength = 0;
    public static int numNodesExpanded = 0;
    public static long searchTime = 0;
    public static boolean drawPath = true;

    public static Node START_NODE = Node.start;
    public static Node END_NODE = Node.end;
    public static Node[][] nodesGrid = new Node[numRow][numCol];
    public static ArrayList<Node> pathNodes = new ArrayList<>();
    private int[][] maze;

    private int rows;
    private int cols;


    public Panel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(BACKGROUND_COLOR);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.add(settingsPanel);

        settingsPanel.setBounds(GRID_WIDTH, 0, SETTINGS_WIDTH, SETTINGS_HEIGHT);

 
        
        createNodes();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = e.getX();
                    int y = e.getY();
                    int row = y / nodeSize;
                    int col = x / nodeSize;
                    if (row < numRow && col < numCol) {
                        Panel.this.mouseClicked(row, col);
                    }
                }
            }
        });
    }

    public void createNodes() {
        for (int row = 0; row < numRow; row++) {
            for (int col = 0; col < numCol; col++) {
                nodesGrid[row][col] = new Node(row, col, true);
            }
        }
        for (int row = 0; row < numRow; row++) {
            for (int col = 0; col < numCol; col++) {
                nodesGrid[row][col].addNeighbors(true);
            }
        }
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        numRow = rows;
        numCol = cols;
        createNodes();
        repaint();
    }
 
    public void drawNodes(Graphics2D g2d) {
        if (drawPath && nodeSize >= 7) {
            for (int row = 0; row < numRow; row++) {
                for (int col = 0; col < numCol; col++) {
                    nodesGrid[row][col].draw(g2d);
                }
            }
            if (!pathNodes.isEmpty()) {
                ArrayList<Node> copyPathNodes = new ArrayList<>(pathNodes);
                for (Node node : copyPathNodes) {
                    g2d.setColor(PATH_COLOR);
                    drawPath(node.getRect().getCenterX(), node.getRect().getCenterY(),
                    node.getParent().getRect().getCenterX(), node.getParent().getCenterY(), g2d);
                }
            }
        } else {
            ArrayList<Node> copyPathNodes = new ArrayList<>(pathNodes);
            for (Node node : copyPathNodes) {
                node.setBackgroundColor(PATH_COLOR);
            }
            for (int row = 0; row < numRow; row++) {
                for (int col = 0; col < numCol; col++) {
                    nodesGrid[row][col].draw(g2d);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawNodes(g2d);
        updateInfo();
    }

    public void resetPanel() {
        numCol = GRID_WIDTH / nodeSize;
        numRow = GRID_HEIGHT / nodeSize;
        nodesGrid = new Node[numRow][numCol];
        pathNodes.clear();
        Node.start = null;
        Node.end = null;
        START_NODE = Node.start;
        END_NODE = Node.end;
        createNodes();
        repaint();
    }

    private void mouseClicked(int row, int col) {
        nodesGrid[row][col].changeType();
        drawNodes(this.getGraphics2D());
        repaint();
    }

    public Graphics2D getGraphics2D() {
        return (Graphics2D) this.getGraphics();
    }

    public void updateInfo() {
        String info = "<html>";
        info += "<div style='font-family: Arial, sans-serif; color: #333; border: 2px solid #4CAF50; border-radius: 10px; padding: 20px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3); transition: transform 0.3s;'>";
        info += "<h2 style='color: #4CAF50; text-align: center;'>Pathfinding Algorithm Information</h2>";
        info += "<p style='background-color: #f9f9f9; padding: 15px; border-radius: 8px; animation: fadeIn 0.5s;'>";
        info += "<strong>Grid Size:</strong> " + numRow + " x " + numCol + "<br>";
    
        info += displayNodeInfo("Start Node:", START_NODE, "#2196F3");
        info += displayNodeInfo("End Node:", END_NODE, "#FF9800");
    
        info += "<strong>Solving Speed:</strong> <span style='color: #673AB7;'>" + solvingSpeed + " fps</span><br>";
        info += "<strong>Path Length:</strong> <span style='color: #3F51B5;'>" + pathLength + "</span><br>";
        info += "<strong>Nodes Expanded:</strong> <span style='color: #009688;'>" + numNodesExpanded + "</span><br>";
        info += "<strong>Search Time:</strong> <span style='color: #E91E63;'>" + searchTime + " ms</span><br>";
    
        String selectedAlgorithm = settingsPanel.getSelectedAlgorithm();
        info += getAlgorithmComplexity(selectedAlgorithm);
    
        info += "<h3 style='color: #FF5722;'>Key:</h3>";
        info += "<strong>V:</strong> Number of vertices (nodes)<br>";
        info += "<strong>E:</strong> Number of edges (connections)<br>";
        info += "<strong>N:</strong> Number of cells in the maze<br>";
    
        info += "</p></div></html>";
    
        settingsPanel.setInfo(info);
        repaint();
    }
    
    private String displayNodeInfo(String label, Node node, String color) {
        String nodeInfo = "<strong>" + label + ":</strong> ";
        if (node != null) {
            nodeInfo += "<span style='color: " + color + "; font-weight: bold;'>" + node + "</span><br>";
        } else {
            nodeInfo += "<span style='color: #FF5722;'>none</span><br>";
        }
        return nodeInfo;
    }
    
    private String getAlgorithmComplexity(String selectedAlgorithm) {
        String complexityInfo = "<h3 style='color: #FF5722;'>Algorithm Complexity</h3>";
        if (selectedAlgorithm.equals("Dijkstra")) {
            complexityInfo += getAlgorithmInfo("Dijkstra's Algorithm", "O((V + E) log V)", "O(V)");
        } else if (selectedAlgorithm.equals("BFS")) {
            complexityInfo += getAlgorithmInfo("Breadth-First Search (BFS)", "O(V + E)", "O(V)");
        } else if (selectedAlgorithm.equals("DFS")) {
            complexityInfo += getAlgorithmInfo("Depth-First Search (DFS)", "O(V + E)", "O(V)");
        } else if (selectedAlgorithm.equals("A*")) {
            complexityInfo += getAlgorithmInfo("A* Algorithm", "O(E)", "O(V)");
        } else if (selectedAlgorithm.equals("Recursive Backtracking")) {
            complexityInfo += getAlgorithmInfo("Recursive Backtracking", "O(2^N)", "O(N)");
        }
        return complexityInfo;
    }
    
    private String getAlgorithmInfo(String algorithmName, String timeComplexity, String spaceComplexity) {
        return "<div style='margin-top: 10px; border: 1px solid #4CAF50; padding: 10px; border-radius: 8px; background-color: #e8f5e9; transition: background-color 0.3s;'>"
                + "<strong>" + algorithmName + "</strong><br>"
                + "<strong>Time Complexity:</strong> " + timeComplexity + "<br>"
                + "<strong>Space Complexity:</strong> " + spaceComplexity + "<br>"
                + "</div>";
    }

    private void drawPath(double x, double y, double px, double py, Graphics2D g2d) {
        int width = (int) Math.abs(x - px);
        int height = (int) Math.abs(y - py);
        int rectX = (int) Math.min(x, px);
        int rectY = (int) Math.min(y, py);

        width = width == 0 ? nodeSize / 3 : nodeSize + nodeSize / 3;
        height = height == 0 ? nodeSize / 3 : nodeSize + nodeSize / 3;
        g2d.fillRect(rectX, rectY, width, height);
    }

    public void setMazeDimensions(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        System.out.println("Maze dimensions set to: " + rows + "x" + cols);
    }

    public void toggleMode() {
        isDarkMode = !isDarkMode;

        BACKGROUND_COLOR = isDarkMode ? Color.BLACK : Color.WHITE;
        WALKABLE_COLOR = isDarkMode ? Color.BLACK : Color.WHITE;
        WALL_COLOR = isDarkMode ? Color.BLUE : new Color(0, 0, 0, 200);

        setBackground(BACKGROUND_COLOR);
        repaint();
    }

   

    
    public void setDarkMode(boolean isDarkMode) {
        this.isDarkMode = isDarkMode; // Set the instance variable
        setBackground(isDarkMode ? Color.DARK_GRAY : Color.WHITE);
        // repaint(); // Refresh the panel to apply the new background
        
    }



}

