package Model;

import File.FileCreator;
import Util.StringGenerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadingList {

    private String readinglist_id;

    private String creator_researcher_name;

    private String readinglist_name;

    private int number_of_papers;

    private List<String> papers;

    public ReadingList(String creator_researcher_name, String readingList_name) {
        this.readinglist_id = StringGenerator.generateRandomString(15);
        this.creator_researcher_name = creator_researcher_name;
        this.readinglist_name = readingList_name;
        this.number_of_papers = 0;
        this.papers = new ArrayList<>();
    }

    public ReadingList(String readinglist_id, String creator_researcher_name, String readinglist_name, int number_of_papers, List<String> papers) {
        this.readinglist_id = readinglist_id;
        this.creator_researcher_name = creator_researcher_name;
        this.readinglist_name = readinglist_name;
        this.number_of_papers = number_of_papers;
        this.papers = papers;
    }

    public void addPaper(String paper) throws IOException {
        FileCreator fileCreator = new FileCreator();
        papers.add(paper);
        number_of_papers++;
        fileCreator.jsonUpdateReadingList(this, paper, true);
    }

    public void removePaper(String paper) throws IOException {
        FileCreator fileCreator = new FileCreator();
        papers.remove(paper);
        number_of_papers--;
        fileCreator.jsonUpdateReadingList(this, paper, false);

    }

    public String getReadinglist_id() {
        return readinglist_id;
    }

    public void setReadinglist_id(String id) {
        this.readinglist_id = id;
    }

    public String getCreator_researcher_name() {
        return creator_researcher_name;
    }

    public void setCreator_researcher_name(String creator_researcher_name) {
        this.creator_researcher_name = creator_researcher_name;
    }

    public String getReadinglist_name() {
        return readinglist_name;
    }

    public void setReadinglist_name(String readingList_name) {
        this.readinglist_name = readingList_name;
    }

    public int getNumber_of_papers() {
        return number_of_papers;
    }

    public void setNumber_of_papers(int number_of_papers) {
        this.number_of_papers = number_of_papers;
    }

    public List<String> getPapers() {
        return papers;
    }

    public void setPapers(List<String> papers) {
        this.papers = papers;
    }
}
