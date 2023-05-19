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

        return fileParser.parseBibFileArticle("papers");
    }

    public List<ConferencePaper> getAllConferencePapers() {

        return fileParser.parseBibFileConferencePaper("papers");
    }
}
