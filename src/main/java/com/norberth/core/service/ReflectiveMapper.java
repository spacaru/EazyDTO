package com.norberth.core.service;

import java.lang.reflect.Field;

public interface ReflectiveMapper<T extends AttributeAccesorType> {

    /**
     * Returns the current action to perform based on field implementation
     * Example :
     * given the next enum
     * public enum ActionType {
     * SET_FIELD,RECURSIVELY_SET_FIELD
     * }
     *
     * @param field
     * @return
     */
    T getAction(String field);

    T getAction(String[] fields);

    /**
     * Return the field needed for an {@link AttributeAccesorType} on a given {@link Object} source for a given value
     *
     * @param action
     * @param source
     * @param sourceField
     * @return - the field
     */
    Field getField(T action, Object source, String sourceField,ResourceSharingService resourceSharingService);

    /**
     * @param attributeAccesorType
     * @param target
     * @param sourceField
     * @return
     */
    Field getTargetObjectField(AttributeAccesorType attributeAccesorType, Object target, String sourceField);

    Object getSource(AttributeAccesorType attributeAccesorType, Object source, String sourceField, boolean inheritedField);


    Object getValue(Field field, Object source, boolean isListField, String sourceField, boolean isInherited, Field targetObjectField);

}
