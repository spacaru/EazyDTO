package com.norberth.service;

import com.norberth.annotation.MapListProperty;
import com.norberth.annotation.TransferObject;
import com.norberth.annotation.TransferObjectAttribute;
import com.norberth.config.ConverterConfig;
import com.norberth.util.ObjectComparator;
import com.norberth.validator.Action;
import com.norberth.validator.SourceFieldValidator;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Novanc Norberth
 * @version 1.0.0
 */
public class GenericConverter {

    private Logger logger = Logger.getLogger(GenericConverter.class.getSimpleName());
    private final Class type;
    private String packageName;
    private boolean error = false;

    public GenericConverter(Class type, String packageName) {
        this.type = type;
        this.packageName = packageName;
    }

    /**
     * Returns transfer object from source object
     *
     * @param source
     * @return - newly created transfer object
     */
    public Object getTo(Object source) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(this.packageName))
                .setScanners(new SubTypesScanner(),
                        new TypeAnnotationsScanner()));
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(TransferObject.class);
        Object t = null;
        for (Class c : annotatedClasses) {
            if (c.equals(type)) {
                try {
                    t = type.newInstance();
                    Annotation transferObject = c.getAnnotation(TransferObject.class);
                    setFieldValues(c.getDeclaredFields(), source, t, ((TransferObject) transferObject).hasIdField());
                } catch (InstantiationException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                    e.printStackTrace();
                    continue;
                }
            }
        }
        if (t == null && GenericConverterManager.isDebug()) {
            logger.log(Level.WARNING, " No class of type" + type + " found annotated with @TransferObject annotation.");
        }
        return t;
    }

    /**
     * @param sourceList
     * @return
     */
    public List<Object> getToList(List sourceList) {
        List<Object> retList = new ArrayList<>();
        for (Object sourceObj : sourceList) {
            retList.add(getTo(sourceObj));
        }
        retList.removeAll(Collections.singleton(null));
        return retList;
    }


    /**
     * @param sourceList
     * @param attribute
     * @return
     */
    public List<Object> getToListSortBy(List sourceList, String attribute) {
        List<Object> retList = new ArrayList<>();
        for (Object sourceObj : sourceList) {
            retList.add(getTo(sourceObj));
        }
        retList.removeAll(Collections.singleton(null));
        Collections.sort(retList, new ObjectComparator(attribute));
        return retList;
    }


    /**
     * Sets values for list of {@link Field} annotated with {@link TransferObjectAttribute} on classes annotated with {@link TransferObject}
     *
     * @param fields
     * @param source
     * @param target
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void setFieldValues(Field[] fields, Object source, Object target, boolean hasId) throws NoSuchFieldException, IllegalAccessException {

        Object currentSource = null;
        for (Field f : fields) {
            if (f.getAnnotation(TransferObjectAttribute.class) != null) {
                String sourceField = f.getAnnotation(TransferObjectAttribute.class).sourceField();
                boolean inheritedField = f.getAnnotation(TransferObjectAttribute.class).inheritedField();
                String[] concatFields = f.getAnnotation(TransferObjectAttribute.class).concatFields();
                String separator = f.getAnnotation(TransferObjectAttribute.class).separator();

                SourceFieldValidator sourceFieldValidator = new SourceFieldValidator();
                Action action = sourceFieldValidator.getAction(sourceField);
                Field field = null;
                if (inheritedField) {
                    field = source.getClass().getSuperclass().getDeclaredField(sourceField);
                }
                Field targetObjectField = null;
                Object value = null;
                switch (action) {
                    case SET_FIELDS:
                        field = source.getClass().getDeclaredField(sourceField);
                        targetObjectField = target.getClass().getDeclaredField(f.getName());
                        currentSource = source;
                        break;
                    case RECURSIVELY_SET_FIELDS:
                        String[] split = sourceField.split(ConverterConfig.getEscapedNameDelimiter());
                        field = getFieldResursively(new ArrayList<String>(Arrays.asList(split)), source, split[split.length - 1]);
                        if (f.getAnnotation(MapListProperty.class) != null) {
                            value = getListValues(new ArrayList<String>(Arrays.asList(split)), source, split[split.length - 1]);
                        }
                        if (field == null && value == null) {
                            if (GenericConverterManager.isDebug())
                                logger.severe("Field not found or empty in source class " + source.getClass().getSimpleName());
                            continue;
                        }
                        currentSource = getSourceRecursively(new ArrayList<>(Arrays.asList(split)), source);
                        targetObjectField = target.getClass().getDeclaredField(f.getName());
                        if (value == null) {
                            value = getFieldValue(field, currentSource);
                        }
                        break;
                }
                if (field != null) {
                    field.setAccessible(true);
                }
                targetObjectField.setAccessible(true);
                if (concatFields.length > 0 && !concatFields[0].isEmpty()) {
                    concatFieldsAndSetValue(currentSource, target, f, field.getName(), concatFields, value, separator);
                }
                if (!sourceField.contains(ConverterConfig.getNameDelimiter())) {
                    value = getFieldValue(field, source);
                }
                if (GenericConverterManager.isDebug()) {
                    logger.info("Setting target field '" + targetObjectField.getName() + "' value : " + value);
                }
                targetObjectField.set(target, value);
            }
        }

    }

    private Object getListValues(ArrayList<String> sourceList, Object source, String name) {
        ListIterator<String> stringListIterator = sourceList.listIterator();
        Field f = null;
        try {
            while (stringListIterator.hasNext()) {
                if (!error) {
                    String currentField = stringListIterator.next();
                    stringListIterator.remove();
                    if (!stringListIterator.hasNext()) {
                        if (source instanceof List) {
                            List newList = new ArrayList();
                            for (Object obj : (List) source) {
                                Object sourceObject = obj.getClass().newInstance();
                                Field sourceField = sourceObject.getClass().getDeclaredField(name);
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
                        retObject = getListValues(sourceList, newSource, name);
                        return retObject;
                    }
                } else {
                    return f;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericConverter converter = (GenericConverter) o;
        return Objects.equals(type, converter.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type);
    }

    public Class getType() {
        return type;
    }


    private Object getSourceRecursively(ArrayList<String> strings, Object source) {
        ListIterator<String> stringListIterator = strings.listIterator();
        Object sourceObject = null;
        try {
            while (stringListIterator.hasNext()) {
                if (!error) {
                    String currentField = stringListIterator.next();
                    stringListIterator.remove();
                    if (!stringListIterator.hasNext()) {
                        return source;

                    } else {
                        Field f = source.getClass().getDeclaredField(currentField);
                        f.setAccessible(true);
                        Object newSource = f.get(source);
                        sourceObject = getSourceRecursively(strings, newSource);
                    }
                } else {
                    return sourceObject;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sourceObject;
    }

    private Field getFieldResursively(List<String> sourceList, Object source, String name) {
        ListIterator<String> stringListIterator = sourceList.listIterator();
        Field f = null;
        try {
            while (stringListIterator.hasNext()) {
                if (!error) {
                    String currentField = stringListIterator.next();
                    stringListIterator.remove();
                    if (!stringListIterator.hasNext()) {
                        if (source != null) {
                            f = source.getClass().getDeclaredField(currentField);
                            this.error = false;
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
                } else {
                    return f;
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

    private void concatFieldsAndSetValue(Object source, Object target, Field f, String sourceField, String[] concatFields, Object value, String separator) throws NoSuchFieldException, IllegalAccessException {
        Field field;
        Field targetObjectField = target.getClass().getDeclaredField(f.getName());
        Field srcField = source.getClass().getDeclaredField(sourceField);
        Object sourceValue = getFieldValue(srcField, source);
        value = value != null ? value : "" + sourceValue;
        for (String str : concatFields) {
            field = source.getClass().getDeclaredField(str);
            field.setAccessible(true);
            targetObjectField.setAccessible(true);
            srcField.setAccessible(true);
            if (!sourceField.contains(".")) {
                Object currentValue = getFieldValue(field, source);
                separator = separator != null ? separator : ConverterConfig.getSEPARATOR();
                value += separator + currentValue;
            }
        }
        if (GenericConverterManager.isDebug()) {
            logger.info("Setting target field '" + targetObjectField.getName() + "' value : " + value);
        }
        targetObjectField.set(target, value);
    }


    private Object getFieldValue(Field field, Object source) throws IllegalAccessException {
        Object value = null;
        field.setAccessible(true);
        value = field.get(source);
        return value;
    }

}
