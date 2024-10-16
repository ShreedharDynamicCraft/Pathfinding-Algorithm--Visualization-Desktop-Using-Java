

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import Common.Header;

public class Main {
    public static void main(String[] args) {
        // Ask user for their name
        String username = JOptionPane.showInputDialog(null, "Please enter your name:");

        // Proceed only if a name was provided
        if (username != null && !username.trim().isEmpty()) {
            // Ask the user what they want to explore
            String[] options = {"Graph-Weighted Pathfinding Visualizer", "Maze-Based Pathfinding Visualizer"};
            int choice = JOptionPane.showOptionDialog(null, 
                "Which pathfinding visualizer would you like to explore?", 
                "Select Pathfinding Visualizer", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null, options, options[0]);

            // Create and initialize the Header with the chosen visualizer
            SwingUtilities.invokeLater(() -> {
                Header frame = Header.getInstance(username);
                frame.init(choice);
            });
        } else {
            System.out.println("No username provided. Exiting...");
            System.exit(0); // Exit if no username is entered
        }
    }
}
