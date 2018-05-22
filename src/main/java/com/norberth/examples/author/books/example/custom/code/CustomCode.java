package com.norberth.examples.author.books.example.custom.code;

import com.norberth.event.CustomEvent;
import com.norberth.examples.author.books.example.entity.Author;
import com.norberth.examples.author.books.example.to.AuthorTO;

/**
 * Powerful way to customize fields that need to be transformed in any way
 */
public class CustomCode implements CustomEvent<Author, AuthorTO> {
    //    @Override
    public AuthorTO postMap(Author sourceEntity, AuthorTO transferObject) {
//      lets say we have to capitalize all author names
        transferObject.setName(transferObject.getName().toUpperCase());
//        or maybe we need to concatenate two fields
        transferObject.setFullName(sourceEntity.getName() + " " + sourceEntity.getSurname());
        return transferObject;
    }


}
