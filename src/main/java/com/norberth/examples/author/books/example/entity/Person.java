package com.norberth.examples.author.books.example.entity;

import java.util.List;

public class Person {

    protected String name;
    protected String surname;
    private int age;
    protected List<Book> rentedBooks;

    public Person() {
    }

    public Person(String name, String surname, List<Book> rentedBooks,int age) {
        this.name = name;
        this.surname = surname;
        this.rentedBooks = rentedBooks;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Book> getRentedBooks() {
        return rentedBooks;
    }

    public void setRentedBooks(List<Book> rentedBooks) {
        this.rentedBooks = rentedBooks;
    }
}
