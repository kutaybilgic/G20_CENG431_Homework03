package Swing;

import javax.swing.*;

public class MainPanel extends JPanel {

    private JLabel welcomeLabel;

    private String username;


    public JLabel getWelcomeLabel() {
        return welcomeLabel;
    }

    public void setWelcomeLabel(JLabel welcomeLabel) {
        this.welcomeLabel = welcomeLabel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        welcomeLabel = new JLabel("Welcome, " + username + "!");
        add(welcomeLabel);
    }
}
