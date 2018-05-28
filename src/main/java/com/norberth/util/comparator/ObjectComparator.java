package com.norberth.util.comparator;

import java.lang.reflect.Field;
import java.util.Comparator;

public class ObjectComparator implements Comparator<Object> {

    private String objectAttribute;
    private SortationType sortationType;

    public ObjectComparator(String objectAttribute, SortationType sortationType) {
        this.objectAttribute = objectAttribute;
        this.sortationType = sortationType;
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
            if (sortationType.equals(SortationType.ASCENDING)) {
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