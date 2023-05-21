package Controller;

import File.FileCreator;
import File.FileParser;
import Model.Article;
import Model.ConferencePaper;
import Model.Researcher;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public class ResearcherController {

    public List<Researcher> createResearchers() throws IOException, ParserConfigurationException, TransformerException {
        FileParser fileParser = new FileParser();
        List<Article> articleList =  fileParser.parseBibFileArticle("papers");
        List<ConferencePaper> conferencePaperList =  fileParser.parseBibFileConferencePaper("papers");
        FileCreator fileCreator = new FileCreator();
        fileCreator.csvWriter("papers.csv", articleList, conferencePaperList);
        List<Researcher> researcherList = fileCreator.xmlWriter("researchers.xml");


        researcherList.get(0).follow(researcherList.get(1));
        researcherList.get(0).follow(researcherList.get(2));
        researcherList.get(0).follow(researcherList.get(3));
        researcherList.get(0).follow(researcherList.get(4));

        return researcherList;
    }
}
