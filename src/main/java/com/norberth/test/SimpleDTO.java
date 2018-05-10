package com.norberth.test;

public class SimpleDTO {

    private String fullName;

    private ComplicatedEntity complicatedEntity;

    public SimpleDTO(String fullName) {
        this.fullName = fullName;
    }

    public SimpleDTO(String fullName, ComplicatedEntity complicatedEntity) {
        this.fullName = fullName;
        this.complicatedEntity = complicatedEntity;
    }

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
