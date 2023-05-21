package Swing.ReadingList;

import Model.ReadingList;
import Model.Researcher;
import Swing.MainPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class MyReadingListPanel extends JPanel {
    private String username;
    private List<ReadingList> readingLists;

    private Researcher researcher;

    private List<Researcher> researcherList;

    public MyReadingListPanel(Researcher researcher, String username, List<ReadingList> readingLists,List<Researcher> researcherList) {
        this.username = username;
        this.readingLists = readingLists;
        this.researcher = researcher;
        this.researcherList = researcherList;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("My Reading Lists");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (ReadingList readingList : readingLists) {
            listModel.addElement(readingList.getReadinglist_name());
        }

        JList<String> readingListJList = new JList<>(listModel);
        JScrollPane readingListScrollPane = new JScrollPane(readingListJList);

        add(readingListScrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(MyReadingListPanel.this);
                frame.getContentPane().removeAll();
                MainPanel mainPanel = null;
                try {
                    mainPanel = new MainPanel(researcher, readingLists, researcherList);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                mainPanel.setUsername(username);
                frame.getContentPane().add(mainPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        JButton viewListButton = new JButton("View List");
        viewListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = readingListJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    ReadingList selectedList = readingLists.get(selectedIndex);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(MyReadingListPanel.this);
                    frame.getContentPane().removeAll();
                    PaperListPanel paperListPanel = new PaperListPanel(researcher,username, readingLists, selectedList,selectedList.getPapers(), researcherList);
                    frame.getContentPane().add(paperListPanel);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());

        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftButtonPanel.add(backButton);

        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.add(viewListButton);

        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ReadingList> getReadingLists() {
        return readingLists;
    }

    public void setReadingLists(List<ReadingList> readingLists) {
        this.readingLists = readingLists;
    }
}
