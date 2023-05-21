package Swing.ReadingList;

import Controller.PaperController;
import Model.Paper;
import Model.ReadingList;
import Model.Researcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class PaperListPanel extends JPanel {

    private List<String> papers;
    private String username;
    private List<ReadingList> readingLists;

    private ReadingList selectedList;

    private Researcher researcher;

    private List<Researcher> researcherList;

    public PaperListPanel(Researcher researcher,String username, List<ReadingList> readingLists, ReadingList selectedList,List<String> papers, List<Researcher> researcherList) {
        PaperController paperController = new PaperController();
        this.username = username;
        this.readingLists = readingLists;
        this.papers = papers;
        this.selectedList = selectedList;
        this.researcher = researcher;
        this.researcherList = researcherList;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String paper : papers) {
            listModel.addElement(paper);
        }

        JList<String> paperJList = new JList<>(listModel);
        JScrollPane paperScrollPane = new JScrollPane(paperJList);

        add(paperScrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        JButton removeButton = new JButton("Remove Paper");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PaperListPanel.this);
                frame.getContentPane().removeAll();
                MyReadingListPanel myReadingListPanel = new MyReadingListPanel(researcher,username, readingLists, researcherList);
                frame.getContentPane().add(myReadingListPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = paperJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedPaper = papers.get(selectedIndex);
                    boolean isInList = paperController.isPaperInList(username, selectedList.getReadinglist_name(), selectedPaper);
                    if (isInList) {
                        try {
                            selectedList.removePaper(selectedPaper);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    listModel.remove(selectedIndex);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(removeButton, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public List<String> getPapers() {
        return papers;
    }

    public void setPapers(List<String> papers) {
        this.papers = papers;
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

    public ReadingList getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(ReadingList selectedList) {
        this.selectedList = selectedList;
    }
}
