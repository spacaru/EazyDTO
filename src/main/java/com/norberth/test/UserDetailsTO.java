package com.norberth.test;

import com.norberth.annotation.TransferObject;
import com.norberth.annotation.TransferObjectAttribute;

@TransferObject(sourceClass = UserDetails.class)
public class UserDetailsTO {

    @TransferObjectAttribute(sourceField = "name")
    private String name;
    @TransferObjectAttribute(sourceField = "email")
    private String email;
    @TransferObjectAttribute(sourceField = "simpleDTO.fullName")
    private String simpleDtoFullName;
    @TransferObjectAttribute(sourceField = "name", concatFields = {"surname", "email", "age"}, separator = ";")
    private String nameAndSurname;

    @Override
    public String toString() {
        return "UserDetailsTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", simpleDtoFullName='" + simpleDtoFullName + '\'' +
                ", nameAndSurname='" + nameAndSurname + '\'' +
                '}';
    }
}
