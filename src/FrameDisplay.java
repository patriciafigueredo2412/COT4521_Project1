import javax.swing.JFrame;

/**
 * Author: Patricia Figueredo Ventura
 * Panther ID: 6613415
 */

public class FrameDisplay extends JFrame {
    private GraphDisplay graphDisplay;

    public FrameDisplay(GraphDisplay graphDisplay) {
        super("Project 1 - Robot Car Animation");
        this.graphDisplay = graphDisplay;

        add(graphDisplay);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public GraphDisplay getGraphDisplay() {
        return graphDisplay;
    }
}