package com.norberth.examples.author.books.example.entity;

import java.util.List;

public class Author extends Person {

    private List<Book> publishedBooks;
    private Genre genre;
    private Publisher publisher;

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Author() {
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author(List<Book> publishedBooks) {
        this.publishedBooks = publishedBooks;
    }

    public List<Book> getPublishedBooks() {
        return publishedBooks;
    }

    public void setPublishedBooks(List<Book> publishedBooks) {
        this.publishedBooks = publishedBooks;
    }

    @Override
    public String toString() {
        return "Author{" +
                "publishedBooks=" + publishedBooks +
                ", genre=" + genre +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", rentedBooks=" + rentedBooks +
                '}';
    }
}
