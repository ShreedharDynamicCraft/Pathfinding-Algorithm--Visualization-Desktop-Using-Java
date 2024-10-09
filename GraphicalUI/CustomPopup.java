package GraphicalUI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CustomPopup {
    private static Clip clip; // Make clip static for static methods

    public static void showPopupMessage(String message, int durationMillis) {
        JWindow popup = new JWindow();
        popup.setLayout(new BorderLayout());
    
        // Create the message label with customized styling
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Verdana", Font.BOLD, 21)); // Modern and readable font
        label.setForeground(Color.cyan);
        label.setOpaque(true);
        label.setBackground(new Color(52, 73, 94)); // Stylish dark background
    
        // Add some padding and rounded corners
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(236, 240, 241), 3, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    
        // Add drop shadow for a more modern effect
        popup.getRootPane().setOpaque(false);
        popup.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.DARK_GRAY));
    
        popup.add(label, BorderLayout.CENTER);
    
        // Create and add the dismiss button
        JButton dismissButton = new JButton("Dismiss");
        dismissButton.setFont(new Font("Verdana", Font.BOLD, 16));
        dismissButton.setForeground(Color.yellow);
        dismissButton.setFocusPainted(false);
        // dismissButton.setBorder(BorderFactory.createLineBorder(new Color(236, 240, 241), 2));
        dismissButton.setBorder(BorderFactory.createEmptyBorder()); // Remove default border

        // Action for the dismiss button
        dismissButton.addActionListener(e -> {
            popup.setVisible(false);
            popup.dispose(); // Destroy the window immediately
        });
    
        // Panel to hold button with padding
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(52, 73, 94)); // Same background as the label
        buttonPanel.add(dismissButton);
    
        popup.add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the bottom of the popup
    
        // Dynamically set the popup size based on message length
        int width = Math.max(400, 20 * message.length()); // Minimum width of 400, increase based on message length
        popup.setSize(width, 200); // Keep the height constant for better layout
        popup.setLocationRelativeTo(null); // Center the popup on the screen
        popup.setVisible(true);
    
        // Timer to close the popup after the specified duration
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (popup.isVisible()) {
                    popup.setVisible(false);
                    popup.dispose(); // Destroy the window after hiding it
                }
            }
        }, durationMillis);
    }
    
    // Static method to play music
    public static void playMusic() {
        try {
            // Ensure the clip is stopped and closed before starting new music
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }

            // Load and play the music file
            clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("bensound-inspire.wav"));
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Static method to stop music
    public static void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop(); // Stop the music
            clip.close(); // Free system resources
        }
    }
}
