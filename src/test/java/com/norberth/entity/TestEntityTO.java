package com.norberth.entity;

import com.norberth.annotation.MapAttribute;
import com.norberth.annotation.MapObject;

@MapObject(fromClass = TestEntity.class)
public class TestEntityTO {

    @MapAttribute("intTest")
    private int intTest;
    @MapAttribute("doubleTest")

    private double doubleTest;
    @MapAttribute("stringTest")

    private String stringTest;
    @MapAttribute("floatTest")

    private float floatTest;
    @MapAttribute("boolTest")

    private boolean boolTest;

    public int getIntTest() {
        return intTest;
    }

    public double getDoubleTest() {
        return doubleTest;
    }

    public String getStringTest() {
        return stringTest;
    }

    public float getFloatTest() {
        return floatTest;
    }

    public boolean isBoolTest() {
        return boolTest;
    }

    @Override
    public String toString() {
        return "TestEntityTO{" +
                "intTest=" + intTest +
                ", doubleTest=" + doubleTest +
                ", stringTest='" + stringTest + '\'' +
                ", floatTest=" + floatTest +
                ", boolTest=" + boolTest +
                '}';
    }
}
