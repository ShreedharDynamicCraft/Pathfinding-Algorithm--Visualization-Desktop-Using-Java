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
    private JLabel titleLabel;
    private String baseTitle = "Path Finder Visualizer";
    private Random random;
    private String username;
    private boolean isDarkMode = false;

    private Frame(String username) {
        this.username = username;
        random = new Random();
    }

    public static Frame getInstance(String username) {
        if (instance == null) {
            instance = new Frame(username);
        }
        return instance;
    }

    public void init() {
        if (instance != null) {
            this.setLayout(new BorderLayout());
            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

            // Load and set the college logo
            ImageIcon logoIcon = new ImageIcon("Visualization/iiitm-logo.png");

            // Debug prints for logo loading
            System.out.println("Logo Path: " + new java.io.File("Visualization/iiitm-logo.png").getAbsolutePath());
            System.out.println("Icon Width: " + logoIcon.getIconWidth());
            System.out.println("Icon Height: " + logoIcon.getIconHeight());

            if (logoIcon.getIconWidth() != -1) {
                JLabel logoLabel = new JLabel(logoIcon);
                logoLabel.setPreferredSize(new Dimension(100, 100));
                titlePanel.add(logoLabel);
            } else {
                System.out.println("Error: Logo image not found or could not be loaded.");
            }

            titleLabel = new JLabel(baseTitle, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
            titleLabel.setPreferredSize(new Dimension(1000, 50));
            titlePanel.add(titleLabel);

            JLabel welcomeLabel = createWelcomeLabel();
            titlePanel.add(welcomeLabel);

            JButton projectInfoButton = createProjectInfoButton();
            titlePanel.add(projectInfoButton);

            this.add(titlePanel, BorderLayout.NORTH);
            this.getContentPane().add(new Panel(), BorderLayout.CENTER);

            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);

            startTitleAnimation();
        }
    }

    private JLabel createWelcomeLabel() {
        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        welcomeLabel.setForeground(Color.BLUE);
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(Color.LIGHT_GRAY);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        welcomeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                welcomeLabel.setBackground(Color.ORANGE);
                welcomeLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                welcomeLabel.setBackground(Color.LIGHT_GRAY);
                welcomeLabel.setForeground(Color.BLUE);
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
        projectInfoButton.setFont(new Font("Arial", Font.PLAIN, 16));
        projectInfoButton.setForeground(Color.WHITE);
        projectInfoButton.setBackground(new Color(0, 123, 255));
        projectInfoButton.setOpaque(true);
        projectInfoButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.ORANGE, 3),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        projectInfoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
    // Toggle between dark mode and light mode

    public void toggleMode() {
        isDarkMode = !isDarkMode;
        this.getContentPane().setBackground(isDarkMode ? Color.BLACK : Color.WHITE);
        titleLabel.setForeground(isDarkMode ? Color.WHITE : Color.BLACK);
        repaint();
    }

    public static void frameRate(int fps) {
        try {
            Thread.sleep(1000 / fps);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // Display project information in a dialog

    private void showProjectInfo() {
        String infoMessage = "<html><body style='text-align: center;'>"
                + "<h2 style='font-size: 28px;'>Path Finding Algorithms Visualizer</h2>"
                + "<p style='font-size: 16px;'><b>A report submitted for the course named Project I (CS3201)</b></p>"
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

        JOptionPane.showMessageDialog(this, infoMessage, "Project Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
