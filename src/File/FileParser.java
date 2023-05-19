package File;

import Model.Article;
import Model.ConferencePaper;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXParser;
import org.jbibtex.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileParser {

    public List<Article> parseBibFileArticle(String bibFilePath){
        List<Article> papers = new ArrayList<>();
        File folder = new File(bibFilePath);

        if (folder.isDirectory()) {
            File[] bibFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".bib"));

            for (File bibFile : bibFiles) {
                try {
                    FileReader reader = new FileReader(bibFile);
                    BibTeXParser parser = new BibTeXParser();
                    BibTeXDatabase database = parser.parse(reader);

                    for (BibTeXEntry entry : database.getEntries().values()) {
                        if (bibFile.getName().startsWith("A")) {
                            String author = entry.getField(BibTeXEntry.KEY_AUTHOR) != null ? entry.getField(BibTeXEntry.KEY_AUTHOR).toUserString() : "";
                            String title = entry.getField(BibTeXEntry.KEY_TITLE) != null ? entry.getField(BibTeXEntry.KEY_TITLE).toUserString() : "";
                            int year = Integer.parseInt(entry.getField(BibTeXEntry.KEY_YEAR) != null ? entry.getField(BibTeXEntry.KEY_YEAR).toUserString() : "1970") ;
                            int volume = Integer.parseInt(entry.getField(BibTeXEntry.KEY_VOLUME) != null ? entry.getField(BibTeXEntry.KEY_VOLUME).toUserString() : "0") ;
                            String number = entry.getField(BibTeXEntry.KEY_NUMBER) != null ? entry.getField(BibTeXEntry.KEY_NUMBER).toUserString() : "" ;
                            String doi = entry.getField(BibTeXEntry.KEY_DOI) != null ? entry.getField(BibTeXEntry.KEY_DOI).toUserString() : "";
                            String journal = entry.getField(BibTeXEntry.KEY_JOURNAL) != null ? entry.getField(BibTeXEntry.KEY_JOURNAL).toUserString() : "";
                            Article article = new Article(author, title, year, doi, volume, number, journal);
                            papers.add(article);
                        }
                    }
                    reader.close();
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Invalid folder path: " + bibFilePath);
        }

        return papers;
    }

    public List<ConferencePaper> parseBibFileConferencePaper(String bibFilePath){
        List<ConferencePaper> papers = new ArrayList<>();
        File folder = new File(bibFilePath);

        if (folder.isDirectory()) {
            File[] bibFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".bib"));

            for (File bibFile : bibFiles) {
                try {
                    FileReader reader = new FileReader(bibFile);
                    BibTeXParser parser = new BibTeXParser();
                    BibTeXDatabase database = parser.parse(reader);

                    for (BibTeXEntry entry : database.getEntries().values()) {
                        if (bibFile.getName().startsWith("I")) {
                            String author = entry.getField(BibTeXEntry.KEY_AUTHOR) != null ? entry.getField(BibTeXEntry.KEY_AUTHOR).toUserString() : "";
                            String title = entry.getField(BibTeXEntry.KEY_TITLE) != null ? entry.getField(BibTeXEntry.KEY_TITLE).toUserString() : "";
                            int year = Integer.parseInt(entry.getField(BibTeXEntry.KEY_YEAR) != null ? entry.getField(BibTeXEntry.KEY_YEAR).toUserString() : "1970") ;
                            String doi = entry.getField(BibTeXEntry.KEY_DOI) != null ? entry.getField(BibTeXEntry.KEY_DOI).toUserString() : "";
                            String booktitle = entry.getField(BibTeXEntry.KEY_BOOKTITLE) != null ? entry.getField(BibTeXEntry.KEY_BOOKTITLE).toUserString() : "";
                            ConferencePaper conferencePaper = new ConferencePaper(author, title, year, doi, booktitle);
                            papers.add(conferencePaper);
                        }
                    }
                    reader.close();
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Invalid folder path: " + bibFilePath);
        }

        return papers;
    }

    public boolean xmlResearcherReader(String xmlFileName, String researcherName, String password) {
        try {
            // XML dosyasını oku
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(xmlFileName));

            // <researcher> düğümlerini al
            NodeList researcherNodes = doc.getElementsByTagName("researcher");

            // Her <researcher> düğümü için kontrol yap
            for (int i = 0; i < researcherNodes.getLength(); i++) {
                Element researcherElement = (Element) researcherNodes.item(i);
                String name = researcherElement.getElementsByTagName("researcher_name").item(0).getTextContent();
                String pass = researcherElement.getElementsByTagName("password").item(0).getTextContent();

                if (name.equals(researcherName) && pass.equals(password)) {
                    return true; // Eşleşen araştırmacı adı ve şifre bulundu
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Eşleşen araştırmacı adı ve şifre bulunamadı
    }

}
