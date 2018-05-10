package com.norberth.test;

public class ComplicatedEntity {

    private int id;

    public ComplicatedEntity() {
    }

    public ComplicatedEntity(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ComplicatedEntity{" +
                "id=" + id +
                '}';
    }
}
