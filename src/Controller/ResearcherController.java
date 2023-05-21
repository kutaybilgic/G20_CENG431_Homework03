package Controller;

import File.FileCreator;
import File.FileParser;
import Model.Article;
import Model.ConferencePaper;
import Model.Researcher;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public class ResearcherController {

    FileParser fileParser = new FileParser();
    public List<Researcher> createResearchers() throws IOException, ParserConfigurationException, TransformerException {

        List<Article> articleList =  fileParser.parseBibFileArticle("papers");
        List<ConferencePaper> conferencePaperList =  fileParser.parseBibFileConferencePaper("papers");
        FileCreator fileCreator = new FileCreator();
        fileCreator.csvWriter("papers.csv", articleList, conferencePaperList);

        return fileCreator.xmlWriter("researchers.xml");
    }

    public boolean followUnfollowResearcher(String followName, String followerName, boolean isForFollow) throws ParserConfigurationException, IOException, SAXException {
        return fileParser.isUserInFollowList(followName, followerName, isForFollow);
    }


}
