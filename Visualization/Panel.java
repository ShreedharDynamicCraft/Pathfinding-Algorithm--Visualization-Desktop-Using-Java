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

    public void showMoreInfo() {
        String selectedAlgorithm = settingsPanel.getSelectedAlgorithm();
        String info = getDetailedAlgorithmInfo(selectedAlgorithm);
    
        JFrame infoFrame = new JFrame("Algorithm Details");
        infoFrame.setSize(650, 450);
        infoFrame.setLocationRelativeTo(null);
    
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(info);
        textPane.setEditable(false);
    
        JScrollPane scrollPane = new JScrollPane(textPane);
        infoFrame.add(scrollPane, BorderLayout.CENTER);
    
        JPanel buttonPanel = new JPanel();
    
        JButton openArticleButton = new JButton("Open Full Article");
        JButton watchVideoButton = new JButton("Watch YouTube Video");
    
        // Button customization
        for (JButton button : new JButton[]{openArticleButton, watchVideoButton}) {
            button.setBackground(new Color(52, 152, 219));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219)));
            button.setOpaque(true);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setFocusPainted(false);
            button.setPreferredSize(new Dimension(200, 40));
    
            // Hover effects
            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(new Color(41, 128, 185));
                }
                public void mouseExited(MouseEvent e) {
                    button.setBackground(new Color(52, 152, 219));
                }
                public void mousePressed(MouseEvent e) {
                    button.setBackground(new Color(34, 150, 189));
                }
                public void mouseReleased(MouseEvent e) {
                    button.setBackground(new Color(41, 128, 185));
                }
            });
        }
    
        // Add action listeners
        openArticleButton.addActionListener(e -> openArticleInBrowser(selectedAlgorithm));
        watchVideoButton.addActionListener(e -> openYouTubeVideo(selectedAlgorithm));
    
        buttonPanel.add(openArticleButton);
        buttonPanel.add(watchVideoButton);
        infoFrame.add(buttonPanel, BorderLayout.SOUTH);
        infoFrame.setVisible(true);
    }
  

    private void addButtonHoverEffect(JButton button) {
        button.setBackground(Color.LIGHT_GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.GRAY);
            }
    
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.LIGHT_GRAY);
            }
        });
    }


    private void openYouTubeVideo(String algorithm) {
        String videoUrl = switch (algorithm) {
            case "Dijkstra" -> "https://www.youtube.com/watch?v=XB4MIexjvY0";
            case "BFS" -> "https://youtu.be/-tgVpUgsQ5k?si=9VL9qtw6h549WS2e";
            case "DFS" -> "https://youtu.be/Qzf1a--rhp8?si=FIyHTSBzvOwGdOpL";
            case "Recursive Backtracking" -> "https://youtu.be/BJEjmtN8U9g?si=TPJ0vq6fTdltO6zv";
            case "A*" -> "https://www.youtube.com/watch?v=tvAh0JZF2YE"; // A* YouTube link
            default -> "";
        };
    
        openUrlInBrowser(videoUrl);
    }
    
    private void openArticleInBrowser(String algorithm) {
        String url = switch (algorithm) {
            case "Dijkstra" -> "https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/";
            case "BFS" -> "https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/";
            case "DFS" -> "https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/";
            case "Recursive Backtracking" -> "https://www.geeksforgeeks.org/backtracking-algorithms/";
            case "A*" -> "https://www.geeksforgeeks.org/a-search-algorithm/"; // A* article link
            default -> "";
        };
    
        openUrlInBrowser(url);
    }
    
    private void openUrlInBrowser(String url) {
        if (!url.isEmpty()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
                JOptionPane.showMessageDialog(null, "Unable to open the browser: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    

    private String getDetailedAlgorithmInfo(String algorithm) {
        String info = "<html><head>"
                + "<style>"
                + "body { width: 600px; font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; border-radius: 10px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.1); }"
                + "h2 { color: #4CAF50; text-align: center; }"
                + "h3 { color: #2196F3; }"
                + "ul { list-style-type: square; padding-left: 20px; }"
                + "p { font-size: 14px; line-height: 1.6; }"
                + ".container { display: flex; flex-direction: column; align-items: center; }"
                + ".section { margin: 20px 0; padding: 10px; background: #fff; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 100%; }"
                + ".animation { text-align: center; margin-top: 20px; }"
                + ".animation img { max-width: 100%; height: auto; }"  // Make the animation responsive
                + "button { background-color: #4CAF50; color: white; border: none; padding: 10px 15px; cursor: pointer; border-radius: 5px; transition: background-color 0.3s; }"
                + "button:hover { background-color: #45a049; }"
                + "</style>"
                + "<script>"
                + "function printPage() { window.print(); }"
                + "</script></head><body class='container'>";
    
        switch (algorithm) {
            case "Dijkstra":
                info += "<div class='section'><h2>Dijkstra's Algorithm</h2>"
                    + "<p>Dijkstra's algorithm finds the shortest path from a source node to all other nodes in a graph with non-negative edge weights.</p>"
                    + "<h3>Key Features:</h3>"
                    + "<ul>"
                    + "<li>Maintains a priority queue of nodes based on the shortest distance from the source.</li>"
                    + "<li>Finalizes the shortest path to a node once it is visited.</li>"
                    + "<li>Can handle both directed and undirected graphs.</li>"
                    + "</ul>"
                    + "<h3>Applications:</h3>"
+ "<ul>"
+ "    <li>GPS Navigation Systems: Finding the shortest path between two locations in real-time.</li>"
+ "    <li>Network Routing Protocols: Optimizing data flow paths in networks like OSPF.</li>"
+ "    <li>Robotics Pathfinding: Used in robotics to navigate within known environments.</li>"
+ "</ul>"

                    + "<h3>Pseudo-code:</h3>"
                    + "<pre><code>"
                    + "1. function Dijkstra(Graph, source):\n"
                    + "2.   dist[source] = 0\n"
                    + "3.   for each vertex v in Graph:\n"
                    + "4.     if v ≠ source then dist[v] = infinity\n"
                    + "5.   Q = all vertices in Graph\n"
                    + "6.   while Q is not empty:\n"
                    + "7.     u = vertex in Q with min dist[u]\n"
                    + "8.     remove u from Q\n"
                    + "9.     for each neighbor v of u:\n"
                    + "10.      alt = dist[u] + length(u, v)\n"
                    + "11.      if alt < dist[v]:\n"
                    + "12.        dist[v] = alt\n"
                    + "</code></pre>"
                    + "<h3>Time Complexity:</h3>"
                    + "<p>O((V + E) log V) where V is the number of vertices and E is the number of edges. The algorithm leverages a priority queue which determines the logarithmic factor.</p>"
                    + "<h3>Space Complexity:</h3>"
                    + "<p>O(V), due to storing the distances and priority queue.</p>"
                    + "<div class='animation'>"
                    + "<img src='https://miro.medium.com/v2/resize:fit:828/format:webp/1*ziKCd9F5F62n0azJ2d8ssw.gif' alt='Dijkstra Animation'/>"
                    // + "<p>Video Explanation: <a href='https://www.youtube.com/watch?v=XB4MIexjvY0' target='_blank' style='color: #2196F3; text-decoration: none;'>Watch on YouTube</a></p>"
                    + "</div></div>";
                break;
    
            case "BFS":
                info += "<div class='section'><h2>Breadth-First Search (BFS)</h2>"
                    + "<p>BFS is a traversal algorithm for graphs and trees that explores neighbors before moving to the next level of nodes.</p>"
                    + "<h3>Key Features:</h3>"
                    + "<ul>"
                    + "<li>Utilizes a queue to track nodes for exploration.</li>"
                    + "<li>Ensures the shortest path in unweighted graphs.</li>"
                    + "<li>Effective for finding connected components.</li>"
                    + "</ul>"
                    + "<h3>Applications:</h3>"
+ "<ul>"
+ "    <li>Web Crawling: Exploring websites and finding all the links from a given starting point.</li>"
+ "    <li>Social Networking Applications: Finding the shortest connection between two users in a social network.</li>"
+ "    <li>Broadcasting in Networks: Propagating data to all nodes in a network, such as spreading information.</li>"
+ "</ul>"

                    + "<h3>Pseudo-code:</h3>"
                    + "<pre><code>"
                    + "1. function BFS(Graph, start):\n"
                    + "2.   let queue = empty queue\n"
                    + "3.   queue.enqueue(start)\n"
                    + "4.   while queue is not empty:\n"
                    + "5.     node = queue.dequeue()\n"
                    + "6.     for each neighbor in node.neighbors:\n"
                    + "7.       if neighbor not visited:\n"
                    + "8.         mark neighbor as visited\n"
                    + "9.         queue.enqueue(neighbor)\n"
                    + "</code></pre>"
                    + "<h3>Time Complexity:</h3>"
                    + "<p>O(V + E) where V is the number of vertices and E is the number of edges, as each vertex and edge is explored exactly once.</p>"
                    + "<h3>Space Complexity:</h3>"
                    + "<p>O(V), due to storing vertices in the queue.</p>"
                    + "<div class='animation'>"
                    + "<img src='https://aquarchitect.github.io/swift-algorithm-club/Breadth-First%20Search/Images/AnimatedExample.gif' alt='BFS Animation'/>"
                    // + "<p>Video Explanation: <a href='https://www.youtube.com/watch?v=0uBz6TLgM4M' target='_blank' style='color: #2196F3; text-decoration: none;'>Watch on YouTube</a></p>"
                    + "</div></div>";
                break;
    
            case "DFS":
                info += "<div class='section'><h2>Depth-First Search (DFS)</h2>"
                    + "<p>DFS is an algorithm for traversing or searching tree or graph data structures, exploring as far down a branch as possible before backtracking.</p>"
                    + "<h3>Key Features:</h3>"
                    + "<ul>"
                    + "<li>Uses a stack (or recursion) to keep track of nodes.</li>"
                    + "<li>Can find all paths or detect cycles.</li>"
                    + "<li>Ideal for problems like maze solving and puzzle games.</li>"
                    + "</ul>"
                    + "<h3>Applications:</h3>"
+ "<ul>"
+ "    <li>Topological Sorting: Used to order tasks in sequence in problems like job scheduling.</li>"
+ "    <li>Finding Connected Components: Identifying distinct subgraphs within an undirected graph.</li>"
+ "    <li>Pathfinding in Games: Used for exploring possible moves in games like chess or solving mazes.</li>"
+ "</ul>"

                    + "<h3>Pseudo-code:</h3>"
                    + "<pre><code>"
                    + "1. function DFS(Graph, start):\n"
                    + "2.   let stack = empty stack\n"
                    + "3.   stack.push(start)\n"
                    + "4.   while stack is not empty:\n"
                    + "5.     node = stack.pop()\n"
                    + "6.     for each neighbor in node.neighbors:\n"
                    + "7.       if neighbor not visited:\n"
                    + "8.         mark neighbor as visited\n"
                    + "9.         stack.push(neighbor)\n"
                    + "</code></pre>"
                    + "<h3>Time Complexity:</h3>"
                    + "<p>O(V + E) where V is the number of vertices and E is the number of edges.</p>"
                    + "<h3>Space Complexity:</h3>"
                    + "<p>O(V), due to recursion or the use of a stack.</p>"
                    + "<div class='animation'>"
                    + "<img src='https://upload.wikimedia.org/wikipedia/commons/7/7f/Depth-First-Search.gif' alt='DFS Animation'/>"
                    + "</div></div>";
                break;

                case "Recursive Backtracking":
            info += "<div class='section'><h2>Recursive Backtracking</h2>"
                + "<p>Recursive Backtracking is a technique used to solve problems incrementally by building partial solutions and backtracking when a partial solution is not feasible.</p>"
                + "<h3>Key Features:</h3>"
                + "<ul>"
                + "<li>Explores all possible solutions by making choices one at a time.</li>"
                + "<li>Uses recursion to try and backtrack when no valid option is available.</li>"
                + "<li>Commonly used for puzzles, permutations, combinations, and constraint satisfaction problems.</li>"
                + "</ul>"

                + "<ul>"
                + "    <li>N-Queens Problem: Placing N queens on a chessboard such that no two queens threaten each other.</li>"
                + "    <li>Sudoku Solver: Filling a Sudoku grid while ensuring each row, column, and sub-grid contains unique numbers.</li>"
                + "    <li>Maze Solving: Finding a path through a maze by exploring all possible routes.</li>"
                + "    <li>Combinatorial Optimization: Solving problems like the Traveling Salesman Problem using brute-force methods.</li>"
                + "    <li>String Permutations: Generating all permutations of a given string or set of characters.</li>"
                + "</ul>"
                + "<h3>Pseudo-code:</h3>"
                + "<pre><code>"
                + "1. function Backtrack(candidates, solution):\n"
                + "2.   if solution is complete:\n"
                + "3.     return solution\n"
                + "4.   for each candidate in candidates:\n"
                + "5.     if candidate is valid:\n"
                + "6.       solution.add(candidate)\n"
                + "7.       Backtrack(candidates, solution)\n"
                + "8.       solution.remove(candidate)\n"
                + "</code></pre>"
                + "<h3>Time Complexity:</h3>"
                + "<p>O(b^d), where b is the branching factor and d is the depth of the solution tree. The complexity grows exponentially in the worst case.</p>"
                + "<h3>Space Complexity:</h3>"
                + "<p>O(d), due to the recursion depth.</p>"
                + "<div class='animation'>"
                + "<img src='https://i.gifer.com/Ngsm.gif' alt='Recursive Backtracking Animation'/>"
                + "<p>Video Explanation: <a href='https://www.youtube.com/watch?v=d1rXcJuEsy0' target='_blank'>Watch on YouTube</a></p>"
                + "</div></div>";
            break;
    
            case "A*":
                info += "<div class='section'><h2>A* Search Algorithm</h2>"
                    + "<p>A* is a pathfinding and graph traversal algorithm known for its efficiency and optimality by using heuristics.</p>"
                    + "<h3>Key Features:</h3>"
                    + "<ul>"
                    + "<li>Combines elements of Dijkstra's Algorithm and Greedy Best-First Search.</li>"
                    + "<li>Uses a heuristic function to estimate the cost of the cheapest path.</li>"
                    + "<li>Guarantees the shortest path if the heuristic is admissible (never overestimates).</li>"
                    + "</ul>"

+ "<h3>Applications:</h3>"
+ "<ul>"
+ "    <li>Pathfinding in Games: Used in video games for characters to find the shortest path between two points.</li>"
+ "    <li>GPS Navigation Systems: Finding the shortest or fastest route between locations based on road networks.</li>"
+ "    <li>Robotics: Helps autonomous robots navigate through environments avoiding obstacles.</li>"
+ "    <li>AI Planning: Used in artificial intelligence for planning optimal sequences of actions.</li>"
+ "</ul>"

           + "<h3>Heuristic:</h3>"
                + "<p>The heuristic function h(x) estimates the cost from node x to the goal. For example, the straight-line distance in map navigation.</p>"
                    + "<h3>Pseudo-code:</h3>"
                    + "<pre><code>"
                    + "1. function A*(Graph, start, goal):\n"
                    + "2.   openSet = {start}\n"
                    + "3.   while openSet is not empty:\n"
                    + "4.     current = node in openSet with lowest fScore\n"
                    + "5.     if current == goal:\n"
                    + "6.       return reconstruct_path(cameFrom, current)\n"
                    + "7.     openSet.remove(current)\n"
                    + "8.     for each neighbor of current:\n"
                    + "9.       tentative_gScore = gScore[current] + dist(current, neighbor)\n"
                    + "10.      if tentative_gScore < gScore[neighbor]:\n"
                    + "11.        cameFrom[neighbor] = current\n"
                    + "12.        gScore[neighbor] = tentative_gScore\n"
                    + "13.        fScore[neighbor] = gScore[neighbor] + heuristic(neighbor, goal)\n"
                    + "</code></pre>"
                    + "<h3>Time Complexity:</h3>"
                    + "<p>O(E), where E is the number of edges. The exact complexity depends on the quality of the heuristic.</p>"
                    + "<h3>Space Complexity:</h3>"
                    +"<p>An example of an A* algorithm in action where nodes are cities connected with roads and h(x) is the straight-line distance to the target point:</p>"
                    + "<p>O(V), due to storing the path and open/closed sets.</p>"
                    + "<div class='animation'>"
                    + "<img src='https://upload.wikimedia.org/wikipedia/commons/9/98/AstarExampleEn.gif' alt='A* Algorithm Animation'/>"
                    +"<p>Key: green: start; blue: goal; orange: visited </p>"
                    + "</div></div>";
                break;
    
            default:
                info += "<p>Algorithm not found.</p>";
                break;
        }
    
        info += "<button onclick='printPage()'>Print</button></body></html>";
    
        return info;
    }
    

    
    public void setDarkMode(boolean isDarkMode) {
        this.isDarkMode = isDarkMode; // Set the instance variable
        setBackground(isDarkMode ? Color.DARK_GRAY : Color.WHITE);
        // repaint(); // Refresh the panel to apply the new background
        
    }



    
    

}


