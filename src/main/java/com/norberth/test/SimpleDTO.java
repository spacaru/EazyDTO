package com.norberth.test;

import com.norberth.annotation.TransferObject;
import com.norberth.annotation.TransferObjectAttribute;

@TransferObject(sourceClass = UserDetails.class)
public class SimpleDTO {

    @TransferObjectAttribute(sourceField = "name", concatFields = {"surname"})
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "SimpleDTO{" +
                "fullName='" + fullName + '\'' +
                '}';
    }
}
