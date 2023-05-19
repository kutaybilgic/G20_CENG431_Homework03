import Util.RunApplication;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {

        RunApplication runApplication = new RunApplication();
        runApplication.runApp();
    }
}