package com.norberth.examples.author.books.example.entity;

public class Publisher {
    private String companyName;

    public Publisher(String name) {
        this.companyName = name;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "publisherName='" + companyName + '\'' +
                '}';
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String publisherName) {
        this.companyName = publisherName;
    }
}
