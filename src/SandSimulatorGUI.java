import particle.ParticleTool;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

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


        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem importImage = new JMenuItem("Import Image");
        importImage.addActionListener((event) -> showFilePopup());
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((event) -> System.exit(0));

        fileMenu.add(importImage);
        fileMenu.add(exit);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(250, 600));
        buttonPanel.setLayout(new GridBagLayout());
        main.add(buttonPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 15, 5, 15);

        // Used LinkedHashMap because it preserves insertion order on iteration
        LinkedHashMap<String, String> buttonNames = new LinkedHashMap<>();
        buttonNames.put("Clear", "_CLEAR");
        buttonNames.put("Erase", "_ERASE");
        buttonNames.put("Sand", "SAND");
        buttonNames.put("Wall", "WALL");
        buttonNames.put("Water", "WATER");
        buttonNames.put("Fire", "FIRE");
        buttonNames.put("Wood", "WOOD");
        buttonNames.put("Lava", "LAVA");
        buttonNames.put("Methane", "METHANE");

        buttons = new JButton[buttonNames.size()];

        int i = 0;
        for (Map.Entry<String, String> entry: buttonNames.entrySet()) {
            buttons[i] = new JButton(entry.getKey());
            buttons[i].setActionCommand(entry.getValue());
            buttons[i].addActionListener(this);
            buttons[i].setPreferredSize(new Dimension(100, 30));
            gbc.gridx = 0;
            gbc.gridy = i;
            buttonPanel.add(buttons[i], gbc);
            i++;
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

    public void showFilePopup() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, PNG & GIF Images", "jpg", "gif", "png", "jpeg");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            sandDisplayPanel.handleFileUpload(chooser.getSelectedFile());
        }
    }

}
