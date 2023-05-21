package Swing.Researcher;

import Controller.ReadingListController;
import Model.ReadingList;
import Model.Researcher;
import Swing.MainPanel;
import Swing.ReadingList.ViewReadingListPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ViewProfilePanel extends JPanel {

    private Researcher researcher;

    private Researcher selectedResearcher;
    private List<ReadingList> readingList;
    private List<Researcher> researcherList;

    private String username;

    public ViewProfilePanel(Researcher researcher, Researcher selectedResearcher, List<ReadingList> readingList, List<Researcher> researcherList) throws IOException {
        this.researcher = researcher;
        this.readingList = readingList;
        this.researcherList = researcherList;
        this.selectedResearcher = selectedResearcher;
        ReadingListController readingListController = new ReadingListController();
        List<ReadingList> readingListResearcher = readingListController.getAllReadingListsByUsername(selectedResearcher.getResearcher_name());

        setLayout(new BorderLayout());

        // Researcher Name'i göstermek için JLabel oluşturun ve özelliklerini ayarlayın
        JLabel nameLabel = new JLabel("Researcher Name: " + selectedResearcher.getResearcher_name());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel followingLabel = new JLabel("Following Researchers: " + String.join(", ", selectedResearcher.getFollowing_researcher_names()));
        followingLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        followingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel followerLabel = new JLabel("Follower Researchers: " + String.join(", ", selectedResearcher.getFollower_researcher_names()));
        followerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        followerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(Box.createVerticalStrut(10)); // Yukarıda 10 piksel boşluk bırakır
        contentPanel.add(nameLabel);
        contentPanel.add(followingLabel);
        contentPanel.add(followerLabel);
        contentPanel.add(Box.createVerticalStrut(10)); // Aşağıda 10 piksel boşluk bırakır

        add(contentPanel, BorderLayout.CENTER);

        // Reading Listelerin görüntülendiği kutuyu oluşturun
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (ReadingList rList : readingListResearcher) {
            listModel.addElement(rList.getReadinglist_name());
        }

        JList<String> readingListJList = new JList<>(listModel);
        readingListJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane readingListScrollPane = new JScrollPane(readingListJList);

        JPanel readingListPanel = new JPanel();
        readingListPanel.setLayout(new BorderLayout());
        readingListPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        readingListPanel.add(readingListScrollPane, BorderLayout.CENTER);

        JButton viewReadingListButton = new JButton("View Reading List");
        viewReadingListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = readingListJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    ReadingList selectedReadingList = readingListResearcher.get(selectedIndex);
                    ViewReadingListPanel viewReadingListPanel = null;
                    try {
                        viewReadingListPanel = new ViewReadingListPanel(researcher, selectedResearcher, readingList, researcherList, selectedReadingList);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    viewReadingListPanel.setUsername(username);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ViewProfilePanel.this);
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(viewReadingListPanel);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });



        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ViewProfilePanel.this);
                frame.getContentPane().removeAll();
                ResearchersPanel researchersPanel = new ResearchersPanel(researcher, readingList, researcherList);
                researchersPanel.setUsername(username);
                frame.getContentPane().add(researchersPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(viewReadingListButton);
        buttonPanel.add(backButton);

        JPanel panelWrapper = new JPanel(new BorderLayout());
        panelWrapper.add(readingListPanel, BorderLayout.CENTER);
        panelWrapper.add(buttonPanel, BorderLayout.SOUTH);

        add(panelWrapper, BorderLayout.SOUTH);
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

    public Researcher getSelectedResearcher() {
        return selectedResearcher;
    }

    public void setSelectedResearcher(Researcher selectedResearcher) {
        this.selectedResearcher = selectedResearcher;
    }
}
