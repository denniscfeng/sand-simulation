import javax.swing.*;

public class SandSimulatorGUI {

    JFrame frame;
    SandDisplayPanel sandDisplayPanel;

    public SandSimulatorGUI(SandDisplayPanel sandDisplayPanel) {

        this.frame = new JFrame("Sand Simulator");

        this.sandDisplayPanel = sandDisplayPanel;

        this.frame.setContentPane(sandDisplayPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);

    }

}
