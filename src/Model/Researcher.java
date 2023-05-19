package Model;

import File.FileCreator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Researcher {

    private String researcher_name;

    private String password;

    private List<Researcher> following_researcher_names;

    private List<Researcher> follower_researcher_names;

    private List<ReadingList> readingLists;

    public Researcher(String researcher_name, String password) {
        this.researcher_name = researcher_name;
        this.password = password;
        this.following_researcher_names = new ArrayList<>();
        this.follower_researcher_names =  new ArrayList<>();
        this.readingLists =  new ArrayList<>();
    }

    public ReadingList createReadingList(String readingList_name, Paper paper) throws IOException {
        for (ReadingList readingList1 : readingLists) {
            if (readingList1.getReadinglist_name().equals(readingList_name)) {
                System.out.println("Reading list already exists");
                return null;
            }
        }

        FileCreator fileCreator = new FileCreator();
        ReadingList readingList = new ReadingList(researcher_name, readingList_name);
        readingLists.add(readingList);
        fileCreator.jsonWriter(readingList);
        readingList.addPaper(paper);
        return readingList;
    }

    public void follow(Researcher researcher) {
        FileCreator fileCreator = new FileCreator();
        if (following_researcher_names.contains(researcher)) {
            System.out.println("Researcher already followed");
        } else {
            following_researcher_names.add(researcher);
            researcher.getFollower_researcher_names().add(this);
            fileCreator.xmlUpdater("researchers.xml", this, researcher, true);
        }
    }

    public void unfollow(Researcher researcher) {
        FileCreator fileCreator = new FileCreator();
        if (following_researcher_names.contains(researcher)) {
            following_researcher_names.remove(researcher);
            researcher.getFollower_researcher_names().remove(this);
            fileCreator.xmlUpdater("researchers.xml", this, researcher, false);
        } else {
            System.out.println("Researcher not followed");
        }
    }

    public String getResearcher_name() {
        return researcher_name;
    }

    public void setResearcher_name(String researcher_name) {
        this.researcher_name = researcher_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Researcher> getFollowing_researcher_names() {
        return following_researcher_names;
    }

    public void setFollowing_researcher_names(List<Researcher> following_researcher_names) {
        this.following_researcher_names = following_researcher_names;
    }

    public List<Researcher> getFollower_researcher_names() {
        return follower_researcher_names;
    }

    public void setFollower_researcher_names(List<Researcher> follower_researcher_names) {
        this.follower_researcher_names = follower_researcher_names;
    }

    public List<ReadingList> getReadingLists() {
        return readingLists;
    }

    public void setReadingLists(List<ReadingList> readingLists) {
        this.readingLists = readingLists;
    }
}
