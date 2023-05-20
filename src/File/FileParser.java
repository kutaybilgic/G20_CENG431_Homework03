package File;

import Model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
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
                            int year = Integer.parseInt(entry.getField(BibTeXEntry.KEY_YEAR) != null ? entry.getField(BibTeXEntry.KEY_YEAR).toUserString() : String.valueOf(1970)) ;
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

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(xmlFileName));


            NodeList researcherNodes = doc.getElementsByTagName("researcher");


            for (int i = 0; i < researcherNodes.getLength(); i++) {
                Element researcherElement = (Element) researcherNodes.item(i);
                String name = researcherElement.getElementsByTagName("researcher_name").item(0).getTextContent();
                String pass = researcherElement.getElementsByTagName("password").item(0).getTextContent();

                if (name.equals(researcherName) && pass.equals(password)) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<ReadingList> jsonReadingListParserForUser(List<Researcher> researcherList, String researcherName) {
        List<ReadingList> readingLists = new ArrayList<>();
        for (Researcher researcher: researcherList) {
            if (researcher.getResearcher_name().equals(researcherName)) {
                readingLists = researcher.getReadingLists();
            }
        }

        return readingLists;
    }

    public List<Article> readCSVArticle(String filePath) {
        List<Article> objects = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length >= 5) {
                    String type = parts[0].trim();
                    String title = parts[2].trim();
                    String authors = parts[1].trim();
                    String year = parts[3].trim();
                    String doi = parts[4].trim();

                    if (type.equalsIgnoreCase("Article")) {
                        String volume = parts[5].trim();
                        String number = parts[6].trim();
                        String journal = parts[7].trim();
                        String download = parts[8].trim();
                        Article article = new Article(authors, title, Integer.parseInt(year), doi, Integer.parseInt(download), type, Integer.parseInt(volume), number, journal);
                        objects.add(article);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }

    public List<ConferencePaper> readCSVConferencePaper(String filePath) {
        List<ConferencePaper> objects = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length >= 5) {
                    String type = parts[0].trim();
                    String title = parts[2].trim();
                    String authors = parts[1].trim();
                    String year = parts[3].trim();
                    String doi = parts[4].trim();

                    if (type.equalsIgnoreCase("Conference Paper")) {
                        String boooktitle = parts[5].trim();
                        String download = parts[6].trim();
                        ConferencePaper conferencePaper = new ConferencePaper(authors, title, Integer.parseInt(year), doi, Integer.parseInt(download), type,boooktitle );
                        objects.add(conferencePaper);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }

    public boolean jsonIsPaperInList(Paper paper, String username, String listName) {
        try {
            String jsonContent = Files.readString(Path.of("ReadingLists.json")); // JSON dosyasını oku

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonContent);

            // JSON dizisini dolaş ve uygun listeyi bul
            for (JsonNode listNode : jsonNode) {
                String listUsername = listNode.get("creator_researcher_name").asText();
                String currentListName = listNode.get("readinglist_name").asText();

                // İstenilen kullanıcı adı ve liste adı ile eşleşen listeyi bul
                if (username.equals(listUsername) && listName.equals(currentListName)) {
                    JsonNode nameOfPapersNode = listNode.get("name_of_papers");

                    // Eğer paper'ın başlığı name_of_papers dizisinde varsa true dön
                    for (JsonNode titleNode : nameOfPapersNode) {
                        String title = titleNode.asText();
                        if (title.equals(paper.getTitle())) {
                            return true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

}
