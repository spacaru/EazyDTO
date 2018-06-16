package com.norberth.example;

public class ChildEntity {

    private int id;
    private String childName;

    public ChildEntity() {
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ChildEntity{" +
                "id=" + id +
                ", childName='" + childName + '\'' +
                '}';
    }
}
