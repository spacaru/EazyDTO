package com.norberth.examples.author.books.example.to;

import com.norberth.annotation.MapAttribute;
import com.norberth.annotation.MapList;
import com.norberth.annotation.MapObject;
import com.norberth.examples.author.books.example.custom.code.CustomCode;
import com.norberth.examples.author.books.example.entity.Author;

import java.util.List;

@MapObject(sourceClass = Author.class, customCodeClass = CustomCode.class)
public class AuthorTO {
    //    maps name field from each published book of the author to this list of strings
    @MapList(sourceField = "publishedBooks.name")
    private List<String> bookNames;

    //    we can map inherited fields
    @MapAttribute(sourceField = "name")
    private String name;
    //    we can map primitives
    @MapAttribute(sourceField = "age")
    private int age;
    //    we can map Object fields
    @MapAttribute(sourceField = "genre")
    private String genre;
    //    we set this field using CustomeCode class by implementing CustomMapper<Author,AuthorTO> interface then in postMap method we set the fullName
    private String fullName;

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

    public List<String> getBookNames() {
        return bookNames;
    }

    public void setBookNames(List<String> bookNames) {
        this.bookNames = bookNames;
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
                "bookNames=" + bookNames +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", genre='" + genre + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
