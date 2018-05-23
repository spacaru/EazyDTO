package com.norberth.examples.author.books.example.to;

import com.norberth.annotation.MapAttribute;
import com.norberth.annotation.MapObject;
import com.norberth.examples.author.books.example.entity.Publisher;

@MapObject(fromClass = Publisher.class)
public class PublisherTO {

    @MapAttribute("companyName")
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "PublisherTO{" +
                "companyName='" + companyName + '\'' +
                '}';
    }
}
