package Helper;

import Backend.Node;
import GraphicalUI.CustomPopup;
import static GraphicalUI.Frame.frameRate;
import GraphicalUI.Panel;
import static GraphicalUI.Panel.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

public abstract class SearchAlgo extends SwingWorker<Void, Void> {
    protected final Panel panel;
    protected long startTime;
    private final List<Node> pathList = new ArrayList<>();

    public SearchAlgo(Panel panel) {
        this.panel = panel;
    }

    protected abstract void find();
    protected abstract void initializeNodes();

    @Override
    protected Void doInBackground() throws Exception {
        // Check if start or end node is missing
        if (START_NODE == null || END_NODE == null) {
            String missingNodeMsg = START_NODE == null ? "Start" : "End";
            // JOptionPane.showMessageDialog(null, "Please select the " + missingNodeMsg + " Node to continue.");


// Display a more customized and beautiful message
JOptionPane.showMessageDialog(
    null, 
    "<html><h2 style='color:blue;'>Action Required</h2><p>Please select the <b style='color:red;'>" + missingNodeMsg + "</b> Node to continue.</p></html>", 
    "Missing Node Alert", 
    JOptionPane.WARNING_MESSAGE, 
    UIManager.getIcon("OptionPane.warningIcon")
);


            System.out.println("Error: " + missingNodeMsg + " node is missing.");
            return null;
        }

        System.out.println("Start Node: " + START_NODE + ", End Node: " + END_NODE + ". Proceeding...");
        CustomPopup.showPopupMessage("Exploring Path from Start to End...", 1100);

        CustomPopup.playMusic();
        panel.settingsPanel.enableButtons(false);
        // Initialize nodes and perform the search
        initializeNodes();
        find();
        tracePath();
        panel.settingsPanel.enableButtons(true);
        CustomPopup.stopMusic();
        return null;
    }

    protected void process() {
        searchTime = System.currentTimeMillis() - startTime;
        panel.updateInfo();
        if (solvingSpeed > 0) frameRate(solvingSpeed);
        panel.repaint();
    }

    protected void tracePath() {
        Node currentNode = END_NODE;
        System.out.println("Tracing the path from End Node...");

        while (currentNode != null && currentNode.getParent() != null && !currentNode.equals(START_NODE)) {
            pathList.add(currentNode);
            currentNode = currentNode.getParent();
            pathLength++;
        }

        Collections.reverse(pathList);
        System.out.println("\n******************\n\nFinal Path List:" + pathList);

        for (Node node : pathList) {
            node.setAsPath();
            pathNodes.add(node);
            process();
        }

        System.out.println("\nPath successfully traced and displayed.");
        CustomPopup.showPopupMessage("Path Found SucessFully", 1100);

    }
}