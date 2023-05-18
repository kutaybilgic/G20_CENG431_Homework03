package Model;

public class ConferencePaper extends Paper{

    private String booktitle;

    public ConferencePaper(String authors, String title, int year, String doi, String booktitle) {
        super(authors, title, year, doi);
        this.booktitle = booktitle;
    }

    @Override
    public void downloadPaper() {
        super.downloadPaper();
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public String getType() {
        return "Conference Paper";
    }
}
