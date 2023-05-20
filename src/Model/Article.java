package Model;

public class Article extends Paper{

    private int volume;

    private String number;

    private String journal;


    public Article(String authors, String title, int year, String doi, int volume, String number, String journal) {
        super(authors, title, year, doi);
        this.volume = volume;
        this.number = number;
        this.journal = journal;
    }

    public Article(String authors, String title, int year, String doi, int number_of_downloads, String type, int volume, String number, String journal) {
        super(authors, title, year, doi, number_of_downloads, type);
        this.volume = volume;
        this.number = number;
        this.journal = journal;
    }

    @Override
    public void downloadPaper() {
        super.downloadPaper();
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getType() {
        return "Article";
    }
}
