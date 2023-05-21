package Swing.Researcher;

import Model.ReadingList;
import Model.Researcher;
import Swing.MainPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ResearchersPanel extends JPanel {

    private Researcher researcher;
    private List<ReadingList> readingList;
    private List<Researcher> researcherList;

    private String username;

    public ResearchersPanel(Researcher researcher, List<ReadingList> readingList, List<Researcher> researcherList) {
        this.researcher = researcher;
        this.readingList = readingList;
        this.researcherList = researcherList;

        setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Researcher research : researcherList) {
            listModel.addElement(research.getResearcher_name());
        }
        JList<String> researcherNameList = new JList<>(listModel);
        researcherNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        Font font = new Font("Arial", Font.BOLD, 14);
        researcherNameList.setFont(font);
        researcherNameList.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(researcherNameList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton viewProfileButton = new JButton("View Profile");
        JButton backButton = new JButton("Back");

        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftButtonPanel.add(backButton);

        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.add(viewProfileButton);

        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);

        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = researcherNameList.getSelectedIndex();
                if (selectedIndex != -1) {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ResearchersPanel.this);
                    frame.getContentPane().removeAll();
                    Researcher selectedResearcher = researcherList.get(selectedIndex);

                    ViewProfilePanel profilePanel = null;
                    try {
                        profilePanel = new ViewProfilePanel(researcher, selectedResearcher, readingList, researcherList);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    profilePanel.setUsername(username);
                    frame.getContentPane().add(profilePanel);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ResearchersPanel.this);
                frame.getContentPane().removeAll();
                MainPanel mainPanel = null;
                try {
                    mainPanel = new MainPanel(researcher, readingList, researcherList);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                mainPanel.setUsername(username);
                frame.getContentPane().add(mainPanel);
                frame.revalidate();
                frame.repaint();
            }
        });


        JLabel titleLabel = new JLabel("Researcher List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scrollPane.setColumnHeaderView(titleLabel);
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    public List<ReadingList> getReadingList() {
        return readingList;
    }

    public void setReadingList(List<ReadingList> readingList) {
        this.readingList = readingList;
    }

    public List<Researcher> getResearcherList() {
        return researcherList;
    }

    public void setResearcherList(List<Researcher> researcherList) {
        this.researcherList = researcherList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
