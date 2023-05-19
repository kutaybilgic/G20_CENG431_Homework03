package Swing;

import Controller.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private LoginPanel loginPanel;
    private MainPanel mainPanel;
    private String username;

    public MainFrame() {
        setTitle("OpenResearch");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginPanel = new LoginPanel();
        mainPanel = new MainPanel();

        JLabel openResearchLabel = new JLabel("OpenResearch");
        openResearchLabel.setHorizontalAlignment(SwingConstants.LEFT);
        openResearchLabel.setVerticalAlignment(SwingConstants.TOP);
        openResearchLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(openResearchLabel, BorderLayout.NORTH);

        setContentPane(loginPanel);

        loginPanel.addLoginButtonAction(e -> {
            String username = loginPanel.getUsernameField().getText();
            String password = new String(loginPanel.getPasswordField().getPassword());

            LoginController loginController = new LoginController();
            boolean loggedIn = loginController.login(username, password);

            if (loggedIn) {
                setUsername(username);
                setContentPane(mainPanel);
            } else {
                JOptionPane.showMessageDialog(MainFrame.this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }

            revalidate();
            repaint();
        });

        setVisible(true);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        mainPanel.setUsername(username);
    }
}
