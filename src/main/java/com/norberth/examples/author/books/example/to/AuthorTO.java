package com.norberth.examples.author.books.example.to;

import com.norberth.annotation.MapAttribute;
import com.norberth.annotation.MapList;
import com.norberth.annotation.MapObject;
import com.norberth.examples.author.books.example.entity.Author;
import com.norberth.examples.author.books.example.custom.code.CustomCode;

import java.util.List;

@MapObject(sourceClass = Author.class,customCode = CustomCode.class)
public class AuthorTO {
    //    maps name field from each published book of the author to this list of strings
    @MapList(sourceField = "publishedBooks.name")
    private List<String> bookNames;

    //    we can map inherited fields
    @MapAttribute(sourceField = "name")
    private String name;
    @MapAttribute(sourceField = "age")
    private int age;

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
                '}';
    }
}
