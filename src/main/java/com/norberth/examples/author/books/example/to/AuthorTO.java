package com.norberth.examples.author.books.example.to;

import com.norberth.annotation.MapAttribute;
import com.norberth.annotation.MapList;
import com.norberth.annotation.MapObject;
import com.norberth.examples.author.books.example.custom.code.CustomCode;
import com.norberth.examples.author.books.example.entity.Author;
import com.norberth.examples.author.books.example.entity.Book;

import java.util.List;

@MapObject(fromClass = Author.class, customMapperClass = CustomCode.class)
public class AuthorTO {
    //    maps name field from each published book of the author to this list of strings
    @MapObject(value="publishedBooks",fromClass = Book.class)
    private List<BookTO> books;

    //    we can map inherited fields
    @MapAttribute("name")
    private String name;
    //    we can map primitives
    @MapAttribute("age")
    private int age;
    //    we can map Object fields
    @MapAttribute("genre")
    private String genre;
    //    we set this field using CustomeCode class by implementing CustomEvent<Author,AuthorTO> interface then in postMap method we set the fullName
    private String fullName;
    @MapAttribute("publisher.name")
    private String publisherName;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public List<BookTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookTO> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "AuthorTO{" +
                "books=" + books +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", genre='" + genre + '\'' +
                ", fullName='" + fullName + '\'' +
                ", publisherName='" + publisherName + '\'' +
                '}';
    }
}
