package Swing.ReadingList;

import Controller.PaperController;
import Controller.ReadingListController;
import Model.*;
import Swing.MainPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class CreateReadingListPanel extends JPanel {

    private Researcher researcher;
    private JTextField readingListNameField;
    private JComboBox<String> paperComboBox;
    private List<ReadingList> readingLists;

    private List<Researcher> researcherList;

    public CreateReadingListPanel(Researcher researcher, List<ReadingList> readingLists, List<Researcher> researcherList) {
        PaperController paperController = new PaperController();
        ReadingListController readingListController = new ReadingListController();
        this.researcher = researcher;
        this.readingLists = readingLists;
        this.researcherList = researcherList;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Create Reading List");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));

        JLabel nameLabel = new JLabel("Reading List Name:");
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(nameLabel);

        readingListNameField = new JTextField();
        readingListNameField.setMaximumSize(new Dimension(200, 25));
        readingListNameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(readingListNameField);

        JLabel paperLabel = new JLabel("Select Paper:");
        paperLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(paperLabel);

        List<Article> articles = paperController.getAllArticles();
        List<ConferencePaper> conferencePapers = paperController.getAllConferencePapers();

        DefaultComboBoxModel<String> paperComboBoxModel = new DefaultComboBoxModel<>();
        for (Article article : articles) {
            paperComboBoxModel.addElement(article.getTitle());
        }
        for (ConferencePaper conferencePaper : conferencePapers) {
            paperComboBoxModel.addElement(conferencePaper.getTitle());
        }
        paperComboBox = new JComboBox<>(paperComboBoxModel);
        paperComboBox.setMaximumSize(new Dimension(800, 25));
        paperComboBox.setPreferredSize(new Dimension(200, paperComboBox.getPreferredSize().height));
        paperComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(paperComboBox);

        add(contentPanel, BorderLayout.CENTER);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String readingListName = readingListNameField.getText();

                int selectedIndex = paperComboBox.getSelectedIndex();
                if (selectedIndex != -1 && !readingListName.isEmpty()) {
                    String selectedPaperTitle = paperComboBox.getItemAt(selectedIndex);

                    boolean isExist = readingListController.isListExist(researcher.getResearcher_name(), readingListName);

                    if (isExist) {
                        JOptionPane.showMessageDialog(CreateReadingListPanel.this,
                                "Reading list already exists",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    for (Article article : articles) {
                        if (article.getTitle().equals(selectedPaperTitle)) {
                            try {
                                researcher.createReadingList(readingListName, article);

                                JOptionPane.showMessageDialog(CreateReadingListPanel.this,
                                        "Reading list created successfully",
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;

                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(CreateReadingListPanel.this,
                                        "Error creating reading list",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                ex.printStackTrace();
                            }
                        }
                    }
                    for (ConferencePaper conferencePaper : conferencePapers) {
                        if (conferencePaper.getTitle().equals(selectedPaperTitle)) {
                            try {
                                researcher.createReadingList(readingListName, conferencePaper);

                                JOptionPane.showMessageDialog(CreateReadingListPanel.this,
                                        "Reading list created successfully",
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;

                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(CreateReadingListPanel.this,
                                        "Error creating reading list",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(CreateReadingListPanel.this);
                frame.getContentPane().removeAll();
                MainPanel mainPanel = null;
                try {
                    ReadingListController readingListController = new ReadingListController();
                    List<ReadingList> readingListsNew = readingListController.getAllReadingListsByUsername(researcher.getResearcher_name());
                    mainPanel = new MainPanel(researcher, readingListsNew, researcherList);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                mainPanel.setUsername(researcher.getResearcher_name());
                frame.getContentPane().add(mainPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());

        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftButtonPanel.add(backButton);

        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.add(createButton);

        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }
}
