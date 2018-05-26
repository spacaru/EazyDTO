package com.norberth.examples.author.books.example.entity;

public class BaseEntity {

    protected Integer id;

    public BaseEntity(Integer id) {
        this.id = id;
    }

    public BaseEntity() {

    }

    public Integer getId() {
        return id;
    }
}
