package com.norberth.validator;

public interface Validator<T extends Enum> {

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
}
