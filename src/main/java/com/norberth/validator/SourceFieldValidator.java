package com.norberth.validator;

public class SourceFieldValidator implements Validator {

    @Override
    public Action getAction(String field) {
        Action action = null;
        if (field.contains(".")) {
            action = Action.RECURSIVELY_SET_FIELDS;
        } else {
            action = Action.SET_FIELDS;
        }
        return action;
    }
}
