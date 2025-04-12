package app.javacrud;

public class Book {
    private int id;
    private String title;
    private String author;
    private int year;
    private int pages;

    public Book(int id, String title, String author,int year, int pages) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.author = author;
        this.pages = pages;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getPages() {
        return pages;
    }
}
