package com.norberth.validator;

import java.lang.reflect.Field;

public interface Mapper<T extends Enum> {

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
    public T getAction(String field);

    /**
     * Return the field needed for an {@link Action} on a given {@link Object} source for a given sourceField
     *
     * @param action
     * @param source
     * @param sourceField
     * @return - the field
     */
    Field getField(T action, Object source, String sourceField);

    /**
     * @param action
     * @param target
     * @param sourceField
     * @return
     */
    Field getTargetObjectField(T action, Object target, String sourceField);

    Object getSource(Action action, Object source, String sourceField, boolean inheritedField);


    Object getValue(Field field, Object source, boolean isListField, String sourceField, boolean isInherited);
}
