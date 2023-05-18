package Util;

import File.FileCreator;
import File.FileParser;
import Model.Article;
import Model.ConferencePaper;
import Model.ReadingList;
import Model.Researcher;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.List;

public class RunApplication {

    public void runApp() throws ParserConfigurationException, TransformerException {
        //SwingUtilities.invokeLater(MainFrame::new);
        FileParser fileParser = new FileParser();
        List<Article> articleList =  fileParser.parseBibFileArticle("papers");
        List<ConferencePaper> conferencePaperList =  fileParser.parseBibFileConferencePaper("papers");
        FileCreator fileCreator = new FileCreator();
        fileCreator.csvWriter("papers.csv", articleList, conferencePaperList);
        Researcher researcher1 = new Researcher("researcher1", "password1");
        Researcher researcher2 = new Researcher("researcher2", "password2");
        ReadingList readingList1 = researcher1.createReadingList("readingList1", articleList.get(0));
        readingList1.addPaper(articleList.get(1));
        ReadingList readingList2 = researcher1.createReadingList("readingList2", articleList.get(3));
        readingList2.addPaper(articleList.get(4));

        List<Researcher> researcherList = fileCreator.xmlWriter("researchers.xml");
        researcherList.get(0).follow(researcherList.get(1));
        researcherList.get(0).follow(researcherList.get(2));
        researcherList.get(0).unfollow(researcherList.get(1));
        researcherList.get(0).unfollow(researcherList.get(2));
    }
}
