package Controller;

import File.FileParser;
import Model.ReadingList;
import java.io.IOException;
import java.util.List;

public class ReadingListController {

    public List<ReadingList> getAllReadingListsByUsername(String username) throws IOException {
        FileParser fileParser = new FileParser();

        return fileParser.jsonReadingListParserForUser(username);
    }

    public boolean isListExist(String username, String listName) {
        FileParser fileParser = new FileParser();

        return fileParser.jsonIsListExistForUser(username, listName);
    }
}
