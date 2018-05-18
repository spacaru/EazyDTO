package com.norberth.test;

import com.norberth.annotation.MapList;
import com.norberth.annotation.MapObject;
import com.norberth.annotation.MapAttribute;

import java.math.BigInteger;
import java.util.List;

@MapObject(sourceClass = UserDetails.class)
public class UserDetailsTO {

    @MapAttribute(sourceField = "name")
    private String name;
    @MapAttribute(sourceField = "email")
    private String email;
    @MapList(sourceField = "simpleDTO.complicatedEntity.id", inheritedField = true)
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
