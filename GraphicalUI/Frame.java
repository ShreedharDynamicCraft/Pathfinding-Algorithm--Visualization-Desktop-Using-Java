package GraphicalUI;

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
            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BorderLayout());
            titlePanel.setBackground(new Color(30, 30, 30)); // Dark background for title panel

            // Load and set the college logo
            ImageIcon logoIcon = new ImageIcon("GraphicalUI/iiitm-logo.png");

            if (logoIcon.getIconWidth() != -1) {
                JLabel logoLabel = new JLabel(logoIcon);
                logoLabel.setPreferredSize(new Dimension(110, 110));
                logoLabel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7)); // Padding around the logo
                titlePanel.add(logoLabel, BorderLayout.WEST); // Add logo to the left
            } else {
                System.out.println("Error: Logo image not found or could not be loaded.");
            }

            // Title in the center
            titleLabel = new JLabel(baseTitle, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 36)); // Font change for modern look
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // Add padding to the title
            titlePanel.add(titleLabel, BorderLayout.CENTER);

            // Panel to hold welcomeLabel and projectInfoButton dynamically
            JPanel userInfoPanel = new JPanel();
            userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.X_AXIS));
            userInfoPanel.setBackground(new Color(30, 30, 30)); // Matching background

            JLabel welcomeLabel = createWelcomeLabel();
            userInfoPanel.add(welcomeLabel); // Add welcome label

            userInfoPanel.add(Box.createHorizontalStrut(10)); // Add space between username and button

            JButton projectInfoButton = createProjectInfoButton();
            userInfoPanel.add(projectInfoButton); // Add project info button

            userInfoPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            titlePanel.add(userInfoPanel, BorderLayout.EAST); // Add to the right

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
        welcomeLabel.setFont(new Font("Sans Serif", Font.ITALIC, 28)); // Slightly larger font
        welcomeLabel.setForeground(Color.CYAN);
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(new Color(50, 50, 50)); // Dark background for welcome label
        welcomeLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2)); // Border around label
        welcomeLabel.setBorder(BorderFactory.createCompoundBorder(
                welcomeLabel.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 10))); // Padding around the label

        welcomeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                welcomeLabel.setBackground(Color.ORANGE);
                welcomeLabel.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                welcomeLabel.setBackground(new Color(50, 50, 50));
                welcomeLabel.setForeground(Color.CYAN);
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
        projectInfoButton.setFont(new Font("Sans Serif", Font.PLAIN, 22)); // Larger font for button
        projectInfoButton.setForeground(Color.WHITE);
        projectInfoButton.setBackground(new Color(0, 150, 136)); // Teal color for button
        projectInfoButton.setOpaque(true);
        projectInfoButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.ORANGE, 3),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        projectInfoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        projectInfoButton.setFocusPainted(false); // Remove focus border

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
        String infoMessage = "<html><body style='text-align: center;'>"
                + "<h2 style='font-size: 28px;'>Path Finding Algorithms Visualizer</h2>"
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

        JOptionPane.showMessageDialog(this, infoMessage, "Project Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
