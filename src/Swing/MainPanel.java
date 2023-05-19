package Swing;

import Swing.Paper.PaperPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {

    private JLabel welcomeLabel;
    private String username;

    public MainPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        welcomeLabel = new JLabel();
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);

        JButton showAllPapersButton = createButton("Papers");
        showAllPapersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaperPanel paperPanel = new PaperPanel();
                paperPanel.setUsername(username);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(MainPanel.this);
                frame.getContentPane().removeAll();
                frame.getContentPane().add(paperPanel);
                frame.revalidate();
                frame.repaint();
            }
        });
        buttonPanel.add(showAllPapersButton, gbc);

        JButton createReadingListButton = createButton("Create Reading List");
        createReadingListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reading list oluşturma işlemi
            }
        });
        buttonPanel.add(createReadingListButton, gbc);

        JButton viewReadingListsButton = createButton("My Reading Lists");
        viewReadingListsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kullanıcının kendi reading listelerini görüntüleme işlemi
            }
        });
        buttonPanel.add(viewReadingListsButton, gbc);

        JButton viewOtherResearchButton = createButton("Researchers");
        viewOtherResearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Diğer research'leri görüntüleme işlemi
            }
        });
        buttonPanel.add(viewOtherResearchButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);
    }

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
        welcomeLabel.setText("Welcome, " + username + "!");
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 60));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        return button;
    }

}
