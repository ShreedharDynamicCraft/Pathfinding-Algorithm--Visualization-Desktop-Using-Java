// package GraphicalUI;

// import javax.swing.*; // GUI components (JButton, JLabel, etc.)

// import GraphicalUI.InfoPanel;
// import Maze.*;
// import Util.HeuristicFunction; // Heuristic functions for search
// // import Util.MazeGenerator; // Maze generation (unused)
// import Util.Node; // Node representation

// import javax.swing.filechooser.FileNameExtensionFilter; // File chooser filter

// import Algo.*;

// import static GraphicalUI.Panel.*;

// import java.awt.*; // Layout, dimensions, colors
// import java.util.ArrayList; // List management
// import java.util.Objects; // Object utilities

// import javax.sound.sampled.*; // Audio handling

// import java.io.File; // File handling
// import java.io.IOException; // IO exceptions
// import java.io.FileNotFoundException; // File not found exceptions
// import java.util.Scanner; // Input reading from files
// import java.awt.event.*; // Event handling (ActionListener, etc.)

// public class SettingsPanel extends JPanel {
//     private final JButton startButton = new JButton("Start");
//     private final JButton stop = new JButton("Stop");
//     private final JButton resetButton = new JButton("Generate");

//     private final JLabel speedLabel = new JLabel("Speed");
//     private final JScrollBar speed = new JScrollBar(JScrollBar.HORIZONTAL, 1, 1, 0, 1000);
//     private final JLabel speedValue = new JLabel(solvingSpeed + " fps");

//     private final JLabel sizeLabel = new JLabel("Size");
//     private final JScrollBar size = new JScrollBar(JScrollBar.HORIZONTAL, 2, 1, 2, 100);
//     private final JLabel sizeValue = new JLabel(nodeSize + " px");

//     private final JLabel info = new JLabel("");

//     JLabel heuristicLabel = new JLabel("Heuristic Function");
//     private final String[] heuristics = {"Manhattan", "Euclidean", "Chebyshev", "Octile", "Octagonal", "Diagonal"};
//     private final JComboBox<String> heuristicBox = new JComboBox<>(heuristics);

//     JLabel algorithmLabel = new JLabel("Algorithm");
//     private final String[] algorithms = {"Dijkstra", "A*", "DFS", "BFS", "Recursive Backtracking"};
//     private final JComboBox<String> algorithmBox = new JComboBox<>(algorithms);

//     JLabel mazeTypeLabel = new JLabel("Maze Type");
//     private final String[] mazeTypes = {"Recursive Backtracking", "Prim's", "Hunt & Kill", "Binary Tree", "Wilson's",
//             "Kruskal's", "Aldous-Broder", "Recursive Division", "Upload Text File"};

//     private final JComboBox<String> mazeTypeBox = new JComboBox<>(mazeTypes);

//     private final Panel panel;
//     JButton toggleButton = new JButton("Toggle Dark/Light Mode");

//     JButton uploadButton = new JButton("Upload Maze");
//     private JLabel selectedFileLabel;

//     private JButton getMoreInfoButton;

//     private InfoPanel infoPanel;
//     private File selectedFile = null; // Variable to hold the selected file



//     private boolean isPathfindingActive = false; // Flag to track pathfinding state


//     private Frame frame; // Reference to Frame

//     String algorithm = "Dijkstra"; // Example algorithm name

//     public SettingsPanel(Panel panel, Frame frame) {
//         this.frame = frame; // Store the reference
//         this.panel = panel;
//         this.infoPanel = infoPanel != null ? infoPanel : new InfoPanel(algorithmBox.getSelectedItem().toString(), this);
//         initSettingsPanel();
//     }

//     public SettingsPanel(Panel panel, InfoPanel infoPanel) {
//         this.panel = panel;
//         this.infoPanel = infoPanel != null ? infoPanel : new InfoPanel(algorithmBox.getSelectedItem().toString(), this);
//         initSettingsPanel();  // Other initialization code...
//     }

//     private void initSettingsPanel() {
//         this.setPreferredSize(new Dimension(SETTINGS_WIDTH, SETTINGS_HEIGHT));
//         this.setLayout(null);
//         Color sky = new Color(70, 116, 176); // RGB values for sky blue
//         this.setBackground(sky);
//         this.setBounds(Panel.WIDTH - SETTINGS_WIDTH, 0, SETTINGS_WIDTH, SETTINGS_HEIGHT);
//         selectedFileLabel = new JLabel("No file selected.");
//         add(selectedFileLabel);
//         addButtonsToPanel();
//         setButtonsBounds();
//         setButtonsAction();
//         this.revalidate(); // Ensure the layout is updated
//         this.repaint();
//     }

//     public SettingsPanel(Panel panel) {
//         this.panel = panel;
//         this.infoPanel = infoPanel != null ? infoPanel : new InfoPanel(algorithmBox.getSelectedItem().toString(), this);
//         initSettingsPanel();
//     }


//     private void addButtonsToPanel() {
//         // Add components to panel in structured order
//         addComponents(speedLabel, speed, speedValue);         // Speed controls
//         addComponents(sizeLabel, size, sizeValue);            // Size controls
//         addComponents(mazeTypeLabel, mazeTypeBox, uploadButton);  // Maze type and upload button
//         addComponents(algorithmLabel, algorithmBox);          // Algorithm selection

//         // Conditionally add heuristic selection based on algorithm choice
//         if ("A*".equals(algorithmBox.getSelectedItem())) {
//             addComponents(heuristicLabel, heuristicBox);
//         }

//         // Add functional buttons to panel
//         addComponents(toggleButton, startButton, resetButton, stop);

//         // Add information panel and details button
//         this.add(info);
//         setupMoreInfoButton();  // Setup the 'Algorithm Details' button

//         this.revalidate();
//         this.repaint();
//     }

//     // Helper method to add multiple components to the panel
//     private void addComponents(JComponent... components) {
//         for (JComponent component : components) {
//             this.add(component);
//         }
//     }

//     // Setup for the 'Algorithm Details' button
//     private void setupMoreInfoButton() {
//         getMoreInfoButton = new JButton("Algorithm Details");
//         getMoreInfoButton.setBackground(Color.ORANGE);
//         getMoreInfoButton.setForeground(Color.BLACK);
//         getMoreInfoButton.setFocusPainted(false);
//         getMoreInfoButton.setFont(new Font("Arial", Font.BOLD, 14));
//         getMoreInfoButton.setToolTipText("Get detailed explanation of the selected algorithm");

//         // Action listener for button click
//         getMoreInfoButton.addActionListener(e -> {
//             System.out.println("Algorithm Details button clicked");
//             if (infoPanel != null) {
//                 infoPanel.showMoreInfo();
//             } else {
//                 System.out.println("infoPanel is null");
//             }
//         });

//         // Add the button to the panel
//         this.add(getMoreInfoButton);
//     }

//     // Helper method to set button background and repaint
//     private void setButtonBackground(JButton button, Color color) {
//         button.setBackground(color);
//         button.repaint();
//     }

//     private void setButtonsBounds() {
//         int downShift = 20;
//         int labelWidth = 50;
//         int scrollWidth = 150;
//         int buttonWidth = 140;
//         int fontSize = 15;
//         // speed bounds
//         speedLabel.setBounds((SETTINGS_WIDTH - labelWidth) / 2 - scrollWidth, downShift, labelWidth, downShift);
//         speed.setBounds((SETTINGS_WIDTH - scrollWidth) / 2, downShift, scrollWidth, 20);
//         speedValue.setBounds((SETTINGS_WIDTH - labelWidth) / 2 + scrollWidth, downShift, labelWidth, downShift);
//         // size bounds
//         sizeLabel.setBounds((SETTINGS_WIDTH - labelWidth) / 2 - scrollWidth, downShift * 3, labelWidth, downShift);
//         size.setBounds((SETTINGS_WIDTH - scrollWidth) / 2, downShift * 3, scrollWidth, downShift);
//         sizeValue.setBounds((SETTINGS_WIDTH - labelWidth) / 2 + scrollWidth, downShift * 3, labelWidth, downShift);
//         // maze algorithm bounds
//         mazeTypeLabel.setBounds((SETTINGS_WIDTH - labelWidth) / 2 - scrollWidth, downShift * 5, labelWidth, downShift);
//         mazeTypeBox.setBounds((SETTINGS_WIDTH - scrollWidth) / 2, downShift * 5, scrollWidth, downShift);
//         uploadButton.setBounds((SETTINGS_WIDTH - buttonWidth) / 2, downShift * 6, buttonWidth, downShift);
//         uploadButton.setVisible(false); // Initially hidden, will appear when "Upload Text File" is selected

//         // algorithm bounds
//         algorithmLabel.setBounds((SETTINGS_WIDTH - labelWidth) / 2 - scrollWidth, downShift * 7,
//                 labelWidth * 2, downShift);
//         algorithmBox.setBounds((SETTINGS_WIDTH - scrollWidth) / 2, downShift * 7, scrollWidth, downShift);

//         // heuristic bounds
//         heuristicLabel.setBounds((SETTINGS_WIDTH - labelWidth) / 2 - scrollWidth,
//                 downShift * 9, labelWidth * 3, downShift);
//         heuristicBox.setBounds((SETTINGS_WIDTH - scrollWidth) / 2 + labelWidth,
//                 downShift * 9, scrollWidth, downShift);
//         if (Objects.equals(algorithmBox.getSelectedItem(), "A*")) {
//             heuristicBox.setEnabled(true);
//         } else {
//             heuristicBox.setEnabled(false);
//         }
//         // function button bounds
//         startButton.setBounds((SETTINGS_WIDTH - buttonWidth) / 2, downShift * 11, buttonWidth, downShift);
//         resetButton.setBounds((SETTINGS_WIDTH - buttonWidth) / 2, downShift * 13, buttonWidth, downShift);
//         stop.setBounds((SETTINGS_WIDTH - buttonWidth) / 2, downShift * 15, buttonWidth, downShift);

//         toggleButton.setBounds((SETTINGS_WIDTH - buttonWidth - 1) / 2, downShift * 10, buttonWidth, downShift);
//         toggleButton.setVisible(true);

//         // info bounds (for displaying algorithm info)
//         info.setBounds(0, downShift * 17, SETTINGS_WIDTH, fontSize * 32); // Increased height
//         info.setFont(new Font("Arial", Font.BOLD, fontSize));
//         info.setForeground(Color.RED);
//         info.setHorizontalAlignment(JLabel.CENTER);
//         info.setVerticalAlignment(JLabel.CENTER);
//         getMoreInfoButton.setBounds((SETTINGS_WIDTH - buttonWidth) / 2, downShift * 17, buttonWidth, downShift);
//     }

//     private void setButtonsAction() {
//         startButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 resetPath();

//                 if (!isPathfindingActive) {
//                     System.out.println("Starting pathfinding..."); // Debug log
//                     isPathfindingActive = true; // Set flag to true when pathfinding starts

//                     SearchAlgo pathFinding = switch (algorithmBox.getSelectedItem().toString()) {
//                         case "Dijkstra" -> new Dijkstra(panel);
//                         case "A*" -> new aStar(panel);
//                         case "DFS" -> new DFS(panel);
//                         case "BFS" -> new BFS(panel);
//                         case "Recursive Backtracking" -> new RBT(panel);
//                         default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithmBox.getSelectedItem());
//                     };

//                     // Execute the pathfinding logic
//                     new Thread(() -> {
//                         pathFinding.execute(); // Start pathfinding in a separate thread
//                         isPathfindingActive = false; // Reset flag when done
//                         System.out.println("Pathfinding complete."); // Debug log
//                         SwingUtilities.invokeLater(() -> panel.repaint()); // Ensure UI updates on EDT
//                     }).start();
//                 } else {
//                     System.out.println("Pathfinding already active."); // Debug log
//                 }
//             }
//         });

//         mazeTypeBox.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 if (mazeTypeBox.getSelectedItem().toString().equals("Upload Text File")) {
//                     uploadButton.setVisible(true);
//                 } else {
//                     uploadButton.setVisible(false);
//                 }
//             }
//         });

//         stop.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
            

//                 if (isPathfindingActive) {
//                     // Logic to stop the pathfinding process if applicable
//                     System.out.println("Stopping pathfinding..."); // Debug log
//                     // Add code to stop the pathfinding algorithm if it supports interruption
//                 }
//                 drawPath = !drawPath; // Toggle draw path state
//                 stop.setText(drawPath ? "Stop" : "Start");
//                 panel.repaint();
               
//             }
//         });

//         // Action Listener for the Upload Button
//         uploadButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 JFileChooser fileChooser = new JFileChooser();
//                 int returnValue = fileChooser.showOpenDialog(null);
//                 if (returnValue == JFileChooser.APPROVE_OPTION) {
//                     selectedFile = fileChooser.getSelectedFile();
//                     selectedFileLabel.setText("Selected File: " + selectedFile.getName()); // Update label with file name
//                 }
//             }
//         });
        
//         add(uploadButton);
//         add(selectedFileLabel);

//         resetButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 panel.resetPanel();
        
//                 String selectedMaze = mazeTypeBox.getSelectedItem().toString();
        
//                 final MazeAlgo maze; // Declare maze as final
        
//                 if (selectedMaze.equals("Upload Text File")) {
//                     if (selectedFile == null) {
//                         JOptionPane.showMessageDialog(null, "Please upload a maze file first.", "No File Selected", JOptionPane.WARNING_MESSAGE);
//                         return; // Exit if no file selected
//                     }
//                     maze = new FileMaze(panel, selectedFile);
//                 } else {
//                     maze = switch (selectedMaze) {
//                         case "Binary Tree" -> new BinaryTreeMaze(panel);
//                         case "Recursive Backtracking" -> new RBTMaze(panel);
//                         case "Prim's" -> new PrimMaze(panel);
//                         case "Hunt & Kill" -> new HuntAndKillMaze(panel);
//                         case "Wilson's" -> new WilsonMaze(panel);
//                         case "Kruskal's" -> new KruskalMaze(panel);
//                         case "Aldous-Broder" -> new AldousBroder(panel);
//                         case "Recursive Division" -> new RecursiveDivision(panel);
//                         default -> null;
//                     };
//                 }
        
//                 if (maze != null) {
//                     new Thread(() -> {
//                         try {
//                             maze.execute();
//                         } finally {
                            
//                         }
//                     }).start();
//                 }
//             }
//         });
        
        
//         // Method to load maze from a text file
//         speed.addAdjustmentListener(new AdjustmentListener() {
//             @Override
//             public void adjustmentValueChanged(AdjustmentEvent e) {
//                 speedValue.setText(String.valueOf(speed.getValue()) + " fps");
//                 solvingSpeed = speed.getValue();
//                 speedValue.repaint();
//                 speed.repaint();
//             }
//         });

//         size.addAdjustmentListener(new AdjustmentListener() {
//             @Override
//             public void adjustmentValueChanged(AdjustmentEvent e) {
//                 sizeValue.setText(String.valueOf(size.getValue()) + " px");
//                 sizeValue.repaint();
//                 size.repaint();
//                 nodeSize = size.getValue();
//                 panel.resetPanel();
//                 panel.repaint();
//             }
//         });

//         heuristicBox.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 int index = heuristicBox.getSelectedIndex();
//                 Node.heuristicFunction.setType(HeuristicFunction.Heuristic.values()[index]);
//                 for (int row = 0; row < numRow; row++) {
//                     for (int col = 0; col < numCol; col++) {
//                         nodesGrid[row][col].setCost();
//                     }
//                 }
//             }
//         });

//         algorithmBox.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 int index = algorithmBox.getSelectedIndex();
//                 if (index == 1) {
//                     heuristicBox.setEnabled(true);
//                     SettingsPanel.this.add(heuristicLabel);
//                     SettingsPanel.this.add(heuristicBox);
//                 } else {
//                     heuristicBox.setSelectedIndex(0);
//                     heuristicBox.setEnabled(false);
//                     SettingsPanel.this.remove(heuristicLabel);
//                     SettingsPanel.this.remove(heuristicBox);
//                 }
//             }
//         });

//         toggleButton.addActionListener(e -> {
//             panel.toggleMode(); // Toggle the mode directly

//             // Update the button text based on the current mode
//             if (panel.isDarkMode()) {
//                 toggleButton.setText("Light Mode");
//                 info.setForeground(Color.WHITE);
//             } else {
//                 toggleButton.setText("Dark Mode");
//                 info.setForeground(Color.BLACK);
//             }

//             panel.repaint(); // Refresh panel to show changes
//         });
//     }

//     private void resetPath() {
//         for (Util.Node[] nodes : nodesGrid) {
//             for (int j = 0; j < nodes.length; j++) {
//                 nodes[j].resetPath();
//             }
//         }
//         pathLength = 0;
//         numNodesExpanded = 0;
//         searchTime = 0;
//         pathNodes = new ArrayList<>();
//         panel.repaint();
//     }

//     public void setInfo(String info) {
//         this.info.setText(info);
//     }

//     public String getSelectedAlgorithm() {
//         return (String) algorithmBox.getSelectedItem();
//     }

//     public void enableButtons(boolean b) {
//         // size bounds
//         size.setEnabled(b);
//         // function buttons
//         startButton.setEnabled(b);
//         resetButton.setEnabled(b);
//         stop.setEnabled(b);
//     }

//     // Method to load maze from a text file
//     public void generateMazeFromTextFile(File selectedFile) throws IOException {
//         try (Scanner scanner = new Scanner(selectedFile)) {
//             if (!scanner.hasNextInt()) {
//                 JOptionPane.showMessageDialog(null, "Invalid file format. First two numbers should be rows and columns.", "File Error", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }
//             int rows = scanner.nextInt();
//             int cols = scanner.nextInt();

//             // Set an upper limit for rows and columns to prevent excessive memory usage
//             int maxSize = 1000; // Example limit
//             if (rows <= 0 || cols <= 0 || rows > maxSize || cols > maxSize) {
//                 JOptionPane.showMessageDialog(null, "Maze size is too large or invalid. Maximum allowed size is " + maxSize + "x" + maxSize + ".", "File Error", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             Node[][] nodesGrid = new Node[rows][cols];

//             for (int i = 0; i < rows; i++) {
//                 for (int j = 0; j < cols; j++) {
//                     if (!scanner.hasNextInt()) {
//                         JOptionPane.showMessageDialog(null, "Invalid file format. Not enough maze data.", "File Error", JOptionPane.ERROR_MESSAGE);
//                         return;
//                     }
//                     int value = scanner.nextInt();
//                     nodesGrid[i][j] = new Node(i, j, value == 1); // Assuming '1' represents a wall
//                 }
//             }

//             // Update the panel with the new maze
//             panel.setMazeDimensions(rows, cols);
//             panel.setMaze(nodesGrid);
//             System.out.println("Maze loaded successfully with " + rows + " rows and " + cols + " cols.");
//         } catch (IOException e) {
//             System.out.println("File not found: " + selectedFile.getName());
//             throw e;
//         }
//     }

//     private void updateSelectedFileLabel() {
//         selectedFileLabel.setText(selectedFile.getName());
//     }
// }







package GraphicalUI;

import Algo.*;
import GraphicalUI.Panel;
import Maze.*;
import Util.HeuristicFunction;
import Util.Node;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;


public class SettingsPanel extends JPanel {
    private final JButton startButton = new JButton("Start Pathfinding");
    private final JButton stopButton = new JButton("Pause/Resume");
    private final JButton resetButton = new JButton("Reset All");
    private final JButton generateButton = new JButton("Generate Maze");
    private final JButton toggleModeButton = new JButton("Toggle Dark/Light Mode");
    private final JButton uploadButton = new JButton("Upload Custom Maze");
    private final JButton getMoreInfoButton = new JButton("Algorithm Details");

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
    private final String[] mazeTypes = {"Binary Tree", "Recursive Backtracking", "Recursive Division", "Prim's", "Kruskal's", "Hunt & Kill", "Wilson's", "Aldous-Broder", "Upload Text File"};

    private final JComboBox<String> mazeTypeBox = new JComboBox<>(mazeTypes);

    private final Panel panel;
    private final InfoPanel infoPanel;
    private File selectedFile = null;
    private boolean isPathfindingActive = false;

    public SettingsPanel(Panel panel) {
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
    // private void addComponentsToPanel(JPanel contentPanel) {
    //     // add(Box.createVerticalStrut(10));
    //     addLabeledComponent(contentPanel, speedLabel, speedSlider, speedValue);
        
    //     addLabeledComponent(contentPanel, sizeLabel, sizeSlider, sizeValue);
    //     addLabeledComponent(contentPanel, mazeTypeLabel, mazeTypeBox);
    //     contentPanel.add(uploadButton);
    //     addLabeledComponent(contentPanel, algorithmLabel, algorithmBox);
    //     addLabeledComponent(contentPanel, heuristicLabel, heuristicBox);
    
    //     contentPanel.add(Box.createVerticalStrut(2));
    //     contentPanel.add(startButton);
    //     contentPanel.add(stopButton);
    //     contentPanel.add(resetButton);
    //     contentPanel.add(generateButton);
    //     contentPanel.add(toggleModeButton);
    //     contentPanel.add(getMoreInfoButton);
    
    //     contentPanel.add(Box.createVerticalStrut(2));
    //     contentPanel.add(info);
    //     contentPanel.add(selectedFileLabel);
    
    //     for (Component comp : contentPanel.getComponents()) {
    //         if (comp instanceof JComponent) {
    //             ((JComponent) comp).setAlignmentX(Component.CENTER_ALIGNMENT);
    //         }
    //     }
    // }
    // private void addLabeledComponent(JPanel parentPanel, JLabel label, JComponent... components) {
    //     JPanel panel = new JPanel();
    //     panel.setLayout(new FlowLayout(FlowLayout.CENTER));
    //     panel.add(label);
    //     for (JComponent component : components) {
    //         panel.add(component);
    //     }
        
    //     parentPanel.add(panel);
    // }
    

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



    // private void addLabeledComponent(JPanel parentPanel, JLabel label, JComponent... components) {
    //     // Create a panel for each labeled component
    //     JPanel panel = new JPanel();
    //     panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5)); // Adjusted gaps for better spacing
    
    //     // Customize label appearance
    //     label.setFont(new Font("Arial", Font.BOLD, 14)); // Use Arial and increase size
    //     label.setForeground(new Color(34, 34, 34)); // Dark gray color for labels
    //     label.setPreferredSize(new Dimension(100, 30)); // Set a preferred size for uniformity
    //     label.setToolTipText("This is the " + label.getText()); // Tooltip for labels
    
    //     // Add label to the panel
    //     panel.add(label);
    
    //     // Add components and set their properties
    //     for (JComponent component : components) {
    //         component.setPreferredSize(new Dimension(200, 40)); // Size for better visualization
    //         component.setBackground(new Color(255, 255, 255)); // White background for components
    //         component.setForeground(Color.BLACK); // Black text for contrast
    //         component.setFont(new Font("Arial", Font.PLAIN, 14)); // Consistent font for components
    //         panel.add(component);
    //     }
    
    //     // Set a border for the panel for better visibility (optional)
    //     panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Adding vertical padding to avoid tight layout
    
    //     // Set background color for the panel
    //     panel.setBackground(new Color(240, 240, 240)); // Light gray background
    
    //     // Add the panel to the parent panel
    //     parentPanel.add(panel);
    // }
    
    
    
    // Example help dialog method
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
        mazeTypeBox.setSelectedIndex(0);
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
                JOptionPane.showMessageDialog(null, "Please upload a maze file first.", "No File Selected", JOptionPane.WARNING_MESSAGE);
                return null;
            }
            return new FileMaze(panel, selectedFile);
        }
        return switch (selectedMaze) {
            case "Binary Tree" -> new BinaryTreeMaze(panel);
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



