package Swing.Paper;

import Model.Paper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaperDetailsPanel extends JPanel {

    private String username;
    private Paper paper;

    public PaperDetailsPanel(Paper paper) {
        this.paper = paper;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel contentPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Title: " + paper.getTitle());
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f)); // Başlık fontunu kalın hale getirme
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new BorderLayout());

        JLabel authorLabel = new JLabel("Author: " + paper.getAuthors());
        infoPanel.add(authorLabel, BorderLayout.NORTH);

        JPanel detailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel yearLabel = new JLabel("Year: " + paper.getYear());
        yearLabel.setHorizontalAlignment(SwingConstants.LEFT); // Yıl yazısını sola hizalama
        detailPanel.add(yearLabel);

        JLabel doiLabel = new JLabel("Doi: " + paper.getDoi());
        doiLabel.setHorizontalAlignment(SwingConstants.LEFT); // Doi yazısını sola hizalama
        detailPanel.add(doiLabel);

        JLabel downloadsLabel = new JLabel("Number of Downloads: " + paper.getNumber_of_downloads());
        downloadsLabel.setHorizontalAlignment(SwingConstants.LEFT); // İndirme sayısı yazısını sola hizalama
        detailPanel.add(downloadsLabel);

        infoPanel.add(detailPanel, BorderLayout.WEST); // Detail panelini info panelinin batı (west) bölgesine yerleştirme

        contentPanel.add(infoPanel, BorderLayout.CENTER);

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PaperDetailsPanel.this);
                frame.getContentPane().removeAll();
                PaperPanel paperPanel = new PaperPanel();
                paperPanel.setUsername(username);
                frame.getContentPane().add(paperPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
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
}
