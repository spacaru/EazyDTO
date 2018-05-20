package com.norberth.examples.author.books.example;

import com.norberth.examples.author.books.example.entity.Author;
import com.norberth.examples.author.books.example.entity.Book;
import com.norberth.examples.author.books.example.entity.Genre;
import com.norberth.examples.author.books.example.to.AuthorTO;
import com.norberth.factory.GenericConverterFactory;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        GenericConverterFactory gcm = GenericConverterFactory.getInstance();
        gcm.setDebug(true);
        gcm.setPackageName("com.norberth.examples");

//        lets create some Authors with some books
        Book sleepingBeauty = new Book("Sleeping Beauty", "fantasy");
        Book whoStoleMyCookie = new Book("Who stole my cookie?", "comedy");
        Author jamesDelaney = new Author(Arrays.asList(sleepingBeauty, whoStoleMyCookie));
        jamesDelaney.setGenre(Genre.FANTASY);
        jamesDelaney.setAge(42);
        jamesDelaney.setName("James");
        jamesDelaney.setSurname("Delaney");
        Book firstBook = new Book("Uninspired book", "autobiography");
        Book secondBook = new Book("Nothing new", "lifestyle");
        Author joanaDelaney = new Author(Arrays.asList(firstBook, secondBook));
        joanaDelaney.setAge(27);
        joanaDelaney.setGenre(Genre.ROMANCE);
        joanaDelaney.setName("Joana");
        joanaDelaney.setSurname("Delaney");


//        now lets say we want to display the names of books for this author in a web page and we only need the book name
//        for this we create an AuthorTO class with a list of book names using @MapObject @MapList attributes ( see AuthorTO.class)

//        now using the generic converter manager we can conver jamesDelaney entity to TO using getTo method
        AuthorTO authorTO = (AuthorTO) gcm.getConverter(AuthorTO.class).getTo(jamesDelaney);
//        using the get getToList we can map list of authors to list of AuthorTOs
        List<AuthorTO> authorTOList = (List) gcm.getConverter(AuthorTO.class).getToList(Arrays.asList(jamesDelaney, joanaDelaney));
    }
}
