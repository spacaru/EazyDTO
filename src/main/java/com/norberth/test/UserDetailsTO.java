package com.norberth.test;

import com.norberth.annotation.MapListProperty;
import com.norberth.annotation.TransferObject;
import com.norberth.annotation.TransferObjectAttribute;

import java.math.BigInteger;
import java.util.List;

@TransferObject(sourceClass = UserDetails.class)
public class UserDetailsTO {

    @TransferObjectAttribute(sourceField = "name")
    private String name;
    @TransferObjectAttribute(sourceField = "email")
    private String email;
    @MapListProperty
    @TransferObjectAttribute(sourceField = "simpleDTO.complicatedEntity.id")
    private List<BigInteger> idList;

    @Override
    public String toString() {
        return "UserDetailsTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", idList=" + idList +
                '}';
    }
}
