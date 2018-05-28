package com.norberth.entity;

import com.norberth.core.annotation.MapAttribute;
import com.norberth.core.annotation.MapObject;

@MapObject(fromClass = TestObjectDataTypes.class)
public class TestObjectDataTypesTO {

    @MapAttribute("anInteger")
    private Integer integer;
    @MapAttribute("afloat")
    private Float aFloat;
    @MapAttribute("aShort")
    private Short aShort;
    @MapAttribute("aDouble")
    private Double aDouble;
    @MapAttribute("aBoolean")
    private Boolean aBoolean;
    @MapAttribute("string")
    private String string;
    @MapAttribute("entity")
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public Float getaFloat() {
        return aFloat;
    }

    public void setaFloat(Float aFloat) {
        this.aFloat = aFloat;
    }

    public Short getaShort() {
        return aShort;
    }

    public void setaShort(Short aShort) {
        this.aShort = aShort;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    @Override
    public String toString() {
        return "TestObjectDataTypesTO{" +
                "integer=" + integer +
                ", aFloat=" + aFloat +
                ", aShort=" + aShort +
                ", aDouble=" + aDouble +
                ", aBoolean=" + aBoolean +
                ", string='" + string + '\'' +
                ", entity=" + entity +
                '}';
    }
}
