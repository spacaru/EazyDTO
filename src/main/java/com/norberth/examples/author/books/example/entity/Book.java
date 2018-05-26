package com.norberth.examples.author.books.example.entity;

public class Book extends BaseEntity {

    private String name;
    private String genre;

    public Book() {
        super();
    }

    public Book(Integer id,String name, String genre) {
        super(id);
        this.name = name;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
