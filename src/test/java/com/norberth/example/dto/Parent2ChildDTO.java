package com.norberth.example.dto;

import com.norberth.core.annotation.MapAttribute;
import com.norberth.core.annotation.MapObject;
import com.norberth.example.ChildEntity;
import com.norberth.example.ParentEntity;

import java.util.List;

@MapObject(fromClass = ParentEntity.class)
public class Parent2ChildDTO {

    @MapAttribute("parentName")
    private String parentName;
    @MapObject(value = "children.childName",fromClass = ChildEntity.class)
    private List<String> children;

    @Override
    public String toString() {
        return "Parent2ChildDTO{" +
                "parentName='" + parentName + '\'' +
                ", children=" + children +
                '}';
    }
}
