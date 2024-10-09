package Algorithms;

import Util.Node;

import javax.swing.*;

import GraphicalUI.CustomPopup;
import GraphicalUI.Panel;

import static GraphicalUI.Frame.frameRate;
import static GraphicalUI.Panel.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        if (START_NODE == null || END_NODE == null) {
            System.out.println("Start or end node is null");
            return null;
        }

        // Show a beautiful pop-up message for 1 second
        CustomPopup.showPopupMessage("Exploring Path from Start to End...", 1100);

        CustomPopup.playMusic();
        panel.settingsPanel.enableButtons(false);
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
        if (solvingSpeed > 0) {
            frameRate(solvingSpeed);
        }
        panel.repaint();
    }

    protected void tracePath() {
        Node currentNode = END_NODE;
        while (currentNode.getParent() != null && !currentNode.equals(START_NODE)) {
            pathList.add(currentNode);
            currentNode = currentNode.getParent();
            pathLength++;
        }
        Collections.reverse(pathList);
        for (Node node : pathList) {
            node.setAsPath();
            pathNodes.add(node);
            process();
        }
    }

   
}
