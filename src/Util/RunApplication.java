package Util;

import File.FileCreator;
import File.FileParser;
import Model.Article;
import Model.ConferencePaper;
import Model.ReadingList;
import Model.Researcher;
import Swing.MainFrame;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public class RunApplication {

    public void runApp()  {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainFrame();
                } catch (IOException | ParserConfigurationException | TransformerException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
