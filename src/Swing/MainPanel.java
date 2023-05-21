package Swing;


import Model.ReadingList;
import Model.Researcher;
import Swing.Paper.PaperPanel;
import Swing.ReadingList.CreateReadingListPanel;
import Swing.ReadingList.MyReadingListPanel;
import Swing.Researcher.ResearchersPanel;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class MainPanel extends JPanel {

    private JLabel welcomeLabel;
    private String username;
    private List<ReadingList> readingLists;

    private Researcher researcher;
    private List<Researcher> researcherList;

    public MainPanel(Researcher researcher, List<ReadingList> readingList, List<Researcher> researcherList) throws IOException {
        this.researcher = researcher;
        this.readingLists = readingList;
        this.researcherList = researcherList;
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
                PaperPanel paperPanel = null;
                try {
                    paperPanel = new PaperPanel(researcher, readingLists, researcherList);
                } catch (IOException | ParserConfigurationException | TransformerException ex) {
                    throw new RuntimeException(ex);
                }
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
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(MainPanel.this);
                frame.getContentPane().removeAll();
                CreateReadingListPanel createReadingListPanel = new CreateReadingListPanel(researcher, readingLists, researcherList);
                frame.getContentPane().add(createReadingListPanel);
                frame.revalidate();
                frame.repaint();
            }
        });
        buttonPanel.add(createReadingListButton, gbc);

        JButton viewReadingListsButton = createButton("My Reading Lists");
        viewReadingListsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(MainPanel.this);
                frame.getContentPane().removeAll();
                MyReadingListPanel myReadingListPanel = new MyReadingListPanel(researcher,username, readingLists, researcherList);
                myReadingListPanel.setUsername(username);
                frame.getContentPane().add(myReadingListPanel);
                frame.revalidate();
                frame.repaint();
            }
        });
        buttonPanel.add(viewReadingListsButton, gbc);

        JButton viewOtherResearchButton = createButton("Researchers");
        viewOtherResearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(MainPanel.this);
                frame.getContentPane().removeAll();
                ResearchersPanel researchersPanel = new ResearchersPanel(researcher, readingList, researcherList);
                researchersPanel.setUsername(username);
                frame.getContentPane().add(researchersPanel);
                frame.revalidate();
                frame.repaint();
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

    public List<ReadingList> getReadingLists() {
        return readingLists;
    }

    public void setReadingLists(List<ReadingList> readingLists) {
        this.readingLists = readingLists;
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    public List<Researcher> getResearcherList() {
        return researcherList;
    }

    public void setResearcherList(List<Researcher> researcherList) {
        this.researcherList = researcherList;
    }
}
