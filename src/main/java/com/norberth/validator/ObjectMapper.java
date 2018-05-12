package com.norberth.validator;

import com.norberth.config.ConverterConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class ObjectMapper implements Mapper<Action> {

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

    @Override
    public Field getField(Action action, Object source, String sourceField) {
        Field retField = null;
        try {
            switch (action) {
                case SET_FIELDS:
                    retField = source.getClass().getDeclaredField(sourceField);

                    break;
                case RECURSIVELY_SET_FIELDS:
                    String[] split = sourceField.split(ConverterConfig.getEscapedNameDelimiter());
                    retField = getFieldResursively(new ArrayList<String>(Arrays.asList(split)), source, split[split.length - 1]);
                    break;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return retField;
    }


    @Override
    public Field getTargetObjectField(Action action, Object target, String sourceField) {
        Field retField = null;
        try {
            switch (action) {
                case RECURSIVELY_SET_FIELDS:
                    retField = target.getClass().getDeclaredField(sourceField);
                    break;
                case SET_FIELDS:
                    retField = target.getClass().getDeclaredField(sourceField);
                    break;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return retField;
    }

    @Override
    public Object getSource(Action action, Object source, String sourceField, boolean inheritedField) {
        Object targetSource = null;
        switch (action) {
            case SET_FIELDS:
                targetSource = source;
                break;
            case RECURSIVELY_SET_FIELDS:
                String[] split = sourceField.split(ConverterConfig.getEscapedNameDelimiter());
                targetSource = getSourceRecursively(new ArrayList<>(Arrays.asList(split)), source);
                break;
        }
        return targetSource;
    }

    @Override
    public Object getValue(Field field, Object source, boolean isListField, String sourceField, boolean isInherited) {
        Object value = null;
        try {
            if (!isListField) {
                value = getFieldValue(field, source);
            } else {
                String[] split = sourceField.split(ConverterConfig.getEscapedNameDelimiter());
                value = getListValues(new ArrayList<String>(Arrays.asList(split)), source, split[split.length - 1], isInherited);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    private Field getFieldResursively(List<String> sourceList, Object source, String name) {
        ListIterator<String> stringListIterator = sourceList.listIterator();
        Field f = null;
        try {
            while (stringListIterator.hasNext()) {
                String currentField = stringListIterator.next();
                stringListIterator.remove();
                if (!stringListIterator.hasNext()) {
                    if (source != null) {
                        f = source.getClass().getDeclaredField(currentField);
                        return f;
                    } else {
                        return null;
                    }
                } else {
                    Field targetField = source.getClass().getDeclaredField(currentField);
                    targetField.setAccessible(true);
                    Object newSource = targetField.get(source);
                    f = getFieldResursively(sourceList, newSource, name);
                }

            }
        } catch (NoSuchFieldException e) {
            return f;
        } catch (IllegalAccessException e) {
            return f;
        }
        return f;
    }

    private Object getSourceRecursively(ArrayList<String> strings, Object source) {
        ListIterator<String> stringListIterator = strings.listIterator();
        Object sourceObject = null;
        try {
            while (stringListIterator.hasNext()) {
                String currentField = stringListIterator.next();
                stringListIterator.remove();
                if (!stringListIterator.hasNext()) {
                    return source;
                } else {
                    Field f = null;
                        f = source.getClass().getDeclaredField(currentField);
                    f.setAccessible(true);
                    Object newSource = f.get(source);
                    sourceObject = getSourceRecursively(strings, newSource);
                }

            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sourceObject;
    }

    private Object getListValues(ArrayList<String> sourceList, Object source, String name, boolean isInherited) {
        ListIterator<String> stringListIterator = sourceList.listIterator();
        Field f = null;
        try {
            while (stringListIterator.hasNext()) {
                String currentField = stringListIterator.next();
                stringListIterator.remove();
                if (!stringListIterator.hasNext()) {
                    if (source instanceof List) {
                        List newList = new ArrayList();
                        for (Object obj : (List) source) {
                            Object sourceObject = obj.getClass().newInstance();
                            Field sourceField = null;
                            if (!isInherited) {
                                sourceField = sourceObject.getClass().getDeclaredField(name);
                            } else {
                                sourceField = sourceObject.getClass().getSuperclass().getDeclaredField(name);
                            }
                            sourceField.setAccessible(true);
                            Object value = sourceField.get(obj);
                            Object targetObject = null;
                            if (sourceField.getType().isPrimitive()) {
                                targetObject = value;
                            } else {
                                Object newObject = sourceField.getType().newInstance();
                                sourceField.set(newObject, value);
                            }
                            newList.add(targetObject);
                        }
                        return newList;
                    } else {
                        return null;
                    }

                } else {
                    Field targetField = source.getClass().getDeclaredField(currentField);
                    targetField.setAccessible(true);
                    Object newSource = targetField.get(source);
                    Object retObject = null;
                    retObject = getListValues(sourceList, newSource, name,isInherited);
                    return retObject;
                }

            }
        } catch (NoSuchFieldException e) {
//            this.error = true;
            return f;
        } catch (IllegalAccessException e) {
            return f;
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return f;
    }

    private Object getFieldValue(Field field, Object source) throws IllegalAccessException {
        Object value = null;
        field.setAccessible(true);
        value = field.get(source);
        return value;
    }
}
