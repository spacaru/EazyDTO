package com.norberth.test;

import com.norberth.annotation.MapListAttribute;
import com.norberth.annotation.MapObject;
import com.norberth.annotation.MapObjectAttribute;

import java.math.BigInteger;
import java.util.List;

@MapObject(sourceClass = UserDetails.class)
public class UserDetailsTO {

    @MapObjectAttribute(sourceField = "name")
    private String name;
    @MapObjectAttribute(sourceField = "email")
    private String email;
    @MapListAttribute(sourceField = "simpleDTO.complicatedEntity.id", inheritedField = true)
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
