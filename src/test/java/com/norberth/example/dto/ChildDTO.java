package com.norberth.example.dto;

import com.norberth.core.annotation.MapAttribute;
import com.norberth.core.annotation.MapObject;
import com.norberth.example.ChildEntity;

@MapObject(fromClass = ChildEntity.class)
public class ChildDTO {
    @MapAttribute("childName")
    private String childName;


    @Override
    public String toString() {
        return "ChildDTO{" +
                "childName='" + childName + '\'' +
                '}';
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
}
