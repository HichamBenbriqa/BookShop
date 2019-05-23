package com.example.hp.bookshop.Model;

public class Book {
    private String iSBN;
    private String title;
    private String bookfile;
    private String summary;
    private int edition;
    private int prix;
    private String author;
    private String genre;
    private String image;

    public Book(String iSBN, String title, String bookfile, String summary, int edition,int prix, String  author, String genre,String image) {
        this.iSBN = iSBN;
        this.title = title;
        this.bookfile = bookfile;
        this.summary = summary;
        this.edition = edition;
        this.prix=prix;
        this.author = author;
        this.genre = genre;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public Book(String iSBN, String title, String image) {
        this.iSBN = iSBN;
        this.title = title;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getiSBN() {
        return iSBN;
    }

    public void setiSBN(String iSBN) {
        this.iSBN = iSBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookfile() {
        return bookfile;
    }

    public void setBookfile(String bookfile) {
        this.bookfile = bookfile;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}
