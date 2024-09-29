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
    import java.util.Random;

    public class Frame extends JFrame {
        private static Frame instance;
        private Timer titleAnimationTimer;
        private JLabel titleLabel; // JLabel for the title
        private String baseTitle = "Path Finder Visualizer";
        private Random random;

        private boolean isDarkMode = false; // Define this at class level




        private Frame() {
            random = new Random();
        }

        public static Frame getInstance() {
            if (instance == null) {
                instance = new Frame();
            }
            return instance;
        }

        public void init() {
            if (instance != null) {
                // Set the layout
                this.setLayout(new BorderLayout());
        
                // Create a panel for the title and logo
                JPanel titlePanel = new JPanel();
                titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));         
                // Load and set the college logo
                ImageIcon logoIcon = new ImageIcon("Visualization/iiitm-logo.png"); // Use the relative path
        
                // Debug prints
                System.out.println("Logo Path: " + new java.io.File("Visualization/iiitm-logo.png").getAbsolutePath());
                System.out.println("Icon Width: " + logoIcon.getIconWidth());
                System.out.println("Icon Height: " + logoIcon.getIconHeight());
        
                // Check if the icon is properly loaded
                if (logoIcon.getIconWidth() == -1) {
                    System.out.println("Error: Logo image not found or could not be loaded.");
                } else {
                    JLabel logoLabel = new JLabel(logoIcon);
                    logoLabel.setPreferredSize(new Dimension(100, 80)); // Set a preferred size for the logo
                    titlePanel.add(logoLabel); // Add the logo to the panel
                }
        
                // Create and configure the title label
                titleLabel = new JLabel(baseTitle, SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 34)); // Set font to bold
                titleLabel.setPreferredSize(new Dimension(1100, 30)); // Set preferred size
                titlePanel.add(titleLabel); // Add title label to the panel
        
                // Add the title panel to the top
                this.add(titlePanel, BorderLayout.NORTH);
        
                this.getContentPane().add(new Panel(), BorderLayout.CENTER); // Assuming Panel is defined elsewhere
                this.pack();
                this.setLocationRelativeTo(null);
                this.setVisible(true);
        
                // Start the title animation
                startTitleAnimation();
            }
        }
        
        
        

        private void startTitleAnimation() {
            titleAnimationTimer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Random color generation
                    Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                    setTitleWithColor(randomColor);
                }
            });
            titleAnimationTimer.start();
        }

        private void setTitleWithColor(Color color) {
            // Change the title label color
            titleLabel.setForeground(color);
        }

        public static void frameRate(int fps) {
            try {
                Thread.sleep(1000 / fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // public static void toggleFrameMode() {
        //     isDarkMode = !isDarkMode; // Toggle between dark and light modes
        //     this.getContentPane().setBackground(isDarkMode ? Color.BLACK : Color.WHITE); // Update frame background
        //     titleLabel.setForeground(isDarkMode ? Color.WHITE : Color.BLACK); // Optionally update text color
        //     repaint(); // Repaint frame to reflect changes
        // }

        // Assuming isDarkMode is declared as an instance variable
        public void toggleMode() {
            isDarkMode = !isDarkMode; // Toggle mode
            this.getContentPane().setBackground(isDarkMode ? Color.BLACK : Color.WHITE); // Update frame background
            titleLabel.setForeground(isDarkMode ? Color.WHITE : Color.BLACK); // Optionally update text color
            repaint(); // Repaint frame to reflect changes
        }
        

    }
