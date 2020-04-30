import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SandSimulatorGUI implements ActionListener, ChangeListener {

    JFrame frame;
    SandDisplayPanel sandDisplayPanel;
    JButton[] buttons;

    int tool;
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

        List<String> buttonNames = new ArrayList<>();
        buttonNames.add("Clear");
        buttonNames.add("Erase");
        buttonNames.add("Sand");
        buttonNames.add("Water");

        buttons = new JButton[buttonNames.size()];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonNames.get(i));
            buttons[i].setActionCommand("" + i);
            buttons[i].addActionListener(this);
            buttons[i].setPreferredSize(new Dimension(100, 30));
            gbc.gridx = 0;
            gbc.gridy = i;
            buttonPanel.add(buttons[i], gbc);
        }

        buttons[2].setSelected(true);
        this.tool = 2;

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
        setTool(Integer.parseInt(e.getActionCommand()));

        for (JButton b: buttons)
            b.setSelected(false);

        if (tool != 0) // Keep option selected
            ((JButton) e.getSource()).setSelected(true);

    }

    // Slider handler
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider brushWidth = (JSlider) e.getSource();
        setBrushWidth(brushWidth.getValue());
    }

    public int getTool() {
        return tool;
    }

    public void setTool(int tool) {
        this.tool = tool;
    }

    public int getBrushWidth() {
        return brushWidth;
    }

    public void setBrushWidth(int brushWidth) {
        this.brushWidth = brushWidth;
    }

}
