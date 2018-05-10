package com.norberth.util;

import java.lang.reflect.Field;
import java.util.Comparator;

public class ObjectComparator implements Comparator<Object> {

    private String objectAttribute;

    public ObjectComparator(String objectAttribute) {
        this.objectAttribute = objectAttribute;
    }

    @Override
    public int compare(Object o1, Object o2) {
        try {
            Field o1Field = o1.getClass().getDeclaredField(objectAttribute);
            Field o2Field = o2.getClass().getDeclaredField(objectAttribute);
            o1Field.setAccessible(true);
            o2Field.setAccessible(true);
            Object o1Value = o1Field.get(o1);
            Object o2Value = o2Field.get(o2);
            return o1Value.toString().compareTo(o2Value.toString());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
