package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(59, 89, 182));
        loginButton.setForeground(Color.WHITE);

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("Username:"), constraints);

        constraints.gridy = 1;
        add(usernameField, constraints);

        constraints.gridy = 2;
        add(new JLabel("Password:"), constraints);

        constraints.gridy = 3;
        add(passwordField, constraints);

        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20, 10, 10, 10);
        add(loginButton, constraints);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }

    public void addLoginButtonAction(ActionListener action) {
        loginButton.addActionListener(action);
    }
}
