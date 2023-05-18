package Swing;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("OpenResearch");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new LoginPanel());
        setVisible(true);
    }
}
