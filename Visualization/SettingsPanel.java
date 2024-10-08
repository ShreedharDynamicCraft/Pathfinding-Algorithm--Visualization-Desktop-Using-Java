package Visualization;
import javax.swing.*; // GUI components (JButton, JLabel, etc.)
import Algorithms.Create.*; // Maze creation algorithms
import Algorithms.Search.*; // Pathfinding algorithms (A*, Dijkstra, etc.)
import Util.HeuristicFunction; // Heuristic functions for search
// import Util.MazeGenerator; // Maze generation (unused)
import Util.Node; // Node representation

import Visualization.InfoPanel; // Displays algorithm info
import javax.swing.filechooser.FileNameExtensionFilter; // File chooser filter

import java.awt.*; // Layout, dimensions, colors
import java.util.ArrayList; // List management
import java.util.Objects; // Object utilities
import static Visualization.Panel.*; // Static members from Panel
import javax.sound.sampled.*; // Audio handling

import java.io.File; // File handling
import java.io.IOException; // IO exceptions
import java.io.FileNotFoundException; // File not found exceptions
import java.util.Scanner; // Input reading from files
import java.awt.event.*; // Event handling (ActionListener, etc.)


public class SettingsPanel extends JPanel {
    private final JButton startButton = new JButton("Start");
    private final JButton stop = new JButton("Stop");
    private final JButton resetButton = new JButton("Generate");

    private final JLabel speedLabel = new JLabel("Speed");
    private final JScrollBar speed = new JScrollBar(JScrollBar.HORIZONTAL, 1, 1, 0, 1000);
    private final JLabel speedValue = new JLabel(solvingSpeed + " fps");

    private final JLabel sizeLabel = new JLabel("Size");
    private final JScrollBar size = new JScrollBar(JScrollBar.HORIZONTAL, 2, 1, 2, 100);
    private final JLabel sizeValue = new JLabel(nodeSize + " px");

    private final JLabel info = new JLabel("");

    JLabel heuristicLabel = new JLabel("Heuristic Function");
    private final String[] heuristics = {"Manhattan", "Euclidean", "Chebyshev", "Octile", "Octagonal", "Diagonal"};
    private final JComboBox<String> heuristicBox = new JComboBox<>(heuristics);

    JLabel algorithmLabel = new JLabel("Algorithm");
    private final String[] algorithms = {"Dijkstra", "A*", "DFS", "BFS", "Recursive Backtracking"};
    private final JComboBox<String> algorithmBox = new JComboBox<>(algorithms);

    JLabel mazeTypeLabel = new JLabel("Maze Type");
    private final String[] mazeTypes = {"Recursive Backtracking", "Prim's", "Hunt & Kill", "Binary Tree", "Wilson's",
    "Kruskal's", "Aldous-Broder", "Recursive Division", "Upload Text File"};

    private final JComboBox<String> mazeTypeBox = new JComboBox<>(mazeTypes);

    private final Panel panel;
    JButton toggleButton = new JButton("Toggle Dark/Light Mode");


    JButton uploadButton = new JButton("Upload Maze");
    private JLabel selectedFileLabel;
    
    private JButton getMoreInfoButton;

    private  InfoPanel infoPanel;
 
    private Clip clip; // Field to manage sound

    private boolean isPathfindingActive = false; // Flag to track pathfinding state

private File selectedFile = null; // Variable to hold the selected file

private Frame frame; // Reference to Frame

String algorithm = "Dijkstra"; // Example algorithm name

public SettingsPanel(Panel panel, Frame frame) {
    this.frame = frame; // Store the reference
    this.panel = panel;
this.infoPanel = infoPanel != null ? infoPanel : new InfoPanel(algorithmBox.getSelectedItem().toString(), this);
    initSettingsPanel();
}
public SettingsPanel(Panel panel, InfoPanel infoPanel) {
    this.panel = panel;
    this.infoPanel = infoPanel != null ? infoPanel : new InfoPanel(algorithmBox.getSelectedItem().toString(), this);
 initSettingsPanel();  // Other initialization code...
}

private void initSettingsPanel() {
    this.setPreferredSize(new Dimension(SETTINGS_WIDTH, SETTINGS_HEIGHT));
    this.setLayout(null);
    Color sky = new Color(70, 116, 176); // RGB values for sky blue
    this.setBackground(sky);
    this.setBounds(Panel.WIDTH - SETTINGS_WIDTH, 0, SETTINGS_WIDTH, SETTINGS_HEIGHT);
    selectedFileLabel = new JLabel("No file selected.");
    add(selectedFileLabel);
    addButtonsToPanel();
    setButtonsBounds();
    setButtonsAction();
    this.revalidate(); // Ensure the layout is updated
    this.repaint();
}

public SettingsPanel(Panel panel) {
        this.panel = panel;
        this.infoPanel = infoPanel != null ? infoPanel : new InfoPanel(algorithmBox.getSelectedItem().toString(), this);
        initSettingsPanel();
}

private void playMusic() {
    try {
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File("bensound-inspire.wav")));
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
        ex.printStackTrace();
    }
}
       private void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop(); // Stop the music
        }
    }

  private void addButtonsToPanel() {
        // Add components to panel in structured order
        addComponents(speedLabel, speed, speedValue);         // Speed controls
        addComponents(sizeLabel, size, sizeValue);            // Size controls
        addComponents(mazeTypeLabel, mazeTypeBox, uploadButton);  // Maze type and upload button
        addComponents(algorithmLabel, algorithmBox);          // Algorithm selection
    
        // Conditionally add heuristic selection based on algorithm choice
        if ("A*".equals(algorithmBox.getSelectedItem())) {
            addComponents(heuristicLabel, heuristicBox);
        }
    
        // Add functional buttons to panel
        addComponents(toggleButton, startButton, resetButton, stop);
    
        // Add information panel and details button
        this.add(info);
        setupMoreInfoButton();  // Setup the 'Algorithm Details' button
        
        this.revalidate();
        this.repaint();
    }
    
    // Helper method to add multiple components to the panel
    private void addComponents(JComponent... components) {
        for (JComponent component : components) {
            this.add(component);
        }
    }
    
    // Setup for the 'Algorithm Details' button
    private void setupMoreInfoButton() {
        getMoreInfoButton = new JButton("Algorithm Details");
        getMoreInfoButton.setBackground(Color.ORANGE);
        getMoreInfoButton.setForeground(Color.BLACK);
        getMoreInfoButton.setFocusPainted(false);
        getMoreInfoButton.setFont(new Font("Arial", Font.BOLD, 14));
        getMoreInfoButton.setToolTipText("Get detailed explanation of the selected algorithm");
     
        // Action listener for button click
        getMoreInfoButton.addActionListener(e -> {
            System.out.println("Algorithm Details button clicked");
            if (infoPanel != null) {
                infoPanel.showMoreInfo();
            } else {
                System.out.println("infoPanel is null");
            }
        });
    
        // Add the button to the panel
        this.add(getMoreInfoButton);
    }
    
    // Helper method to set button background and repaint
    private void setButtonBackground(JButton button, Color color) {
        button.setBackground(color);
        button.repaint();
    }
    
    private void setButtonsBounds() {
        int downShift = 20;
        int labelWidth = 50;
        int scrollWidth = 150;
        int buttonWidth = 140;
        int fontSize = 15;
        // speed bounds
        speedLabel.setBounds((SETTINGS_WIDTH - labelWidth) / 2 - scrollWidth, downShift, labelWidth, downShift);
        speed.setBounds((SETTINGS_WIDTH - scrollWidth) / 2, downShift, scrollWidth, 20);
        speedValue.setBounds((SETTINGS_WIDTH - labelWidth) / 2 + scrollWidth, downShift, labelWidth, downShift);
        // size bounds
        sizeLabel.setBounds((SETTINGS_WIDTH - labelWidth) / 2 - scrollWidth, downShift * 3, labelWidth, downShift);
        size.setBounds((SETTINGS_WIDTH - scrollWidth) / 2, downShift * 3, scrollWidth, downShift);
        sizeValue.setBounds((SETTINGS_WIDTH - labelWidth) / 2 + scrollWidth, downShift * 3, labelWidth, downShift);
        // maze algorithm bounds
        mazeTypeLabel.setBounds((SETTINGS_WIDTH - labelWidth) / 2 - scrollWidth, downShift * 5, labelWidth, downShift);
        mazeTypeBox.setBounds((SETTINGS_WIDTH - scrollWidth) / 2, downShift * 5, scrollWidth, downShift);
        uploadButton.setBounds((SETTINGS_WIDTH - buttonWidth) / 2, downShift * 6, buttonWidth, downShift);
        uploadButton.setVisible(false); // Initially hidden, will appear when "Upload Text File" is selected

        // algorithm bounds
        algorithmLabel.setBounds((SETTINGS_WIDTH - labelWidth) / 2 - scrollWidth, downShift * 7,
                labelWidth * 2, downShift);
        algorithmBox.setBounds((SETTINGS_WIDTH - scrollWidth) / 2, downShift * 7, scrollWidth, downShift);

        // heuristic bounds
        heuristicLabel.setBounds((SETTINGS_WIDTH - labelWidth) / 2 - scrollWidth,
                downShift * 9, labelWidth * 3, downShift);
        heuristicBox.setBounds((SETTINGS_WIDTH - scrollWidth) / 2 + labelWidth,
                downShift * 9, scrollWidth, downShift);
        if(Objects.equals(algorithmBox.getSelectedItem(), "A*")) {
            heuristicBox.setEnabled(true);
        } else {
            heuristicBox.setEnabled(false);
        }
        // function button bounds
        startButton.setBounds((SETTINGS_WIDTH - buttonWidth) / 2, downShift * 11, buttonWidth, downShift);
        resetButton.setBounds((SETTINGS_WIDTH - buttonWidth) / 2, downShift * 13, buttonWidth, downShift);
        stop.setBounds((SETTINGS_WIDTH - buttonWidth) / 2, downShift * 15, buttonWidth, downShift);

        toggleButton.setBounds((SETTINGS_WIDTH - buttonWidth-1) / 2, downShift * 10, buttonWidth, downShift);
        toggleButton.setVisible(true);

// info bounds (for displaying algorithm info)
info.setBounds(0, downShift * 17, SETTINGS_WIDTH, fontSize * 32); // Increased height
        info.setFont(new Font("Arial", Font.BOLD, fontSize));
        info.setForeground(Color.RED);
        info.setHorizontalAlignment(JLabel.CENTER);
        info.setVerticalAlignment(JLabel.CENTER);
        getMoreInfoButton.setBounds((SETTINGS_WIDTH - buttonWidth) / 2, downShift * 17, buttonWidth, downShift);

    }

    private void setButtonsAction() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetPath();
                stopMusic();

                if (!isPathfindingActive) {
                    System.out.println("Starting pathfinding..."); // Debug log
                    isPathfindingActive = true; // Set flag to true when pathfinding starts
                    playMusic(); // Start music when the algorithm starts
    
                    SearchAlgo pathFinding = switch (algorithmBox.getSelectedItem().toString()) {
                        case "Dijkstra" -> new Dijkstra(panel);
                        case "A*" -> new aStar(panel);
                        case "DFS" -> new DFS(panel);
                        case "BFS" -> new BFS(panel);
                        case "Recursive Backtracking" -> new RBT(panel);
                        default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithmBox.getSelectedItem());
                    };
    
                    // Execute the pathfinding logic
                    new Thread(() -> {
                        pathFinding.execute(); // Start pathfinding in a separate thread
                        isPathfindingActive = false; // Reset flag when done
                        stopMusic(); // Stop the music when done
                        System.out.println("Pathfinding complete."); // Debug log
                        SwingUtilities.invokeLater(() -> panel.repaint()); // Ensure UI updates on EDT
                    }).start();
                } else {
                    System.out.println("Pathfinding already active."); // Debug log
                }
            }
        });
        
     mazeTypeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mazeTypeBox.getSelectedItem().toString().equals("Upload Text File")) {
                    uploadButton.setVisible(true);
                } else {
                    uploadButton.setVisible(false);
                }
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopMusic();

                if (isPathfindingActive) {
                    // Logic to stop the pathfinding process if applicable
                    System.out.println("Stopping pathfinding..."); // Debug log
                    // Add code to stop the pathfinding algorithm if it supports interruption
                    stopMusic(); // Stop music when drawing stops
                }
                drawPath = !drawPath; // Toggle draw path state
                stop.setText(drawPath ? "Stop" : "Start");
                panel.repaint();
                stopMusic();

            }
        
        });
        
// Action Listener for the Upload Button
uploadButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            updateSelectedFileLabel(); // Update the label to show the selected file name
        }
    }
});

        // Add components to the panel
        add(uploadButton);
        add(selectedFileLabel);
    
// Action Listener for the Reset Button (which may also be the Generate button)
resetButton.addActionListener(new ActionListener() {
    @Override
    
    public void actionPerformed(ActionEvent e) {
        playMusic(); // Play music when settings panel is created

        panel.resetPanel();

        String selectedMaze = mazeTypeBox.getSelectedItem().toString();

        // If "Upload Text File" is selected
        if (selectedMaze.equals("Upload Text File")) {
            if (selectedFile == null) {
                JOptionPane.showMessageDialog(null, "Please upload a maze file first.", "No File Selected", JOptionPane.WARNING_MESSAGE);
                return; // Exit if no file selected
            }

            // Generate maze from the selected text file
            try {
                generateMazeFromTextFile(selectedFile);
                JOptionPane.showMessageDialog(null, "Maze generated from file successfully.", "Maze Generated", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error reading the file. Please try again.", "File Error", JOptionPane.ERROR_MESSAGE);
            }
            return; // Exit to prevent further execution
        }

        // Handling other maze types if applicable
        MazeAlgo maze = switch (selectedMaze) {
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

        // Execute maze algorithm if not null
        if (maze != null) {
            maze.execute();
            // stopMusic();
        }
    }
});

// Method to load maze from a text file
        speed.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                speedValue.setText(String.valueOf(speed.getValue()) + " fps");
                solvingSpeed = speed.getValue();
                speedValue.repaint();
                speed.repaint();
            }
        });

        size.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                sizeValue.setText(String.valueOf(size.getValue()) + " px");
                sizeValue.repaint();
                size.repaint();
                nodeSize = size.getValue();
                panel.resetPanel();
                panel.repaint();
            }
        });

        heuristicBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = heuristicBox.getSelectedIndex();
                Node.heuristicFunction.setType(HeuristicFunction.Heuristic.values()[index]);
                for (int row = 0; row < numRow; row++) {
                    for (int col = 0; col < numCol; col++) {
                        nodesGrid[row][col].setCost();
                    }
                }
            }
        });

        algorithmBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = algorithmBox.getSelectedIndex();
                if (index == 1) {
                    heuristicBox.setEnabled(true);
                    SettingsPanel.this.add(heuristicLabel);
                    SettingsPanel.this.add(heuristicBox);
                } else {
                    heuristicBox.setSelectedIndex(0);
                    heuristicBox.setEnabled(false);
                    SettingsPanel.this.remove(heuristicLabel);
                    SettingsPanel.this.remove(heuristicBox);
                }
            }
        });

       toggleButton.addActionListener(e -> {
            panel.toggleMode(); // Toggle the mode directly
        
            // Update the button text based on the current mode
            if (panel.isDarkMode()) {
                toggleButton.setText("Light Mode");
                info.setForeground(Color.WHITE);
            } else {
                toggleButton.setText("Dark Mode");
                info.setForeground(Color.BLACK);
            }
        
            panel.repaint(); // Refresh panel to show changes
        });  
    }

       private void resetPath() {
        for (Util.Node[] nodes : nodesGrid) {
            for (int j = 0; j < nodes.length; j++) {
                nodes[j].resetPath();
            }
        }
        pathLength = 0;
        numNodesExpanded = 0;
        searchTime = 0;
        pathNodes = new ArrayList<>();
        panel.repaint();
    }

    public void setInfo(String info) {
        this.info.setText(info);
    }

    public String getSelectedAlgorithm() {
        return (String) algorithmBox.getSelectedItem();
    }
    
    public void enableButtons(boolean b) {
        // size bounds
        size.setEnabled(b);
        // function buttons
        startButton.setEnabled(b);
        resetButton.setEnabled(b);
        stop.setEnabled(b);
    }

        // Method to load maze from a text file
        public void generateMazeFromTextFile(File selectedFile) throws IOException {
            try (Scanner scanner = new Scanner(selectedFile)) {
                if (!scanner.hasNextInt()) {
                    JOptionPane.showMessageDialog(null, "Invalid file format. First two numbers should be rows and columns.", "File Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int rows = scanner.nextInt();
                int cols = scanner.nextInt();
        
                // Set an upper limit for rows and columns to prevent excessive memory usage
                int maxSize = 1000; // Example limit
                if (rows <= 0 || cols <= 0 || rows > maxSize || cols > maxSize) {
                    JOptionPane.showMessageDialog(null, "Maze size is too large or invalid. Maximum allowed size is " + maxSize + "x" + maxSize + ".", "File Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                int[][] maze = new int[rows][cols];
        
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        if (!scanner.hasNextInt()) {
                            JOptionPane.showMessageDialog(null, "Invalid file format. Not enough maze data.", "File Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        maze[i][j] = scanner.nextInt();
                    }
                }
                        // Update the panel with the new maze
                panel.setMazeDimensions(rows, cols);
                panel.setMaze(maze);
                System.out.println("Maze loaded successfully with " + rows + " rows and " + cols + " cols.");
            } catch (IOException e) {
                System.out.println("File not found: " + selectedFile.getName());
                throw e;
            }
        }
    
private void updateSelectedFileLabel() {
    selectedFileLabel.setText(selectedFile.getName());
}
}