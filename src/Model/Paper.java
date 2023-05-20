package Model;


import File.FileCreator;

import java.util.Random;

public abstract class Paper {

    private String authors;

    private String title;

    private int year;

    private String doi;

    private int number_of_downloads;

    private String type;

    public Paper(String authors, String title, int year, String doi) {
        Random random = new Random();
        this.authors = authors;
        this.title = title;
        this.year = year;
        this.doi = doi;
        this.number_of_downloads = random.nextInt(0,1500);
    }

    public Paper(String authors, String title, int year, String doi, int number_of_downloads, String type) {
        this.authors = authors;
        this.title = title;
        this.year = year;
        this.doi = doi;
        this.number_of_downloads = number_of_downloads;
        this.type = type;
    }

    public void downloadPaper(){
        System.out.println(number_of_downloads);
        FileCreator fileCreator = new FileCreator();
        this.number_of_downloads++;
        fileCreator.csvUpdater("papers.csv",this);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public int getNumber_of_downloads() {
        return number_of_downloads;
    }

    public void setNumber_of_downloads(int number_of_downloads) {
        this.number_of_downloads = number_of_downloads;
    }
}
