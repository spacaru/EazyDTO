package com.norberth.examples.author.books.example.to;

import com.norberth.annotation.MapAttribute;
import com.norberth.annotation.MapObject;
import com.norberth.examples.author.books.example.entity.Book;

@MapObject(fromClass = Book.class)
public class BookTO {
    @MapAttribute("name")
    private String name;
    @MapAttribute("id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BookTO{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
