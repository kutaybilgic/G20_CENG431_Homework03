package Util;


import Swing.MainFrame;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

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
