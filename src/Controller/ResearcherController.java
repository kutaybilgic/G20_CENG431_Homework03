package Controller;

import File.FileCreator;
import File.FileParser;
import Model.Article;
import Model.ConferencePaper;
import Model.ReadingList;
import Model.Researcher;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
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

        ReadingList readingList1 = researcherList.get(5).createReadingList("readingList1", articleList.get(0));
        readingList1.addPaper(articleList.get(1));
        readingList1.addPaper(articleList.get(6));
        ReadingList readingList2 = researcherList.get(1).createReadingList("readingList2", articleList.get(3));
        readingList2.addPaper(articleList.get(4));
        researcherList.get(2).createReadingList("readingList3", articleList.get(2));
        researcherList.get(2).getReadingLists().get(0).removePaper(articleList.get(2));
        researcherList.get(0).follow(researcherList.get(1));
        researcherList.get(0).follow(researcherList.get(2));
        researcherList.get(0).follow(researcherList.get(3));
        researcherList.get(0).follow(researcherList.get(4));
        researcherList.get(1).follow(researcherList.get(1));

        return researcherList;
    }
}
