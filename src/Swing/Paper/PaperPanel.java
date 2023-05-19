package Swing.Paper;

import Controller.PaperController;
import Model.Article;
import Model.ConferencePaper;
import Model.Paper;
import Swing.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PaperPanel extends JPanel {

    private String username;
    public PaperPanel() {
        PaperController paperController = new PaperController();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Article> articles = paperController.getAllArticles();
        List<ConferencePaper> conferencePapers = paperController.getAllConferencePapers();

        JPanel listPanel = new JPanel(new BorderLayout());
        JLabel papersLabel = new JLabel("Papers");
        papersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        listPanel.add(papersLabel, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(list);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        add(listPanel, BorderLayout.CENTER);

        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedTitle = model.getElementAt(selectedIndex);
                    Paper selectedPaper = null;
                    if (selectedIndex < articles.size()) {
                        selectedPaper = articles.get(selectedIndex);
                    } else {
                        selectedPaper = conferencePapers.get(selectedIndex - articles.size());
                    }
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PaperPanel.this);
                    frame.getContentPane().removeAll();
                    PaperDetailsPanel paperDetailsPanel = new PaperDetailsPanel(selectedPaper);
                    paperDetailsPanel.setUsername(username);
                    frame.getContentPane().add(paperDetailsPanel);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
        add(viewDetailsButton, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PaperPanel.this);
                frame.getContentPane().removeAll();
                MainPanel mainPanel = new MainPanel();
                mainPanel.setUsername(username);
                frame.getContentPane().add(mainPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(backButton, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.NORTH);

        for (Article article : articles) {
            model.addElement(article.getTitle());
        }
        for (ConferencePaper conferencePaper : conferencePapers) {
            model.addElement(conferencePaper.getTitle());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
