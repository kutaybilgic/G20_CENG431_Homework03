package Controller;

import File.FileParser;
import Model.Article;
import Model.ConferencePaper;
import Model.Paper;
import Model.Researcher;

import java.util.List;

public class PaperController {

    FileParser fileParser = new FileParser();

    public List<Article> getAllArticles() {

        return fileParser.readCSVArticle("papers.csv");
    }

    public List<ConferencePaper> getAllConferencePapers() {

        return fileParser.readCSVConferencePaper("papers.csv");
    }

    public boolean isPaperInList(String username, String listName, Paper paper) {
        return fileParser.jsonIsPaperInList(paper, username, listName);
    }
}
