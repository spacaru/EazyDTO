package com.norberth.examples.author.books.example.entity;

public class Publisher {
    private String name;

    public Publisher(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "publisherName='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String publisherName) {
        this.name = publisherName;
    }
}
