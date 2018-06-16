package com.norberth.core.service;

import com.norberth.config.ConverterConfig;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

public class ObjectMapper implements FieldAction<AttributeAccesorType> {

    private static final Logger logger = Logger.getLogger(ObjectMapper.class.getSimpleName());

    @Override
    public AttributeAccesorType getAction(String field) {
        AttributeAccesorType attributeAccesorType = null;
        if (field.contains(".")) {
            attributeAccesorType = AttributeAccesorType.RECURSIVELY_SET_FIELDS;
        } else {
            attributeAccesorType = AttributeAccesorType.SET_FIELDS;
        }
        return attributeAccesorType;
    }

    @Override
    public AttributeAccesorType getAction(String[] fields) {
        return AttributeAccesorType.RECURSIVELY_SET_FIELDS;
    }

    @Override
    public Field getField(AttributeAccesorType attributeAccesorType, Object source, String sourceField, ResourceSharingService resourceSharingService) {
        Field retField = null;
        try {
            switch (attributeAccesorType) {
                case SET_FIELDS:
                    if (source.getClass().isAssignableFrom(List.class)) {
                        System.out.println("bingo");
                    }
                    retField = source.getClass().getDeclaredField(sourceField);
                    break;
                case RECURSIVELY_SET_FIELDS:
                    String[] split = sourceField.split(ConverterConfig.getEscapedNameDelimiter());
                    retField = getFieldResursively(new ArrayList<String>(Arrays.asList(split)), source, split[split.length - 1], false);
                    break;
            }
        } catch (NoSuchFieldException e) {
            try {
                retField = source.getClass().getSuperclass().getDeclaredField(sourceField);
                resourceSharingService.setIS_INHERITED(true);
                return retField;
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            }
        }
        return retField;
    }


    @Override
    public Field getTargetObjectField(AttributeAccesorType attributeAccesorType, Object target, String sourceField) {
        Field retField = null;
        try {
            switch (attributeAccesorType) {
                case RECURSIVELY_SET_FIELDS:
                    retField = target.getClass().getDeclaredField(sourceField);
                    break;
                case SET_FIELDS:
                    retField = target.getClass().getDeclaredField(sourceField);

                    break;
            }
        } catch (NoSuchFieldException e) {
            try {
                retField = target.getClass().getSuperclass().getDeclaredField(sourceField);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            }
        }
        return retField;
    }

    @Override
    public Object getSource(AttributeAccesorType attributeAccesorType, Object source, String sourceField, boolean inheritedField) {
        Object targetSource = null;
        switch (attributeAccesorType) {
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
    public Object getValue(Field field, Object source, boolean isListField, String sourceField, boolean isInherited, Field targetObjectField) {
        Object value = null;
        try {
            if (!isListField) {
                value = getFieldValue(field, source, targetObjectField);
            } else {
                String[] split = sourceField.split(ConverterConfig.getEscapedNameDelimiter());
                value = getListValues(new ArrayList<String>(Arrays.asList(split)), source, split[split.length - 1], isInherited);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    private Field getFieldResursively(List<String> sourceList, Object source, String name, boolean isInherited) {
        ListIterator<String> stringListIterator = sourceList.listIterator();
        Field f = null;
        String currField = "";
        try {
            while (stringListIterator.hasNext()) {

                String currentField = stringListIterator.next();
                currField = currentField;
                stringListIterator.remove();
                if (!stringListIterator.hasNext()) {
                    if (source != null && !source.getClass().getTypeName().contains("List")) {
                        f = source.getClass().getDeclaredField(currentField);
                        return f;
                    } else {
                        Object obj = ((List) source).get(0);
                        f = obj.getClass().getDeclaredField(currentField);
                        return f;
                    }
                } else {
                    Field targetField = source.getClass().getDeclaredField(currentField);
                    targetField.setAccessible(true);
                    Object newSource = targetField.get(source);
                    f = getFieldResursively(sourceList, newSource, name, isInherited);
                }

            }
        } catch (NoSuchFieldException e) {
            try {
                f = source.getClass().getSuperclass().getDeclaredField(currField);
                return f;
            } catch (NoSuchFieldException e1) {
                return f;
            }
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
                        List<Object> newList = new ArrayList<>();
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
                            if (sourceField.getType().isPrimitive() || sourceField.getType().equals(Integer.class) || sourceField.getType().equals(String.class) || sourceField.getType().equals(Long.class) || sourceField.getType().equals(Float.class) || sourceField.getType().equals(Byte.class) || sourceField.getType().equals(BigDecimal.class)) {
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
                    retObject = getListValues(sourceList, newSource, name, isInherited);
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

    private Object getFieldValue(Field field, Object source, Field targetObjectField) throws IllegalAccessException {
        Object value = null;
        if (field == null) {
            logger.severe("Could not find associated field for " + targetObjectField.getName() + " field from source object " + source);
            return null;
        }
        field.setAccessible(true);
        if (field.getType().isEnum()) {
            if (targetObjectField.getType().isEnum()) {
                value = field.get(source);
            } else if (targetObjectField.getType().isAssignableFrom(String.class)) {
                value = field.get(source).toString();
            }
        } else {
            value = field.get(source);
        }
        if (value == null) {
            logger.severe("Source entity " + source.getClass().getTypeName() + " has null value on field : '" + field.getName() + '\'' + " therefore we ignore @MapAttribute core on field '" + targetObjectField.getName() + "\'");
        }
        return value;
    }
}
