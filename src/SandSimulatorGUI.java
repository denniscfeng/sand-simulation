import particle.ParticleTool;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SandSimulatorGUI implements ActionListener, ChangeListener {

    JFrame frame;
    SandDisplayPanel sandDisplayPanel;
    JButton[] buttons;

    ParticleTool particleTool;
    int brushWidth;

    public SandSimulatorGUI(SandDisplayPanel sandDisplayPanel) {

        this.frame = new JFrame("Sand Simulator");
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.LINE_AXIS));
        frame.getContentPane().add(main);

        this.sandDisplayPanel = sandDisplayPanel;
        main.add(sandDisplayPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(250, 600));
        buttonPanel.setLayout(new GridBagLayout());
        main.add(buttonPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 15, 5, 15);

        ArrayList<String> buttonNames = new ArrayList<>();
        buttonNames.add("Clear");
        buttonNames.add("Erase");
        buttonNames.add("Wall");
        buttonNames.add("Sand");
        buttonNames.add("Water");

        //In the order of above buttonNames, correspond to ParticleType's
        ArrayList<String> buttonCommands = new ArrayList<>();
        buttonCommands.add("_CLEAR");
        buttonCommands.add("_ERASE");
        buttonCommands.add("WALL");
        buttonCommands.add("SAND");
        buttonCommands.add("WATER");

        buttons = new JButton[buttonNames.size()];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonNames.get(i));
            buttons[i].setActionCommand(buttonCommands.get(i));
            buttons[i].addActionListener(this);
            buttons[i].setPreferredSize(new Dimension(100, 30));
            gbc.gridx = 0;
            gbc.gridy = i;
            buttonPanel.add(buttons[i], gbc);
        }

        buttons[2].setSelected(true);
        this.particleTool = ParticleTool.SAND;

        // Slider
        this.brushWidth = 5;
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, brushWidth);
        slider.addChangeListener(this);
        slider.setMajorTickSpacing(3);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        JLabel label = new JLabel("Brush Width");
        gbc.insets = new Insets(15, 15, 5, 15);
        gbc.gridx = 0;
        gbc.gridy = buttons.length + 1;
        buttonPanel.add(label, gbc);
        gbc.insets = new Insets(0, 15, 0, 15);
        gbc.gridx = 0;
        gbc.gridy = buttons.length + 2;
        buttonPanel.add(slider, gbc);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);

    }

    // Button handler
    @Override
    public void actionPerformed(ActionEvent e) {
        setParticleTool(e.getActionCommand());

        for (JButton b: buttons)
            b.setSelected(false);

        if (particleTool != ParticleTool._CLEAR) // Keep option selected
            ((JButton) e.getSource()).setSelected(true);

    }

    // Slider handler
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider brushWidth = (JSlider) e.getSource();
        setBrushWidth(brushWidth.getValue());
    }

    public ParticleTool getParticleTool() {
        return particleTool;
    }

    public void setParticleTool(String buttonCommand) {
        this.particleTool = ParticleTool.valueOf(buttonCommand);
    }

    public int getBrushWidth() {
        return brushWidth;
    }

    public void setBrushWidth(int brushWidth) {
        this.brushWidth = brushWidth;
    }

}
