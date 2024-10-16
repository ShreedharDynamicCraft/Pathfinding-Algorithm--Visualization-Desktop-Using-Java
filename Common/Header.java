package Common;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Generic_Visulizer.GraphPathfindingVisualizer;
import GraphicalUI.Panel;

public class Header extends JFrame {
    private static Header instance;
    private Timer titleAnimationTimer;
    private JLabel titleLabel;
    private String baseTitle = "Path Finder Visualizer";
    private Random random;
    private String username;
    private boolean isDarkMode = false;
    private Color titlePanelColor = Color.decode("#1636A0");
    private JPanel contentPanel;
    private int currentChoice;

    private Header(String username) {
        this.username = username;
        random = new Random();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 900); // Set a default size
    }

    public static Header getInstance(String username) {
        if (instance == null) {
            instance = new Header(username);
        }
        return instance;
    }

    public void init(int choice) {
        this.currentChoice = choice;
        this.setLayout(new BorderLayout());
        JPanel titlePanel = createTitlePanel();
        this.add(titlePanel, BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        updateContent();
        this.add(contentPanel, BorderLayout.CENTER);

        titleLabel.setText(baseTitle);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        startTitleAnimation();
    }

    private void updateContent() {
        contentPanel.removeAll();
        if (currentChoice == 0) {
            contentPanel.add(new GraphPathfindingVisualizer(), BorderLayout.CENTER);
            baseTitle = "Graph-Weighted Pathfinding Visualizer";
            CustomPopup.showPopupMessage("Switched To Graph-Weighted Pathfinding Visualizer Panel", 1500);

        } else {
            contentPanel.add(new Panel(), BorderLayout.CENTER);
            baseTitle = "Maze-Based Pathfinding Visualizer";
            CustomPopup.showPopupMessage("Switched To Maze-Based Pathfinding Visualizer Panel", 1500);

        }
        titleLabel.setText(baseTitle);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(titlePanelColor);

        // Switch button on the left
        JButton switchButton = createSwitchButton();
        titlePanel.add(switchButton, BorderLayout.WEST);

        // Title in the center
        titleLabel = new JLabel(baseTitle, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Panel to hold welcomeLabel and projectInfoButton
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.X_AXIS));
        userInfoPanel.setBackground(titlePanelColor);

        JLabel welcomeLabel = createWelcomeLabel();
        userInfoPanel.add(welcomeLabel);
        userInfoPanel.add(Box.createHorizontalStrut(10));

        JButton projectInfoButton = createProjectInfoButton();
        userInfoPanel.add(projectInfoButton);

        userInfoPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        titlePanel.add(userInfoPanel, BorderLayout.EAST);

        return titlePanel;
    }

    private JButton createSwitchButton() {
        JButton switchButton = new JButton("Switch Visualizer");
        switchButton.setFont(new Font("Sans Serif", Font.BOLD, 16));
        switchButton.setForeground(Color.WHITE);
        switchButton.setBackground(new Color(30, 144, 255)); // Dodger Blue
        switchButton.setOpaque(true);
        switchButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        switchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        switchButton.setFocusPainted(false);

        switchButton.addActionListener(e -> {
            currentChoice = 1 - currentChoice; // Toggle between 0 and 1
            updateContent();
        });

        switchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                switchButton.setBackground(new Color(65, 105, 225)); // Royal Blue
            }

            @Override
            public void mouseExited(MouseEvent e) {
                switchButton.setBackground(new Color(30, 144, 255));
            }
        });

        return switchButton;
    }



        private JLabel createWelcomeLabel() {
            JLabel welcomeLabel = new JLabel("Welcome, " + username);
            welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 28));
            welcomeLabel.setForeground(new Color(255, 255, 255)); // White text
            welcomeLabel.setOpaque(true);
            welcomeLabel.setBackground(new Color(41, 128, 185)); // Bright blue
            welcomeLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(52, 152, 219), 2), // Light blue border
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        
            welcomeLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    welcomeLabel.setBackground(new Color(52, 152, 219)); // Lighter blue on hover
                    welcomeLabel.setForeground(new Color(255, 255, 255)); // Keep text white
                    welcomeLabel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(41, 128, 185), 2),
                            BorderFactory.createEmptyBorder(8, 15, 8, 15)));
                    welcomeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
        
                @Override
                public void mouseExited(MouseEvent e) {
                    welcomeLabel.setBackground(new Color(41, 128, 185)); // Back to original blue
                    welcomeLabel.setForeground(new Color(255, 255, 255)); // White text
                    welcomeLabel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                            BorderFactory.createEmptyBorder(8, 15, 8, 15)));
                    welcomeLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
        
                @Override
                public void mousePressed(MouseEvent e) {
                    welcomeLabel.setBackground(new Color(31, 97, 141)); // Darker blue when pressed
                }
        
                @Override
                public void mouseReleased(MouseEvent e) {
                    welcomeLabel.setBackground(new Color(52, 152, 219)); // Back to hover color
                }
        
                @Override
                public void mouseClicked(MouseEvent e) {
                    showProjectInfo();
                }
            });
            return welcomeLabel;
        }
        
        private JButton createProjectInfoButton() {
            JButton projectInfoButton = new JButton("Project Info");
            projectInfoButton.setFont(new Font("Sans Serif", Font.ITALIC, 22));
            projectInfoButton.setForeground(Color.WHITE);
            projectInfoButton.setBackground(new Color(30, 144, 255)); // Dodger Blue
            projectInfoButton.setOpaque(true);
            projectInfoButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 3),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            projectInfoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            projectInfoButton.setFocusPainted(false);

            projectInfoButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    projectInfoButton.setBackground(new Color(65, 105, 225)); // Royal Blue
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    projectInfoButton.setBackground(new Color(30, 144, 255));
                }
            });

            projectInfoButton.addActionListener(e -> showProjectInfo());

            return projectInfoButton;
        }

        private void startTitleAnimation() {
            titleAnimationTimer = new Timer(500, e -> {
                Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                setTitleWithColor(randomColor);
            });
            titleAnimationTimer.start();
        }

        private void setTitleWithColor(Color color) {
            titleLabel.setForeground(color);
        }

        public static void frameRate(int fps) {
            try {
                Thread.sleep(1000 / fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void toggleMode() {
            isDarkMode = !isDarkMode;
            this.getContentPane().setBackground(isDarkMode ? Color.BLACK : Color.WHITE);
            titleLabel.setForeground(isDarkMode ? Color.WHITE : Color.BLACK);
            repaint();
        }


        private void showProjectInfo() {
            // Prepare styled HTML message content
            String infoMessage = "<html><body style='text-align: center;'>"
                    + "<h2 style='font-size: 28px; color: #4CAF50;'>Path Finding Algorithms Visualizer</h2>"
                    + "<p style='font-size: 16px;'><b>A report submitted for the course named Project I (CS3201)</b></p>"
                    + "<p style='margin-top: 40px; font-size: 18px;'><em><b>Submitted By</b></em></p>"
                    + "<p style='font-size: 18px;'><b>Shreedhar Anand</b><br>220101038</p>"
                    + "<p style='margin-top: 40px; font-size: 18px;'><em>Under the guidance of</em></p>"
                    + "<p style='font-size: 18px;'><b>Dr. Navnath Saharia</b></p>"
                    + "<p style='margin-top: 20px;'>"
                    + "<img src='file:GraphicalUI/iiitm-logo.png' style='width: 80px;'/>"
                    + "</p>"
                    + "<p style='font-size: 16px;'><b>Department of Computer Science and Engineering<br>"
                    + "Indian Institute of Information Technology Senapati, Manipur</b></p>"
                    + "<p style='font-size: 16px;'>October 2024</p>"
                    + "</body></html>";
        
            // Create a panel for the message with the icon
            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(infoMessage);
        
            // GitHub link button
            JButton gitHubButton = new JButton("<html><u>View GitHub Repo</u></html>");
            gitHubButton.setForeground(Color.BLUE.darker());
            gitHubButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            gitHubButton.setBorderPainted(false);
            gitHubButton.setOpaque(false);
            gitHubButton.setBackground(Color.WHITE);
        
            gitHubButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new java.net.URI("https://github.com/ShreedharDynamicCraft/Pathfinding-Algorithm--Visualization-Desktop-Using-Java"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        
            // Linktree link button
            JButton linktreeButton = new JButton("<html><u>Visit Linktree Profile</u></html>");
            linktreeButton.setForeground(Color.BLUE.darker());
            linktreeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            linktreeButton.setBorderPainted(false);
            linktreeButton.setOpaque(false);
            linktreeButton.setBackground(Color.WHITE);
        
            linktreeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new java.net.URI("https://linktr.ee/IIITM.Shreedhar"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        
            // Add components to panel
            panel.add(label, BorderLayout.CENTER);
        
            // Panel for buttons (GitHub, Linktree)
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(gitHubButton);
            buttonPanel.add(linktreeButton);
        
            panel.add(buttonPanel, BorderLayout.SOUTH);
        
            // Show a JOptionPane with the panel as the message
            JOptionPane.showMessageDialog(this, panel, "Project Information", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
