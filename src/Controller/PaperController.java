package Controller;

import File.FileParser;
import Model.Article;
import Model.ConferencePaper;
import Model.Paper;
import Model.ReadingList;

import java.io.IOException;
import java.util.List;

public class PaperController {

    FileParser fileParser = new FileParser();

    public List<Article> getAllArticles() {

        return fileParser.readCSVArticle("papers.csv", "papers", false);
    }

    public List<ConferencePaper> getAllConferencePapers() {

        return fileParser.readCSVConferencePaper("papers.csv", "papers", false);
    }

    public List<Paper> getAllPapersByReadingListName(String listName) throws IOException {
        FileParser fileParser = new FileParser();

        return fileParser.jsonReadingListParserByNameArticle(listName);
    }


    public boolean isPaperInList(String username, String listName, String paper) {
        return fileParser.jsonIsPaperInList(paper, username, listName);
    }
}
