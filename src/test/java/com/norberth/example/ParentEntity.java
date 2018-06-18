package com.norberth.example;


public class ParentEntity {

    private int id;
    private String parentName;
    private java.util.Set children;

    public ParentEntity() {
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.util.Set getChildren() {
        return children;
    }

    public void setChildren(java.util.Set children) {
        this.children = children;
    }

    private class Set<T> {
    }
}
