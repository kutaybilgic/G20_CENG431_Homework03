package Controller;

import File.FileParser;
import Model.Article;
import Model.ConferencePaper;

import java.util.List;

public class PaperController {

    FileParser fileParser = new FileParser();

    public List<Article> getAllArticles() {

        return fileParser.readCSVArticle("papers.csv");
    }

    public List<ConferencePaper> getAllConferencePapers() {

        return fileParser.readCSVConferencePaper("papers.csv");
    }

    public boolean isPaperInList(String username, String listName, String paper) {
        return fileParser.jsonIsPaperInList(paper, username, listName);
    }
}
