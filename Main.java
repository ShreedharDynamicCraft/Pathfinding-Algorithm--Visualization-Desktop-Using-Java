// import Visualization.Frame;

// public class Main {
//     public static void main(String[] args) {
//         Frame frame = Frame.getInstance();
//         frame.init();
//     }
// }


import javax.swing.JOptionPane;

import GraphicalUI.Frame;

public class Main {
    public static void main(String[] args) {
        // Ask user for their name
        String username = JOptionPane.showInputDialog(null, "Please enter your name:");

        // Proceed only if a name was provided
        if (username != null && !username.trim().isEmpty()) {
            // Pass the username to Frame
            Frame frame = Frame.getInstance(username);
            frame.init();
        } else {
            System.out.println("No username provided. Exiting...");
            System.exit(0); // Exit if no username is entered
        }
    }
}
