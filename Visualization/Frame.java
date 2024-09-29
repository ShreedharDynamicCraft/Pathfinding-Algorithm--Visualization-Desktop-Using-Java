    // package Visualization;

    // import javax.swing.*;
    // import java.awt.*;
    // import java.awt.event.ActionEvent;
    // import java.awt.event.ActionListener;
    // import java.util.Random;

    // public class Frame extends JFrame {
    //     private static Frame instance;
    //     private Timer titleAnimationTimer;
    //     private JLabel titleLabel; // JLabel for the title
    //     private String baseTitle = "Path Finder Visualizer";
    //     private Random random;

    //     private Frame() {
    //         random = new Random();
    //     }

    //     public static Frame getInstance() {
    //         if (instance == null) {
    //             instance = new Frame();
    //         }
    //         return instance;
    //     }

    //     public void init() {
    //         if (instance != null) {
    //             // Set the layout
    //             this.setLayout(new BorderLayout());

    //             // Create and configure the title label
    //             titleLabel = new JLabel(baseTitle, SwingConstants.CENTER);
    //             titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font to bold
    //             titleLabel.setPreferredSize(new Dimension(400, 50)); // Set preferred size
    //             this.add(titleLabel, BorderLayout.NORTH); // Add title label to the top

    //             this.getContentPane().add(new Panel(), BorderLayout.CENTER); // Assuming Panel is defined elsewhere
    //             this.pack();
    //             this.setLocationRelativeTo(null);
    //             this.setVisible(true);

    //             // Start the title animation
    //             startTitleAnimation();
    //         }
    //     }

    //     private void startTitleAnimation() {
    //         titleAnimationTimer = new Timer(500, new ActionListener() {
    //             @Override
    //             public void actionPerformed(ActionEvent e) {
    //                 // Random color generation
    //                 Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    //                 setTitleWithColor(randomColor);
    //             }
    //         });
    //         titleAnimationTimer.start();
    //     }

    //     private void setTitleWithColor(Color color) {
    //         // Change the title label color
    //         titleLabel.setForeground(color);
    //     }

    //     public static void frameRate(int fps) {
    //         try {
    //             Thread.sleep(1000 / fps);
    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }
    package Visualization;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.util.Random;
    
    public class Frame extends JFrame {
        private static Frame instance;
        private Timer titleAnimationTimer;
        private JLabel titleLabel; // JLabel for the title
        private String baseTitle = "Path Finder Visualizer";
        private Random random;
        private String username; // Store the username
        private boolean isDarkMode = false; // Define dark mode status
    
        // Modified constructor to accept username
        private Frame(String username) {
            this.username = username;
            random = new Random();
        }
    
        // Singleton pattern modified to accept username
        public static Frame getInstance(String username) {
            if (instance == null) {
                instance = new Frame(username);
            }
            return instance;
        }
    
        // Initialize the frame with welcome message and layout
        public void init() {
            if (instance != null) {
                // Set the layout
                this.setLayout(new BorderLayout());
    
                // Create a panel for the title and logo
                JPanel titlePanel = new JPanel();
                titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // Margin for logo and title
    
                // Load and set the college logo
                ImageIcon logoIcon = new ImageIcon("Visualization/iiitm-logo.png"); // Relative path for the logo
    
                // Debug prints for logo loading
                System.out.println("Logo Path: " + new java.io.File("Visualization/iiitm-logo.png").getAbsolutePath());
                System.out.println("Icon Width: " + logoIcon.getIconWidth());
                System.out.println("Icon Height: " + logoIcon.getIconHeight());
    
                // Check if the icon is properly loaded
                if (logoIcon.getIconWidth() == -1) {
                    System.out.println("Error: Logo image not found or could not be loaded.");
                } else {
                    JLabel logoLabel = new JLabel(logoIcon);
                    logoLabel.setPreferredSize(new Dimension(100, 100)); // Set size for the logo
                    titlePanel.add(logoLabel); // Add logo to the panel
                }
    
                // Create and configure the title label
                titleLabel = new JLabel(baseTitle, SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Set font to bold
                titleLabel.setPreferredSize(new Dimension(1000, 50)); // Set preferred size
                titlePanel.add(titleLabel); // Add title label to the panel
    
                // Add a welcome message on the right side of the title panel
                JLabel welcomeLabel = new JLabel("Welcome, " + username);
                welcomeLabel.setFont(new Font("Arial", Font.ITALIC, 20)); // Set font for the welcome message
                welcomeLabel.setForeground(Color.BLUE); // Set text color
    
                // Add styling: Background, Border, and Hover effect
                welcomeLabel.setOpaque(true); // Make background visible
                welcomeLabel.setBackground(Color.LIGHT_GRAY); // Background color
                welcomeLabel.setBorder(BorderFactory.createLineBorder(Color.orange, 2)); // Border
                welcomeLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding
    
                // Add mouse hover effect
                welcomeLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        welcomeLabel.setBackground(Color.ORANGE); // Change background on hover
                        welcomeLabel.setForeground(Color.WHITE); // Change text color on hover
                    }
    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        welcomeLabel.setBackground(Color.LIGHT_GRAY); // Reset background
                        welcomeLabel.setForeground(Color.BLUE); // Reset text color
                    }
    
                    // Mouse click to show project information
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        showProjectInfo(); // Show project info when clicked
                    }
                });
    
                titlePanel.add(welcomeLabel); // Add welcome label to the title panel
    

             // Add the project info button
JButton projectInfoButton = new JButton("Project Info");
projectInfoButton = new JButton("Project Info");
projectInfoButton.setFont(new Font("Arial", Font.PLAIN, 16));
projectInfoButton.setForeground(Color.WHITE);


projectInfoButton.setBackground(new Color(0, 123, 255)); // Blue background
projectInfoButton.setOpaque(true);
projectInfoButton.setBorder(BorderFactory.createCompoundBorder(
    BorderFactory.createLineBorder(Color.ORANGE, 3), // Outer border
    BorderFactory.createEmptyBorder(5, 10, 5, 10)    // Padding inside
));
projectInfoButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover






// Add an action listener to show the project info when the button is clicked
projectInfoButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        showProjectInfo(); // Show project information
    }
});


projectInfoButton.setBounds(-10, 20, 150, 40); // x, y, width, height

// Add the project info button to the title panel
titlePanel.add(projectInfoButton); // Adding button to the left

// Revalidate and repaint the panel to ensure the button shows up
titlePanel.revalidate();
titlePanel.repaint();



                // Add the title panel to the top of the frame
                this.add(titlePanel, BorderLayout.NORTH);
    
                // Add the main content panel (assuming Panel is defined elsewhere)
                this.getContentPane().add(new Panel(), BorderLayout.CENTER);
                this.pack();
                this.setLocationRelativeTo(null);
                this.setVisible(true);
    
                // Start the title animation
                startTitleAnimation();
            }
        }
    
        // Title animation method that changes the title color periodically
        private void startTitleAnimation() {
            titleAnimationTimer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Generate random colors
                    Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                    setTitleWithColor(randomColor); // Set the new title color
                }
            });
            titleAnimationTimer.start();
        }
    
        // Method to change the color of the title label
        private void setTitleWithColor(Color color) {
            titleLabel.setForeground(color); // Change the title label color
        }
    
        // Method to toggle between dark mode and light mode
        public void toggleMode() {
            isDarkMode = !isDarkMode; // Toggle mode
            this.getContentPane().setBackground(isDarkMode ? Color.BLACK : Color.WHITE); // Update frame background
            titleLabel.setForeground(isDarkMode ? Color.WHITE : Color.BLACK); // Update title text color based on mode
            repaint(); // Repaint the frame to reflect changes
        }
    
        // Method to control frame rate (can be useful for animations)
        public static void frameRate(int fps) {
            try {
                Thread.sleep(1000 / fps); // Adjust sleep time to control frame rate
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle interruption
            }
        }
    
        // Method to show project information when the user clicks the welcome label
// Method to display the formatted project report information
private void showProjectInfo() {
    String infoMessage = "<html><body style='text-align: center;'>"
            + "<h2 style='font-size: 28px;'>Path Finding Algorithms Visualizer</h2>"
            + "<p style='font-size: 16px;'><b>A report submitted for the course named Project I (CSXXX)</b></p>"
            + "<p style='margin-top: 40px; font-size: 18px;'><em><b>Submitted By</b></em></p>"
            + "<p style='font-size: 18px;'><b>Shreedhar Anand</b><br>22010138</p>"
            + "<p style='margin-top: 40px; font-size: 18px;'><em>Under the guidance of</em></p>"
            + "<p style='font-size: 18px;'><b>Dr. Navnath Saharia</b></p>"
            + "<p style='margin-top: 20px;'>"
            + "<img src='file:Visualization/iiitm-logo.png' style='width: 80px;'/>"
            + "</p>"
            + "<p style='font-size: 16px;'><b>Department of Computer Science and Engineering<br>"
            + "Indian Institute of Information Technology Senapati, Manipur</b></p>"
            + "<p style='font-size: 16px;'>October 2024</p>"
            + "</body></html>";

    // Create a dialog box to show the formatted information
    JOptionPane.showMessageDialog(this, infoMessage, "Project Information", JOptionPane.INFORMATION_MESSAGE);
}

    }
    