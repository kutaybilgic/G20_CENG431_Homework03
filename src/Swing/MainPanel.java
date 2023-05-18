package Swing;

import javax.swing.*;

public class MainPanel extends JPanel {

    private JLabel welcomeLabel;

    public MainPanel(String username) {
        welcomeLabel = new JLabel("Welcome, " + username + "!");
        add(welcomeLabel);
    }


}
