package Swing;

import Controller.LoginController;
import Controller.ReadingListController;
import Controller.ResearcherController;
import Model.ReadingList;
import Model.Researcher;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class MainFrame extends JFrame {

    private LoginPanel loginPanel;
    private MainPanel mainPanel;
    private String username;

    private List<ReadingList> readingLists;

    public MainFrame() throws IOException, ParserConfigurationException, TransformerException {

        ResearcherController researcherController = new ResearcherController();
        List<Researcher> researcherList = researcherController.createResearchers();
        ReadingListController readingListController = new ReadingListController();
        setTitle("OpenResearch");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPanel = new LoginPanel();


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
                this.readingLists = readingListController.getAllReadingListsByUsername(researcherList, username);
                mainPanel = new MainPanel(readingLists);
                mainPanel.setUsername(username);
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
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public void setLoginPanel(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public List<ReadingList> getReadingLists() {
        return readingLists;
    }

    public void setReadingLists(List<ReadingList> readingLists) {
        this.readingLists = readingLists;
    }
}
