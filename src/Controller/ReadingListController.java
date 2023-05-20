package Controller;

import File.FileParser;
import Model.ReadingList;
import Model.Researcher;

import java.util.List;

public class ReadingListController {

    public List<ReadingList> getAllReadingListsByUsername(List<Researcher> researcherList, String username) {
        FileParser fileParser = new FileParser();

        return fileParser.jsonReadingListParserForUser(researcherList, username);
    }
}
