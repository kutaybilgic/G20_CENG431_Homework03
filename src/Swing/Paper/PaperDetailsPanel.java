package Swing.Paper;

import Controller.PaperController;
import Model.Paper;
import Model.ReadingList;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class PaperDetailsPanel extends JPanel {
    private String username;
    private Paper paper;
    private List<ReadingList> readingLists;
    private JList<String> readingListJList;

    public PaperDetailsPanel(Paper paper, List<ReadingList> readingLists) {
        PaperController paperController = new PaperController();
        this.paper = paper;
        this.readingLists = readingLists;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));

        JLabel titleLabel = new JLabel("Title: " + paper.getTitle());
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);

        contentPanel.add(Box.createVerticalStrut(10));

        JLabel authorLabel = new JLabel("Author: " + paper.getAuthors());
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(authorLabel);

        contentPanel.add(Box.createVerticalStrut(10));

        JLabel yearLabel = new JLabel("Year: " + paper.getYear());
        yearLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(yearLabel);

        contentPanel.add(Box.createVerticalStrut(10));

        JLabel doiLabel = new JLabel("Doi: " + paper.getDoi());
        doiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(doiLabel);

        contentPanel.add(Box.createVerticalStrut(10));

        JLabel downloadsLabel = new JLabel("Number of Downloads: " + paper.getNumber_of_downloads());
        downloadsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(downloadsLabel);

        add(contentPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addToReadingListButton = new JButton("Add To Reading List");
        addToReadingListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = readingListJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    ReadingList selectedList = readingLists.get(readingListJList.getSelectedIndex());
                    boolean added = paperController.isPaperInList(username, selectedList.getReadinglist_name(), paper);
                    if (!added) {
                        try {
                            selectedList.addPaper(paper);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        JOptionPane.showMessageDialog(PaperDetailsPanel.this,
                                "Paper is added to your list",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(PaperDetailsPanel.this,
                                "Paper is already in your list",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonPanel.add(addToReadingListButton);

        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paper.downloadPaper();
                int option = JOptionPane.showOptionDialog(PaperDetailsPanel.this,
                        "Download completed",
                        "Success", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
                refreshPaperDetailsPanel();

            }
        });
        buttonPanel.add(downloadButton);

        add(buttonPanel, BorderLayout.CENTER);

        JPanel readingListPanel = new JPanel(new BorderLayout());
        readingListPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel readingListLabel = new JLabel("Reading List:");
        readingListLabel.setFont(readingListLabel.getFont().deriveFont(Font.BOLD));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (ReadingList readingList : readingLists) {
            listModel.addElement(readingList.getReadinglist_name());
        }

        readingListJList = new JList<>(listModel);
        JScrollPane readingListScrollPane = new JScrollPane(readingListJList);

        readingListPanel.add(readingListLabel, BorderLayout.NORTH);
        readingListPanel.add(readingListScrollPane, BorderLayout.CENTER);

        add(readingListPanel, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PaperDetailsPanel.this);
                frame.getContentPane().removeAll();
                PaperPanel paperPanel = null;
                try {
                    paperPanel = new PaperPanel(readingLists);
                } catch (IOException | TransformerException | ParserConfigurationException ex) {
                    throw new RuntimeException(ex);
                }
                paperPanel.setUsername(username);
                frame.getContentPane().add(paperPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(backButton);

        add(backButtonPanel, BorderLayout.WEST);
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
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

    private void refreshPaperDetailsPanel() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PaperDetailsPanel.this);
        frame.getContentPane().removeAll();
        PaperDetailsPanel paperDetailsPanel = new PaperDetailsPanel(paper, readingLists);
        paperDetailsPanel.setUsername(username);
        frame.getContentPane().add(paperDetailsPanel);
        frame.revalidate();
        frame.repaint();
    }
}
