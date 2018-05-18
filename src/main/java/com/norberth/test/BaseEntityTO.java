package com.norberth.test;

import com.norberth.annotation.MapAttribute;

public class BaseEntityTO {

    @MapAttribute(sourceField = "idd", inheritedField = false)
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
