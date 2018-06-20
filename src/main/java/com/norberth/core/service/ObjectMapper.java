package com.norberth.core.service;

import com.norberth.config.ConverterConfig;

import java.lang.reflect.Field;
import java.util.*;
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
                resourceSharingService.setIsInherited(true);
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
    public Object getValue(Field field, Object source, boolean isListField, String sourceField, boolean isInherited, Field targetObjectField, boolean isNotCustomDTO) {
        Object value = null;
        try {
            if (!isListField) {
                value = getFieldValue(field, source, targetObjectField);
            } else {
                String[] split = sourceField.split(ConverterConfig.getEscapedNameDelimiter());
                value = getListValues(new ArrayList<String>(Arrays.asList(split)), source, split[split.length - 1], isInherited, isNotCustomDTO);
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

    private Object getListValues(ArrayList<String> sourceList, Object source, String name, boolean isInherited, boolean isStringMapping) {
        ListIterator<String> stringListIterator = sourceList.listIterator();
        Object retObject = null;
        Field f = null;
        try {
            while (stringListIterator.hasNext()) {
                String currentField = stringListIterator.next();
                stringListIterator.remove();
                if (!stringListIterator.hasNext()) {
                    if (isStringMapping) {
                        Object retList = null;
                        if (source instanceof List) {
                            source = (List) source;
                            retList = new ArrayList<>();
                            for (Object obj : (List) source) {
                                Field targetField = obj.getClass().getDeclaredField(name);
                                targetField.setAccessible(true);
                                if (source instanceof List) {
                                    addObjectsInList((List) source, currentField, (List) retList);
                                }
                                return retList;
                            }
                        } else if (source instanceof Set) {
                            retList = new HashSet();

                            for (Object obj : (Set) source) {
                                Field targetField = obj.getClass().getDeclaredField(name);
                                targetField.setAccessible(true);
                                if (source instanceof List) {
                                    addObjectsInList((List) source, currentField, (List) retList);
                                } else {
                                    addObjectsInSet((Set) source, currentField, (Set) retList);
                                }
                                return retList;
                            }
                        }

                    } else {

                        if (source instanceof List) {
                            List<Object> newList = new ArrayList<>();
                            addObjectsInList((List) source, name, newList);
                            return newList;
                        }
                    }
                } else {
                    Field targetField = source.getClass().getDeclaredField(currentField);
                    targetField.setAccessible(true);
                    Object newSource = targetField.get(source);
                    return getListValues(sourceList, newSource, name, isInherited, isStringMapping);
                }

            }
        } catch (NoSuchFieldException e) {
//            this.error = true;
            return f;
        } catch (IllegalAccessException e) {
            return f;
        }
        return f;
    }

    private void addObjectsInSet(Set source, String name, Set retList) throws NoSuchFieldException, IllegalAccessException {
        for (Object obj : source) {
            Field targetFieldInObject = obj.getClass().getDeclaredField(name);
            targetFieldInObject.setAccessible(true);
            Object value = targetFieldInObject.get(obj);
            retList.add(value);
        }
    }

    private void addObjectsInList(List source, String name, List<Object> newList) throws NoSuchFieldException, IllegalAccessException {
        for (Object obj : source) {
            Field targetFieldInObject = obj.getClass().getDeclaredField(name);
            targetFieldInObject.setAccessible(true);
            Object value = targetFieldInObject.get(obj);
            newList.add(value);
        }
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
