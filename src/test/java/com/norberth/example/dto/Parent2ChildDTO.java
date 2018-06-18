package com.norberth.example.dto;

import com.norberth.core.annotation.MapAttribute;
import com.norberth.core.annotation.MapObject;
import com.norberth.example.ChildEntity;
import com.norberth.example.ParentEntity;
import com.norberth.example.custom.mapper.CustomParent2ChildMapper;

import java.util.List;
import java.util.Set;

@MapObject(fromClass = ParentEntity.class, customMapperClass = CustomParent2ChildMapper.class)
public class Parent2ChildDTO {

    @MapAttribute("parentName")
    private String parentName;
    @MapObject(value = "children.childName", fromClass = ChildEntity.class)
    private Set<String> children;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Set<String> getChildren() {
        return children;
    }

    public void setChildren(Set<String> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Parent2ChildDTO{" +
                "parentName='" + parentName + '\'' +
                ", children=" + children +
                '}';
    }
}
