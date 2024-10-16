package Generic_Visulizer;
import GraphicalUI.InfoPanel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Common.CustomPopup;
import Generic_Visulizer.AlgorithmProvider;


public class GraphPathfindingVisualizer extends JPanel implements AlgorithmProvider {
    private JPanel controlPanel, graphPanel;
    private JComboBox<String> algorithmSelector;
    private JButton runButton, loadButton, clearButton, selectStartButton, selectEndButton;
    private JTextArea outputArea;
    private Graph graph;
    private Node startNode, endNode;
    private List<Node> path;
    private String currentAlgorithm;
    private boolean selectingStart = false;
    private boolean selectingEnd = false;
    private final JButton getMoreInfoButton = new JButton("View Algorithm Info");
    private final InfoPanel infoPanel;
    private final JButton helpButton = new JButton("Help");


    private final JLabel speedLabel = new JLabel("Animation Speed");
    private final JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 30, 10);
    private final JLabel speedValue = new JLabel("10 fps");
    public GraphPathfindingVisualizer() {
        setLayout(new BorderLayout());
        graph = new Graph();

        initializeComponents();
        layoutComponents();

        infoPanel = new InfoPanel((String) algorithmSelector.getSelectedItem(), this);

    }
    
    @Override
    public String getSelectedAlgorithm() {
        return (String) algorithmSelector.getSelectedItem();
    }


    private void initializeComponents() {
        graphPanel = new GraphPanel();
        String[] algorithms = {"Dijkstra", "A*", "BFS", "DFS"};
        algorithmSelector = new JComboBox<>(algorithms);
        runButton = new JButton("Run Algorithm");
        loadButton = new JButton("Load from File");
        clearButton = new JButton("Clear Graph");
        selectStartButton = new JButton("Select Start Node");
        selectEndButton = new JButton("Select End Node");

        outputArea = new JTextArea(5, 20);
        outputArea.setEditable(false);

        runButton.addActionListener(e -> runAlgorithm());
        loadButton.addActionListener(e -> loadFromFile());
        clearButton.addActionListener(e -> clearGraph());
        selectStartButton.addActionListener(e -> {
            selectingStart = true;
            selectingEnd = false;
        });
        selectEndButton.addActionListener(e -> {
            selectingEnd = true;
            selectingStart = false;
        });
        getMoreInfoButton.addActionListener(e -> {
            if (infoPanel != null) {
                infoPanel.showMoreInfo();
            }
        });
        helpButton.addActionListener(e -> showHelpDialog());


        setupSliders();
        styleComponents();
    }

    private void setupSliders() {
        speedSlider.setMajorTickSpacing(30);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setToolTipText("Animation Speed (fps)");
        speedSlider.addChangeListener(e -> {
            int fps = speedSlider.getValue();
            speedValue.setText(fps + " fps");
        });
        speedSlider.setBackground(Color.pink);
        speedSlider.setForeground(Color.BLUE);
    }

    private void styleComponents() {
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        speedLabel.setFont(labelFont);
        speedValue.setFont(labelFont);
        algorithmSelector.setFont(buttonFont);
        runButton.setFont(buttonFont);
        loadButton.setFont(buttonFont);
        clearButton.setFont(buttonFont);
        selectStartButton.setFont(buttonFont);
        selectEndButton.setFont(buttonFont);
        getMoreInfoButton.setFont(buttonFont);

        JComponent[] components = {speedSlider, algorithmSelector, runButton, loadButton, 
                                   clearButton, selectStartButton, selectEndButton,getMoreInfoButton,helpButton};
        for (JComponent component : components) {
            component.setMaximumSize(new Dimension(Integer.MAX_VALUE, component.getPreferredSize().height));
        }
    }

    private void layoutComponents() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.setPreferredSize(new Dimension(250, getHeight()));
        rightPanel.setBackground(new Color(240, 240, 240));
    
        rightPanel.add(speedLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        rightPanel.add(speedSlider);
        rightPanel.add(speedValue);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    
        rightPanel.add(new JLabel("Select Algorithm:"));
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        rightPanel.add(algorithmSelector);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    
        JComponent[] buttons = {runButton, loadButton, clearButton, selectStartButton, selectEndButton, getMoreInfoButton, helpButton};
        for (JComponent button : buttons) {
            rightPanel.add(button);
            rightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
    
        this.add(rightPanel, BorderLayout.EAST);
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(new JScrollPane(outputArea), BorderLayout.SOUTH);
    }
    


    private void showHelpDialog() {
        String helpMessage = 
            "<html><body style='width: 300px; padding: 5px;'>" +
            "<h2>Graph Pathfinding Visualizer Help</h2>" +
            "<p><b>Adding Nodes:</b> Click on an empty area to add a new node.</p>" +
            "<p><b>Adding Edges:</b> Click on two nodes consecutively to add an edge between them.</p>" +
            "<p><b>Selecting Start/End Nodes:</b> Use the 'Select Start Node' and 'Select End Node' buttons, then click on a node.</p>" +
            "<p><b>Running Algorithm:</b> Choose an algorithm, set start and end nodes, then click 'Run Algorithm'.</p>" +
            "<p><b>Loading Graph:</b> Use 'Load from File' to import a graph from a file.</p>" +
            "<p><b>Clearing Graph:</b> Click 'Clear Graph' to reset the visualizer.</p>" +
            "<p><b>Adjusting Speed:</b> Use the slider to change the animation speed.</p>" +
            "</body></html>";
    
        JOptionPane.showMessageDialog(this, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
    }
    
 private void runAlgorithm() {
        if (startNode == null && endNode == null) {
            outputArea.append("Please select start and end nodes.\n");
            JOptionPane.showMessageDialog(this, "Please select start and end nodes.", "Action Required", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (startNode == null) {
            outputArea.append("Please select a start node.\n");
            JOptionPane.showMessageDialog(this, "Please select a start node.", "Action Required", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (endNode == null) {
            outputArea.append("Please select an end node.\n");
            JOptionPane.showMessageDialog(this, "Please select an end node.", "Action Required", JOptionPane.WARNING_MESSAGE);
            Common.CustomPopup.showPopupMessage("Select end node", 1100);
            return;
        }
        String currentAlgorithm = (String) algorithmSelector.getSelectedItem();
        outputArea.append("Running " + currentAlgorithm + "...\n");
        List<Node> visitedNodes = new ArrayList<>();
        switch (currentAlgorithm) {
            case "Dijkstra": path = Dijkstra.findPath(graph, startNode, endNode, visitedNodes); break;
            case "A*": path = Astar.findPath(graph, startNode, endNode, visitedNodes); break;
            case "BFS": path = BFS.findPath(graph, startNode, endNode, visitedNodes); break;
            case "DFS": path = DFS.findPath(graph, startNode, endNode, visitedNodes); break;
        }
        if (path != null) {
            outputArea.append("Path found: " + pathToString(path) + "\n");
            // Common.CustomPopup.showPopupMessage("Path Found", 1100);
             animateAlgorithm(visitedNodes, path);
        } else {
            outputArea.append("No path found.\n");
            Common.CustomPopup.showPopupMessage("No Path Found", 1100);

        }
    }

    private void animateAlgorithm(List<Node> visitedNodes, List<Node> path) {
        new Thread(() -> {
            int delay = 1000/speedSlider.getValue();
            // Animate visited nodes
            for (Node node : visitedNodes) {
                SwingUtilities.invokeLater(() -> {
                    ((GraphPanel) graphPanel).setVisitedNode(node);
                    graphPanel.repaint();
                });
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Animate path
            for (int i = 0; i < path.size(); i++) {
                final int index = i;
                SwingUtilities.invokeLater(() -> {
                    ((GraphPanel) graphPanel).setHighlightedNodeIndex(index);
                    graphPanel.repaint();
                });
                try {
                    Thread.sleep(delay * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(SwingUtilities.getWindowAncestor(this));
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                System.out.println("Loading file: " + selectedFile.getName());
                graph = GraphFileReader.readFromFile(selectedFile);
                outputArea.append("Graph loaded from file: " + selectedFile.getName() + "\n");
                outputArea.append("Nodes: " + graph.getNodes().size() + "\n");
                startNode = null;
                endNode = null;
                path = null;
                System.out.println("Repainting graph panel...");
                graphPanel.repaint();
            } catch (IOException e) {
                outputArea.append("Error loading file: " + e.getMessage() + "\n");
                CustomPopup.showPopupMessage("Error loading file ! Please upload CSV FILE ",1600);
                e.printStackTrace();
            }
        }
    }

    private void clearGraph() {
        graph = new Graph();
        startNode = null;
        endNode = null;
        path = null;
        outputArea.append("Graph cleared.\n");
        graphPanel.repaint();
    }

    private String pathToString(List<Node> path) {
        StringBuilder sb = new StringBuilder();
        for (Node node : path) {
            sb.append(node.getId()).append(" -> ");
        }
        sb.setLength(sb.length() - 4);  // Remove last " -> "
        return sb.toString();
    }
    private class GraphPanel extends JPanel {
        private static final int NODE_RADIUS = 20;
        private Node selectedNode = null;
        private int highlightedNodeIndex = -1;

        private Set<Node> visitedNodes = new HashSet<>();
private Node currentVisitedNode = null;

public GraphPanel() {
    setPreferredSize(new Dimension(1000, 700));
    setBackground(Color.WHITE);

    addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Node clickedNode = getNodeAt(e.getX(), e.getY());
            if (clickedNode == null) addNode(e.getX(), e.getY());
            else {
                if (selectingStart) startNode = clickedNode; 
                else if (selectingEnd) endNode = clickedNode;
                else if (e.getButton() == MouseEvent.BUTTON1) {
                    if (selectedNode == null) selectedNode = clickedNode;
                    else { addEdge(selectedNode, clickedNode); selectedNode = null; }
                }
                selectingStart = selectingEnd = false;
            }
            repaint();
        }
    });
}

public void setHighlightedNodeIndex(int index) { this.highlightedNodeIndex = index; }
public void setVisitedNode(Node node) { this.currentVisitedNode = node; this.visitedNodes.add(node); }

@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    System.out.println("Painting graph panel..."); System.out.println("Number of nodes: " + graph.getNodes().size());

    // Draw edges
    for (Node node : graph.getNodes()) for (Edge edge : node.getEdges()) drawEdge(g2d, edge);

    // Draw visited nodes
    for (Node node : visitedNodes) drawVisitedNode(g2d, node);

    // Draw path
    if (path != null) drawPath(g2d);

    // Draw nodes
    for (Node node : graph.getNodes()) drawNode(g2d, node);

    // Highlight current visited node
    if (currentVisitedNode != null) highlightNode(g2d, currentVisitedNode, Color.YELLOW);

    // Highlight current path node
    if (path != null && highlightedNodeIndex >= 0 && highlightedNodeIndex < path.size()) 
        highlightNode(g2d, path.get(highlightedNodeIndex), Color.ORANGE);
}

private void drawNode(Graphics2D g2d, Node node) {
    int x = node.getX(), y = node.getY();
    g2d.setColor(node == startNode ? Color.GREEN : node == endNode ? Color.RED : Color.BLUE);
    g2d.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);
    g2d.setColor(Color.WHITE); g2d.drawString(node.getId(), x - 5, y + 5);
    System.out.println("Drew node: " + node.getId() + " at (" + x + "," + y + ")");
}

private void drawVisitedNode(Graphics2D g2d, Node node) {
    g2d.setColor(new Color(200, 200, 255)); 
    g2d.fillOval(node.getX() - NODE_RADIUS - 2, node.getY() - NODE_RADIUS - 2, 2 * NODE_RADIUS + 4, 2 * NODE_RADIUS + 4);
}

private void highlightNode(Graphics2D g2d, Node node, Color color) {
    g2d.setColor(color);
    g2d.fillOval(node.getX() - NODE_RADIUS - 5, node.getY() - NODE_RADIUS - 5, 2 * NODE_RADIUS + 10, 2 * NODE_RADIUS + 10);
}

private void drawEdge(Graphics2D g2d, Edge edge) {
    int x1 = edge.getSource().getX(), y1 = edge.getSource().getY(), x2 = edge.getDestination().getX(), y2 = edge.getDestination().getY();
    g2d.setColor(Color.BLACK); g2d.drawLine(x1, y1, x2, y2);
    g2d.setColor(Color.RED); g2d.drawString(String.format("%.1f", edge.getWeight()), (x1 + x2) / 2, (y1 + y2) / 2);
    System.out.println("Drew edge: (" + x1 + "," + y1 + ") -> (" + x2 + "," + y2 + ")");
}

private void drawPath(Graphics2D g2d) {
    g2d.setColor(Color.ORANGE); g2d.setStroke(new BasicStroke(3));
    for (int i = 0; i < path.size() - 1; i++) g2d.drawLine(path.get(i).getX(), path.get(i).getY(), path.get(i + 1).getX(), path.get(i + 1).getY());
    g2d.setStroke(new BasicStroke(1));
}

private Node getNodeAt(int x, int y) {
    for (Node node : graph.getNodes()) 
        if (Math.hypot(node.getX() - x, node.getY() - y) <= NODE_RADIUS) return node;
    return null;
}

private void addNode(int x, int y) { graph.addNode(new Node(String.valueOf(graph.getNodes().size()), x, y)); }

private void addEdge(Node source, Node destination) {
    String weightStr = JOptionPane.showInputDialog(this, "Enter edge weight:", "1.0");
    try {
        graph.addEdge(source, destination, weightStr != null && !weightStr.isEmpty() ? Double.parseDouble(weightStr) : 1.0);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid weight. Using default weight of 1.0.");
        graph.addEdge(source, destination, 1.0);
    }
}

}
}



