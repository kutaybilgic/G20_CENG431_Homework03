package Controller;

import File.FileParser;

public class LoginController {

    public boolean login(String username, String password) {
        FileParser fileParser = new FileParser();
        String xmlName = "researchers.xml";
        return fileParser.xmlResearcherReader(xmlName, username, password);

    }
}
