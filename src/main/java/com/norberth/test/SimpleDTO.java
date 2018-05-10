package com.norberth.test;

import java.util.List;

public class SimpleDTO {

    private String fullName;

    private List<ComplicatedEntity> complicatedEntity;

    public SimpleDTO(String fullName) {
        this.fullName = fullName;
    }


    public SimpleDTO(String fullName, List<ComplicatedEntity> complicatedEntity) {
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
                ", complicatedEntity=" + complicatedEntity +
                '}';
    }
}
