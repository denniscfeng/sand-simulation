import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SandSimulatorGUI implements ActionListener {

    JFrame frame;
    SandDisplayPanel sandDisplayPanel;
    JButton[] buttons;

    public SandSimulatorGUI(SandDisplayPanel sandDisplayPanel) {

        this.frame = new JFrame("Sand Simulator");
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.LINE_AXIS));
        frame.getContentPane().add(main);

        this.sandDisplayPanel = sandDisplayPanel;
        main.add(sandDisplayPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        main.add(buttonPanel);


        List<String> buttonNames = new ArrayList<>();
        buttonNames.add("Clear");
        buttonNames.add("Sand");
        buttons = new JButton[buttonNames.size()];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonNames.get(i));
            buttons[i].setActionCommand("" + i);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        buttons[1].setSelected(true);
        sandDisplayPanel.setTool(1);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);

    }

    // Button handler
    @Override
    public void actionPerformed(ActionEvent e) {
        int tool = Integer.parseInt(e.getActionCommand());
        sandDisplayPanel.setTool(tool);

        for (JButton b: buttons)
            b.setSelected(false);

        if (tool == 1) // Keep Sand selected
            ((JButton) e.getSource()).setSelected(true);

    }
}
