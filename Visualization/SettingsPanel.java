package Visualization;

import Algorithms.Create.*;
import Algorithms.Search.*;
import Util.HeuristicFunction;
// import Util.MazeGenerator;
import Util.Node;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.Objects;

import static Visualization.Panel.*;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import java.io.FileNotFoundException;
import java.util.Scanner;

import java.awt.event.*; // For ActionListener and AdjustmentListener


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

    JButton uploadButton = new JButton("Upload Maze");
    private JLabel selectedFileLabel;
    
    
    private Clip clip; // Field to manage sound

    private boolean isPathfindingActive = false; // Flag to track pathfinding state

 // Variable to hold the selected file
 private File selectedFile = null;


    
    public SettingsPanel(Panel panel) {
        this.panel = panel;
        this.setPreferredSize(new Dimension(SETTINGS_WIDTH, SETTINGS_HEIGHT));
        this.setLayout(null);
        Color sky = new Color(70, 116, 176); // RGB values for sky blue
        this.setBackground(sky);
        this.setBounds(Panel.WIDTH - SETTINGS_WIDTH, 0, SETTINGS_WIDTH, SETTINGS_HEIGHT);
        this.add(uploadButton);
        selectedFileLabel = new JLabel("No file selected.");
        add(selectedFileLabel);
        addButtonsToPanel();
        setButtonsBounds();
        setButtonsAction();
    }

    private void playMusic() {
        try {
            File musicFile = new File("bensound-inspire.wav"); // Path to your music file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Play music in a loop
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
        // add speed to panel

        
        this.add(speedLabel);
        this.add(speed);
        this.add(speedValue);
        // add size to panel
        this.add(sizeLabel);
        this.add(size);
        this.add(sizeValue);
        // add maze type to panel
        this.add(mazeTypeLabel);
        this.add(mazeTypeBox);
        this.add(uploadButton); // Add upload button to panel
        // add algorithm to panel
        this.add(algorithmLabel);
        this.add(algorithmBox);
        // add heuristic to panel
        if(Objects.equals(algorithmBox.getSelectedItem(), "A*")){
            this.add(heuristicLabel);
            this.add(heuristicBox);
        }
        // add function buttons to panel
        this.add(startButton);
        this.add(resetButton);
        this.add(stop);
        // add info to panel
        this.add(info);
    }

    private void setButtonsBounds() {
        int downShift = 20;
        int labelWidth = 50;
        int scrollWidth = 150;
        int buttonWidth = 100;
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


        

// info bounds (for displaying algorithm info)
info.setBounds(0, downShift * 17, SETTINGS_WIDTH, fontSize * 32); // Increased height
        info.setFont(new Font("Arial", Font.BOLD, fontSize));
        info.setForeground(Color.RED);
        info.setHorizontalAlignment(JLabel.CENTER);
        info.setVerticalAlignment(JLabel.CENTER);
    }

    private void setButtonsAction() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetPath();
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
                if (isPathfindingActive) {
                    // Logic to stop the pathfinding process if applicable
                    System.out.println("Stopping pathfinding..."); // Debug log
                    // Add code to stop the pathfinding algorithm if it supports interruption
                    stopMusic(); // Stop music when drawing stops
                }
                drawPath = !drawPath; // Toggle draw path state
                stop.setText(drawPath ? "Stop" : "Start");
                panel.repaint();
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
            case "Recursive Backtracking" -> new RBTMaze(panel);
            case "Prim's" -> new PrimMaze(panel);
            case "Hunt & Kill" -> new HuntAndKillMaze(panel);
            case "Binary Tree" -> new BinaryTreeMaze(panel);
            case "Wilson's" -> new WilsonMaze(panel);
            case "Kruskal's" -> new KruskalMaze(panel);
            case "Aldous-Broder" -> new AldousBroder(panel);
            case "Recursive Division" -> new RecursiveDivision(panel);
            default -> null;
        };

        // Execute maze algorithm if not null
        if (maze != null) {
            maze.execute();
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




        // uploadButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         JFileChooser fileChooser = new JFileChooser();
        //         int returnValue = fileChooser.showOpenDialog(null);
        //         if (returnValue == JFileChooser.APPROVE_OPTION) {
        //             File selectedFile = fileChooser.getSelectedFile();
        //             // Add logic to handle the uploaded maze file
        //             System.out.println("Maze file uploaded: " + selectedFile.getAbsolutePath());
        //         }
        //     }
        // });
        
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

        panel.setMazeDimensions(rows, cols);
        panel.setMaze(maze);
    } catch (IOException e) {
        System.out.println("File not found: " + selectedFile.getName());
        throw e;
    }
}

private void updateSelectedFileLabel() {
    selectedFileLabel.setText(selectedFile.getName());
}



  }
        
                