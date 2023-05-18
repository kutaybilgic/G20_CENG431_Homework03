package File;

import Model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class FileCreator {

    public void csvWriter(String filePath, List<Article> articleList, List<ConferencePaper> conferencePaperList) {
        try (java.io.FileWriter writer = new java.io.FileWriter(filePath)) {
            for (Article article : articleList) {
                writer.append(article.getType()).append(",");
                writer.append(article.getAuthors()).append(",");
                writer.append(article.getTitle()).append(",");
                writer.append(String.valueOf(article.getYear())).append(",");
                writer.append(article.getDoi()).append(",");
                writer.append(String.valueOf(article.getVolume())).append(",");
                writer.append(article.getNumber()).append(",");
                writer.append(article.getJournal()).append(",");
                writer.append(String.valueOf(article.getNumber_of_downloads())).append("\n");
            }

            for (ConferencePaper conferencePaper : conferencePaperList) {
                writer.append(conferencePaper.getType()).append(",");
                writer.append(conferencePaper.getAuthors()).append(",");
                writer.append(conferencePaper.getTitle()).append(",");
                writer.append(String.valueOf(conferencePaper.getYear())).append(",");
                writer.append(conferencePaper.getDoi()).append(",");
                writer.append(conferencePaper.getBooktitle()).append(",");
                writer.append(String.valueOf(conferencePaper.getNumber_of_downloads())).append("\n");
            }

            System.out.println("Veriler CSV dosyasına yazıldı.");
        } catch (IOException e) {
            System.out.println("CSV dosyasına yazılırken bir hata oluştu: " + e.getMessage());
        }
    }

    public void jsonWriter(ReadingList readingList) {
        String filePath = "createdReadingLists.json";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                file.createNewFile();
                ArrayNode rootNode = objectMapper.createArrayNode();
                ObjectNode readingListNode = objectMapper.valueToTree(readingList);
                ArrayNode paperTitlesNode = objectMapper.createArrayNode();
                for (Paper paper : readingList.getPapers()) {
                    paperTitlesNode.add(paper.getTitle());
                }
                readingListNode.remove("papers");
                readingListNode.set("name_of_papers", paperTitlesNode);
                rootNode.add(readingListNode);
                objectMapper.writeValue(file, rootNode);
            } else {
                ArrayNode rootNode = objectMapper.readValue(file, ArrayNode.class);
                boolean found = false;

                for (int i = 0; i < rootNode.size(); i++) {
                    ObjectNode node = (ObjectNode) rootNode.get(i);
                    if (node.get("readinglist_id").asText().equals(readingList.getReadinglist_id())) {
                        node.put("number_of_papers", readingList.getNumber_of_papers());

                        ArrayNode paperTitlesNode = objectMapper.createArrayNode();
                        for (Paper paper : readingList.getPapers()) {
                            paperTitlesNode.add(paper.getTitle());
                        }
                        node.set("name_of_papers", paperTitlesNode);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    ObjectNode readingListNode = objectMapper.valueToTree(readingList);
                    ArrayNode paperTitlesNode = objectMapper.createArrayNode();
                    for (Paper paper : readingList.getPapers()) {
                        paperTitlesNode.add(paper.getTitle());
                    }
                    readingListNode.remove("papers");
                    readingListNode.set("name_of_papers", paperTitlesNode);
                    rootNode.add(readingListNode);
                }

                objectMapper.writeValue(file, rootNode);

            }

            System.out.println("ReadingList verileri JSON dosyasına başarıyla yazıldı.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Researcher> xmlWriter(String filePath) throws ParserConfigurationException, TransformerException {

        List<Researcher> researcherList = new ArrayList<>();
        Researcher researcher1 = new Researcher("DilekOzturk", "ozturkdilek");
        Researcher researcher2 = new Researcher("SerhatCaner", "canerserhat");
        Researcher researcher3 = new Researcher("TugkanTuglular", "tuglulartugkan");
        Researcher researcher4 = new Researcher("NesliErdogmus", "erdogmusnesli");
        Researcher researcher5 = new Researcher("AltugYigit", "yigitaltug");
        researcherList.add(researcher1);
        researcherList.add(researcher2);
        researcherList.add(researcher3);
        researcherList.add(researcher4);
        researcherList.add(researcher5);

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();


        // Root element: researchers
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("researchers");
        doc.appendChild(rootElement);

        for (Researcher researcher : researcherList) {
            // Researcher element
            Element researcherElement = doc.createElement("researcher");
            rootElement.appendChild(researcherElement);

            // researcher_name element
            Element researcherNameElement = doc.createElement("researcher_name");
            researcherNameElement.appendChild(doc.createTextNode(researcher.getResearcher_name()));
            researcherElement.appendChild(researcherNameElement);

            // password element
            Element passwordElement = doc.createElement("password");
            passwordElement.appendChild(doc.createTextNode(researcher.getPassword()));
            researcherElement.appendChild(passwordElement);

            // following_researcher_names element (empty for now)
            Element followingResearcherNamesElement = doc.createElement("following_researcher_names");
            researcherElement.appendChild(followingResearcherNamesElement);

            // follower_researcher_names element (empty for now)
            Element followerResearcherNamesElement = doc.createElement("follower_researcher_names");
            researcherElement.appendChild(followerResearcherNamesElement);
        }

        // Writing the XML content to a file (optional)
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(filePath); // Specify the file name
        transformer.transform(source, result);

        System.out.println("XML file created successfully.");

        return researcherList;

    }

    public void xmlUpdater(String xmlFileName, Researcher follow, Researcher follower, boolean isFollow) {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(new File(xmlFileName));

                Element rootElement = doc.getDocumentElement();

                NodeList researcherNodes = rootElement.getElementsByTagName("researcher");

                Element followElement = null;
                Element followerElement = null;

                for (int i = 0; i < researcherNodes.getLength(); i++) {
                    Element researcherElement = (Element) researcherNodes.item(i);

                    String researcherName = researcherElement.getElementsByTagName("researcher_name").item(0).getTextContent();

                    if (researcherName.equals(follow.getResearcher_name())) {
                        followElement = researcherElement;
                    }

                    if (researcherName.equals(follower.getResearcher_name())) {
                        followerElement = researcherElement;
                    }

                    if (followElement != null && followerElement != null) {
                        break;
                    }
                }

                if (followElement != null && followerElement != null) {
                    Element followingResearcherNamesElement = (Element) followElement.getElementsByTagName("following_researcher_names").item(0);
                    Element followerResearcherNamesElement = (Element) followerElement.getElementsByTagName("follower_researcher_names").item(0);

                    if (isFollow) {
                        Element followerNameElement = doc.createElement("researcher_name");
                        followerNameElement.appendChild(doc.createTextNode(follower.getResearcher_name()));
                        followingResearcherNamesElement.appendChild(followerNameElement);

                        Element followNameElement = doc.createElement("researcher_name");
                        followNameElement.appendChild(doc.createTextNode(follow.getResearcher_name()));
                        followerResearcherNamesElement.appendChild(followNameElement);
                    }
                    else {

                        NodeList researcherNameNodes = followingResearcherNamesElement.getElementsByTagName("researcher_name");
                        for (int i = 0; i < researcherNameNodes.getLength(); i++) {
                            Element researcherNameElement = (Element) researcherNameNodes.item(i);
                            String researcherName = researcherNameElement.getTextContent();
                            if (researcherName.equals(follower.getResearcher_name())) {
                                followingResearcherNamesElement.removeChild(researcherNameElement);
                                break;
                            }
                        }

                        researcherNameNodes = followerResearcherNamesElement.getElementsByTagName("researcher_name");
                        for (int i = 0; i < researcherNameNodes.getLength(); i++) {
                            Element researcherNameElement = (Element) researcherNameNodes.item(i);
                            String researcherName = researcherNameElement.getTextContent();
                            if (researcherName.equals(follow.getResearcher_name())) {
                                followerResearcherNamesElement.removeChild(researcherNameElement);
                                break;
                            }
                        }
                    }

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File(xmlFileName));
                    transformer.transform(source, result);

                    System.out.println("XML file updated successfully.");
                } else {
                    System.out.println("Follow or follower not found in the XML file.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
