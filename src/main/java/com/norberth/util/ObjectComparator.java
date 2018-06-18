package com.norberth.util;

import java.lang.reflect.Field;
import java.util.Comparator;

public class ObjectComparator implements Comparator<Object> {

    private final String objectAttribute;
    private final SortingType sortingType;

    public ObjectComparator(String objectAttribute, SortingType sortingType) {
        this.objectAttribute = objectAttribute;
        this.sortingType = sortingType;
    }

    @Override
    public int compare(Object o1, Object o2) {
        try {
            Object o1Value = null;
            Object o2Value = null;
            if (!o1.getClass().isAssignableFrom(String.class) && !o2.getClass().isAssignableFrom(String.class)) {
                Field o1Field = o1.getClass().getDeclaredField(objectAttribute);
                Field o2Field = o2.getClass().getDeclaredField(objectAttribute);
                o1Field.setAccessible(true);
                o2Field.setAccessible(true);
                o1Value = o1Field.get(o1);
                o2Value = o2Field.get(o2);
            } else {
                o1Value = o1;
                o2Value = o2;
            }
            if (o1Value == null || o2Value == null) {
                throw new RuntimeException("Null object! Sorting impossible. ;(");
            }
            if (sortingType.equals(SortingType.ASCENDING)) {
                return o1Value.toString().compareTo(o2Value.toString());
            } else {
                return o2Value.toString().compareTo(o1Value.toString());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
