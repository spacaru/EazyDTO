package com.norberth.examples.author.books.example.entity;

import java.util.List;

public class Author extends Person {

    private List<Book> publishedBooks;

    public Author() {
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
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", rentedBooks=" + rentedBooks +
                '}';
    }
}
