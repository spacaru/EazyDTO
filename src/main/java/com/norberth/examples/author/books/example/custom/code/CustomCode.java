package com.norberth.examples.author.books.example.custom.code;

import com.norberth.annotation.CustomMapper;
import com.norberth.examples.author.books.example.entity.Author;
import com.norberth.examples.author.books.example.to.AuthorTO;

/**
 * Powerful way to customize fields that need to be transformed in any way
 */
public class CustomCode  {
//    @Override
    public AuthorTO postProcess(Author sourceEntity, AuthorTO transferObject) {
//      lets say we have to capitalize all author names
        transferObject.setName(transferObject.getName().toUpperCase());
        return transferObject;
    }


}
