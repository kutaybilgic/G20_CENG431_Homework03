package Swing.ReadingList;

import Controller.PaperController;
import Model.*;
import Swing.Paper.PaperDetailsPanel;
import Swing.Researcher.ViewProfilePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ViewReadingListPanel extends JPanel {

    private Researcher researcher;

    private Researcher selectedResearcher;
    private List<ReadingList> readingList;
    private List<Researcher> researcherList;

    private String username;

    private ReadingList selectedReadingList;

    public ViewReadingListPanel(Researcher researcher, Researcher selectedResearcher, List<ReadingList> readingList, List<Researcher> researcherList, ReadingList selectedReadingList) throws IOException {
        this.researcher = researcher;
        this.selectedResearcher = selectedResearcher;
        this.readingList = readingList;
        this.researcherList = researcherList;
        this.selectedReadingList = selectedReadingList;

        initializeUI();
    }

    private void initializeUI() throws IOException {
        setLayout(new BorderLayout());

        PaperController paperController = new PaperController();
        List<Paper> paperListUser = paperController.getAllPapersByReadingListName(selectedReadingList.getReadinglist_name());

        DefaultListModel<String> paperListModel = new DefaultListModel<>();
        for (Paper paper : paperListUser) {
            paperListModel.addElement(paper.getTitle());
        }
        JList<String> paperList = new JList<>(paperListModel);
        JScrollPane scrollPane = new JScrollPane(paperList);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ViewReadingListPanel.this);
                frame.getContentPane().removeAll();
                ViewProfilePanel viewProfilePanel = null;
                try {
                    viewProfilePanel = new ViewProfilePanel(researcher, selectedResearcher, readingList, researcherList);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                viewProfilePanel.setUsername(username);
                frame.getContentPane().add(viewProfilePanel);
                frame.revalidate();
                frame.repaint();
            }
        });


        JButton viewPaperButton = new JButton("View Paper");
        viewPaperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedPaperIndex = paperList.getSelectedIndex();
                if (selectedPaperIndex != -1) {
                Paper selectedPaper = paperListUser.get(selectedPaperIndex);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ViewReadingListPanel.this);
                frame.getContentPane().removeAll();
                PaperDetailsPanel paperDetailsPanel = new PaperDetailsPanel(researcher, selectedPaper, readingList, researcherList, false);
                paperDetailsPanel.setUsername(username);
                frame.getContentPane().add(paperDetailsPanel);
                frame.revalidate();
                frame.repaint();
                }
            }
        });


        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(viewPaperButton, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.PAGE_END);
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    public Researcher getSelectedResearcher() {
        return selectedResearcher;
    }

    public void setSelectedResearcher(Researcher selectedResearcher) {
        this.selectedResearcher = selectedResearcher;
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

    public ReadingList getSelectedReadingList() {
        return selectedReadingList;
    }

    public void setSelectedReadingList(ReadingList selectedReadingList) {
        this.selectedReadingList = selectedReadingList;
    }
}
