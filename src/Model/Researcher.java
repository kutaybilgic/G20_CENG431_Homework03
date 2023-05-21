package Model;

import Controller.ReadingListController;
import File.FileCreator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Researcher {

    private String researcher_name;

    private String password;

    private List<String> following_researcher_names;

    private List<String> follower_researcher_names;

    private List<ReadingList> readingLists;

    public Researcher(String researcher_name, String password) {
        this.researcher_name = researcher_name;
        this.password = password;
        this.following_researcher_names = new ArrayList<>();
        this.follower_researcher_names =  new ArrayList<>();
        this.readingLists =  new ArrayList<>();
    }

    public Researcher(String researcher_name, String password, List<String> following_researcher_names, List<String> follower_researcher_names) {
        this.researcher_name = researcher_name;
        this.password = password;
        this.following_researcher_names = following_researcher_names;
        this.follower_researcher_names = follower_researcher_names;
        this.readingLists =  new ArrayList<>();
    }

    public ReadingList createReadingList(String readingList_name, Paper paper) throws IOException {
        ReadingListController readingListController = new ReadingListController();

        if (readingListController.isListExist(researcher_name, readingList_name)) {
            return null;
        }

        FileCreator fileCreator = new FileCreator();
        ReadingList readingList = new ReadingList(researcher_name, readingList_name);
        readingLists.add(readingList);
        fileCreator.jsonWriter(readingList);
        readingList.addPaper(paper.getTitle());
        return readingList;
    }

    public void follow(Researcher researcher) {
        FileCreator fileCreator = new FileCreator();
        following_researcher_names.add(researcher.getResearcher_name());
        researcher.getFollower_researcher_names().add(this.researcher_name);
        fileCreator.xmlUpdater("researchers.xml", this, researcher, true);

    }

    public void unfollow(Researcher researcher) {
        FileCreator fileCreator = new FileCreator();
        following_researcher_names.remove(researcher.researcher_name);
        researcher.getFollower_researcher_names().remove(this.researcher_name);
        fileCreator.xmlUpdater("researchers.xml", this, researcher, false);
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

    public List<String> getFollowing_researcher_names() {
        return following_researcher_names;
    }

    public void setFollowing_researcher_names(List<String> following_researcher_names) {
        this.following_researcher_names = following_researcher_names;
    }

    public List<String> getFollower_researcher_names() {
        return follower_researcher_names;
    }

    public void setFollower_researcher_names(List<String> follower_researcher_names) {
        this.follower_researcher_names = follower_researcher_names;
    }

    public List<ReadingList> getReadingLists() {
        return readingLists;
    }

    public void setReadingLists(List<ReadingList> readingLists) {
        this.readingLists = readingLists;
    }
}
