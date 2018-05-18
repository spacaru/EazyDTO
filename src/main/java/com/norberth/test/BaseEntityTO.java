package com.norberth.test;

import com.norberth.annotation.MapObjectAttribute;

public class BaseEntityTO {

    @MapObjectAttribute(sourceField = "id", inheritedField = true)
    protected Integer id;
}
