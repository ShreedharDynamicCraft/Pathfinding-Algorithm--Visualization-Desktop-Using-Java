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
            titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align components to the left

            // Load and set the college logo
            ImageIcon logoIcon = new ImageIcon("./iiitm-logo.png"); // Update the path to your logo
            JLabel logoLabel = new JLabel(logoIcon);
            titlePanel.add(logoLabel); // Add the logo to the panel

            // Create and configure the title label
            titleLabel = new JLabel(baseTitle, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font to bold
            titleLabel.setPreferredSize(new Dimension(400, 50)); // Set preferred size
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
}
