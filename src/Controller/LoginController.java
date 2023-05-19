package Controller;

import File.FileParser;
import Model.Researcher;

public class LoginController {

    public boolean login(String username, String password) {
        FileParser fileParser = new FileParser();
        String xmlName = "researchers.xml";
        return fileParser.xmlResearcherReader(xmlName, username, password);

    }
}
