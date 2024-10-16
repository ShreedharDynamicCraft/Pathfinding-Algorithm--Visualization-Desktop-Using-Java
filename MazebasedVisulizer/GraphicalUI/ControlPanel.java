package GraphicalUI;

import Algo.*;
import Backend.HeuristicFunction;
import Backend.Node;
import Common.CustomPopup;
import GraphicalUI.Panel;
import Helper.MazeAlgo;
import Helper.SearchAlgo;
import Maze.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

import Generic_Visulizer.AlgorithmProvider;


public class ControlPanel extends JPanel implements AlgorithmProvider {
    private final JButton startButton = new JButton("Start Pathfinding");
    private final JButton stopButton = new JButton("Pause / Resume Search");
    private final JButton resetButton = new JButton("Reset All");
    private final JButton generateButton = new JButton("Generate Maze");
    private final JButton toggleModeButton = new JButton("Toggle Dark/Light Mode");
    private final JButton uploadButton = new JButton("Upload Custom Maze");
    private final JButton getMoreInfoButton = new JButton("View Algorithm Info");

    private final JLabel speedLabel = new JLabel("Animation Speed");
    private final JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 210, 70);
    private final JLabel speedValue = new JLabel("50 fps");

    private final JLabel sizeLabel = new JLabel("Node Size");
    private final JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 15, 150, 30);
    private final JLabel sizeValue = new JLabel("20 px");

    private final JLabel info = new JLabel("");
    private final JLabel selectedFileLabel = new JLabel("No file selected.");

    private final JLabel heuristicLabel = new JLabel("Heuristic Function");
    private final String[] heuristics = {"Manhattan", "Chebyshev", "Diagonal", "Octagonal", "Octile", "Euclidean"};
    private final JComboBox<String> heuristicBox = new JComboBox<>(heuristics);

    private final JLabel algorithmLabel = new JLabel("Pathfinding Algorithm");
    private final String[] algorithms = {"Dijkstra", "A*", "DFS", "BFS", "Recursive Backtracking", "Greedy BFS","BidirectionalSearch"};
    private final JComboBox<String> algorithmBox = new JComboBox<>(algorithms);

    private final JLabel mazeTypeLabel = new JLabel("Maze Type");
    private final String[] mazeTypes = {"Binary Tree", "Recursive Backtracking", "Recursive Division", "Prim's", "Kruskal's", "Hunt & Kill", "Wilson's", "Aldous-Broder", "TajMahalMaze","RangoliMaze","Upload Text File"};

    private final JComboBox<String> mazeTypeBox = new JComboBox<>(mazeTypes);

    private final Panel panel;
    private final InfoPanel infoPanel;
    private File selectedFile = null;
    private boolean isPathfindingActive = false;

    public ControlPanel(Panel panel) {
        this.panel = panel;
        this.infoPanel = new InfoPanel(algorithmBox.getSelectedItem().toString(), this);
        initSettingsPanel();
    }

    private void initSettingsPanel() {
        setPreferredSize(new Dimension(Panel.SETTINGS_WIDTH, Panel.SETTINGS_HEIGHT));
        setLayout(new BorderLayout()); // Change to BorderLayout
        setBackground(new Color(70, 116, 176));
        setBounds(Panel.WIDTH - Panel.SETTINGS_WIDTH,0, Panel.SETTINGS_WIDTH, Panel.SETTINGS_HEIGHT);
    
        // Create a panel to hold all components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(70, 116, 176));
        // setBackground(new Color(70, 116, 176));

    
        // Add components to the content panel
        addComponentsToPanel(contentPanel);
        setupSliders();
        setComponentActions();
    
        // Create a scroll pane and add the content panel to it
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
    
        // Add the scroll pane to this panel
        add(scrollPane, BorderLayout.CENTER);
    resetAll();
        revalidate();
        repaint();
    }

    private void addComponentsToPanel(JPanel contentPanel) {
        // Reduced vertical gaps and added components for interactivity
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Set vertical alignment for all components
    
        addLabeledComponent(contentPanel, speedLabel, speedSlider, speedValue);
        addLabeledComponent(contentPanel, sizeLabel, sizeSlider, sizeValue);
        addLabeledComponent(contentPanel, mazeTypeLabel, mazeTypeBox);
        contentPanel.add(uploadButton);
        
        addLabeledComponent(contentPanel, algorithmLabel, algorithmBox);
        addLabeledComponent(contentPanel, heuristicLabel, heuristicBox);
        
        // Reduced gap here
        contentPanel.add(startButton);
        contentPanel.add(stopButton);
        contentPanel.add(resetButton);
        contentPanel.add(generateButton);
        contentPanel.add(toggleModeButton);
        contentPanel.add(getMoreInfoButton);
        
        // Additional interactive feature: add a progress bar
        // JProgressBar progressBar = new JProgressBar();
        // progressBar.setString("Progress");
        // progressBar.setStringPainted(true);
        // contentPanel.add(progressBar);
    
        // Additional interactive feature: add a help button
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(e -> showHelpDialog());
        contentPanel.add(helpButton);
    
        // Reduced gap and add more info
        contentPanel.add(info);
        contentPanel.add(selectedFileLabel);
    
        // Center align all components
        for (Component comp : contentPanel.getComponents()) {
            if (comp instanceof JComponent) {
                ((JComponent) comp).setAlignmentX(Component.CENTER_ALIGNMENT);
            }
        }
    }
    
    private void addLabeledComponent(JPanel parentPanel, JLabel label, JComponent... components) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
                label.setFont(new Font("Arial", Font.BOLD, 15)); // Use Arial and increase size

        panel.add(label);
        for (JComponent component : components) {
            panel.add(component);
        }
        parentPanel.add(panel);
    }
    
    private void showHelpDialog() {
        JOptionPane.showMessageDialog(this, 
            "Instructions:\n" +
            "1. Choose your algorithm and maze type.\n" +
            "2. Adjust the speed and size sliders.\n" +
            "3. Click 'Start' to visualize the pathfinding process.\n" +
            "4. Use 'Reset' to start over, or 'Generate' for a new maze.", 
            "Help", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    





    private void setupSliders() {
        setupSlider(speedSlider, "Animation Speed (fps)");
        setupSlider(sizeSlider, "Node Size (px)");
    }

    private void setupSlider(JSlider slider, String toolTipText) {
        slider.setMajorTickSpacing(30);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setToolTipText(toolTipText);
        slider.setBackground(Color.pink);
        slider.setForeground(Color.BLUE);
    }

    private void setComponentActions() {
        startButton.addActionListener(e -> startPathfinding());
        stopButton.addActionListener(e -> togglePathfinding());
        resetButton.addActionListener(e -> resetAll());
        generateButton.addActionListener(e -> generateMaze());
        uploadButton.addActionListener(e -> uploadMazeFile());
        toggleModeButton.addActionListener(e -> toggleDarkLightMode());
        getMoreInfoButton.addActionListener(e -> showAlgorithmDetails());

        mazeTypeBox.addActionListener(e -> uploadButton.setVisible(mazeTypeBox.getSelectedItem().toString().equals("Upload Text File")));

        speedSlider.addChangeListener(e -> updateSpeed());
        sizeSlider.addChangeListener(e -> updateSize());

        heuristicBox.addActionListener(e -> updateHeuristicFunction());
        algorithmBox.addActionListener(e -> toggleHeuristicVisibility());
    }

    private void startPathfinding() {
        if (!isPathfindingActive) {
            resetPath();
            isPathfindingActive = true;
            SearchAlgo pathFinding = createPathfindingAlgorithm();
            new Thread(() -> {
                pathFinding.execute();
                isPathfindingActive = false;
                SwingUtilities.invokeLater(() -> panel.repaint());
            }).start();
        }
    }

    private SearchAlgo createPathfindingAlgorithm() {
        return switch (algorithmBox.getSelectedItem().toString()) {
            case "Dijkstra" -> new Dijkstra(panel);
            case "A*" -> new aStar(panel);
            case "DFS" -> new DFS(panel);
            case "BFS" -> new BFS(panel);
            // case "BidirectionalSearch" -> new BidirectionalSearch(panel);
            // case "Greedy BFS" -> new GreedyBFS(panel);

            
            case "Recursive Backtracking" -> new RBT(panel);
            default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithmBox.getSelectedItem());
        };
    }

    private void togglePathfinding() {
        isPathfindingActive = !isPathfindingActive;
        stopButton.setText(isPathfindingActive ? "Resume" : "Pause");
        panel.repaint();
    }

    private void resetAll() {
        panel.resetPanel();
        resetPath();
        selectedFile = null;
        selectedFileLabel.setText("No file selected.");
        mazeTypeBox.setSelectedIndex(1);
        algorithmBox.setSelectedIndex(0);
        heuristicBox.setSelectedIndex(0);
        speedSlider.setValue(50);
        sizeSlider.setValue(20);
        panel.repaint();
        CustomPopup.showPopupMessage("Panel Reseted sucessfullt",2000);
    }

    private void generateMaze() {
        panel.resetPanel();
        String selectedMaze = mazeTypeBox.getSelectedItem().toString();
        MazeAlgo maze = createMazeAlgorithm(selectedMaze);
        if (maze != null) {
            new Thread(maze::execute).start();
        }
    }

    private MazeAlgo createMazeAlgorithm(String selectedMaze) {
        if (selectedMaze.equals("Upload Text File")) {
            if (selectedFile == null) {
                String message = "<html><body style='text-align:center;'>" +
                "<h2 style='color:red;'>No Maze File Selected</h2>" +
                "<p style='font-size:14px;'>Please upload a maze file to proceed.</p>" +
                "</body></html>";
               // Define options for buttons
        Object[] options = {"Retry", "Cancel"};

        // Show the customized message dialog with default warning icon and custom buttons
        int choice = JOptionPane.showOptionDialog(null, message, "Maze File Missing", 
                                                  JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
                                                  null, options, options[0]);

        // Handle user response
        if (choice == 0) {
            System.out.println("Retry selected");
            // Add retry logic here
        } else {
            System.out.println("Cancel selected");
            // Add cancel logic here
        }
                // JOptionPane.showMessageDialog(null, "Please upload a maze file first.", "No File Selected", JOptionPane.WARNING_MESSAGE);
                return null;
            }
            return new FileMaze(panel, selectedFile);
        }
        return switch (selectedMaze) {
             case "Binary Tree" -> new BinaryTreeMaze(panel);
            case "TajMahalMaze" -> new TajMahalMaze(panel);
case "RangoliMaze.java"->new RangoliMaze(panel);
            case "Recursive Backtracking" -> new RBTMaze(panel);
            case "Prim's" -> new PrimMaze(panel);
            case "Hunt & Kill" -> new HuntAndKillMaze(panel);
            case "Wilson's" -> new WilsonMaze(panel);
            case "Kruskal's" -> new KruskalMaze(panel);
            case "Aldous-Broder" -> new AldousBroder(panel);
            case "Recursive Division" -> new RecursiveDivision(panel);
            
            default -> null;
        };
    }

    private void uploadMazeFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            selectedFileLabel.setText("Selected: " + selectedFile.getName());
        }
    }

    private void toggleDarkLightMode() {
        panel.toggleMode();
        toggleModeButton.setText(panel.isDarkMode() ? "Switch to Light Mode" : "Switch to Dark Mode");
        info.setForeground(panel.isDarkMode() ? Color.WHITE : Color.BLACK);
        panel.repaint();
    }

    private void showAlgorithmDetails() {
        if (infoPanel != null) {
            infoPanel.showMoreInfo();
        }
    }

    private void updateSpeed() {
        int speed = speedSlider.getValue();
        speedValue.setText(speed + " fps");
        Panel.solvingSpeed = speed;
    }

    private void updateSize() {
        int size = sizeSlider.getValue();
        sizeValue.setText(size + " px");
        Panel.nodeSize = size;
        panel.resetPanel();
        panel.repaint();
    }

    private void updateHeuristicFunction() {
        int index = heuristicBox.getSelectedIndex();
        Node.heuristicFunction.setType(HeuristicFunction.Heuristic.values()[index]);
        for (Node[] nodes : Panel.nodesGrid) {
            for (Node node : nodes) {
                node.setCost();
            }
        }
    }

    private void toggleHeuristicVisibility() {
        boolean isAStarSelected = "A*".equals(algorithmBox.getSelectedItem());
        heuristicLabel.setVisible(isAStarSelected);
        heuristicBox.setVisible(isAStarSelected);
    }

    private void resetPath() {
        for (Node[] nodes : Panel.nodesGrid) {
            for (Node node : nodes) {
                node.resetPath();
            }
        }
        Panel.pathLength = 0;
        Panel.numNodesExpanded = 0;
        Panel.searchTime = 0;
        Panel.pathNodes = new ArrayList<>();
        panel.repaint();
    }

    public void setInfo(String info) {
        this.info.setText(info);
    }

    public String getSelectedAlgorithm() {
        return (String) algorithmBox.getSelectedItem();
    }

    public void enableButtons(boolean enabled) {
        sizeSlider.setEnabled(enabled);
        startButton.setEnabled(enabled);
        resetButton.setEnabled(enabled);
        stopButton.setEnabled(enabled);
        generateButton.setEnabled(enabled);
    }

    public void generateMazeFromTextFile(File selectedFile) throws IOException {
        try (Scanner scanner = new Scanner(selectedFile)) {
            if (!scanner.hasNextInt()) {
                JOptionPane.showMessageDialog(null, "Invalid file format. First two numbers should be rows and columns.", "File Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();

            int maxSize = 1000;
            if (rows <= 0 || cols <= 0 || rows > maxSize || cols > maxSize) {
                JOptionPane.showMessageDialog(null, "Maze size is invalid. Maximum allowed size is " + maxSize + "x" + maxSize + ".", "File Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Node[][] nodesGrid = new Node[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (!scanner.hasNextInt()) {
                        JOptionPane.showMessageDialog(null, "Invalid file format. Not enough maze data.", "File Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int value = scanner.nextInt();
                    nodesGrid[i][j] = new Node(i, j, value == 1);
                }
            }

            panel.setMazeDimensions(rows, cols);
            panel.setMaze(nodesGrid);
            System.out.println("Maze loaded successfully with " + rows + " rows and " + cols + " columns.");
        }
    }
}



