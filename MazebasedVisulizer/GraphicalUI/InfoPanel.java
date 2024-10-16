package GraphicalUI;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JOptionPane;


import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Cursor;
import Generic_Visulizer.AlgorithmProvider;


public class InfoPanel {
    private String algorithm; // Declare the algorithm variable
    private AlgorithmProvider provider;

    public InfoPanel(String algorithm, AlgorithmProvider provider) {
        this.algorithm = algorithm;
        this.provider = provider;
    }

    public void showMoreInfo() {
        System.out.println("showMoreInfo called");

        String selectedAlgorithm = provider.getSelectedAlgorithm();
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
        + "body { "
        + "    font-family: Arial, sans-serif; "
        + "    background-color: #f9f9f9; "
        + "    padding: 20px; "
        + "    border-radius: 10px; "
        + "    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1); "
        + "    margin: 0; "
        + "    display: flex; "
        + "    justify-content: center; "
        + "    align-items: center; "
        + "    min-height: 100vh; "
        + "} "
        + ".container { "
        + "    max-width: 600px; "
        + "    width: 100%; "
        + "    display: flex; "
        + "    flex-direction: column; "
        + "    align-items: center; "
        + "} "
        + ".section { "
        + "    margin: 20px 0; "
        + "    padding: 20px; "
        + "    background: #fff; "
        + "    border-radius: 5px; "
        + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); "
        + "    width: 100%; "
        + "    transition: transform 0.3s, box-shadow 0.3s; " // Add transition for hover effect
        + "} "
        + ".section:hover { "
        + "    transform: translateY(-5px); " // Lift effect on hover
        + "    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2); " // Enhanced shadow on hover
        + "} "
        + "h2 { color: #4CAF50; text-align: center; } "
        + "h3 { color: #2196F3; } "
        + "ul { list-style-type: square; padding-left: 20px; } "
        + "p { font-size: 14px; line-height: 1.6; } "
        + ".animation { text-align: center; margin-top: 20px; } "
        + ".animation img { max-width: 100%; height: auto; } " // Responsive image
        + "button { "
        + "    background-color: #4CAF50; "
        + "    color: white; "
        + "    border: none; "
        + "    padding: 10px 15px; "
        + "    cursor: pointer; "
        + "    border-radius: 5px; "
        + "    transition: background-color 0.3s, transform 0.3s; " // Button hover effect
        + "} "
        + "button:hover { "
        + "    background-color: #45a049; "
        + "    transform: scale(1.05); " // Slightly enlarge button on hover
        + "} "
        + "</style>"
      
        + "</head><body class='container'>";

    
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
    
    
        return info;
    }
}
