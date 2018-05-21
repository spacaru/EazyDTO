package com.norberth.service;

import com.norberth.annotation.*;
import com.norberth.config.ConverterConfig;
import com.norberth.factory.GenericConverterFactory;
import com.norberth.util.ObjectComparator;
import com.norberth.validator.Action;
import com.norberth.validator.ObjectMapper;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(MapObject.class);
        Object t = null;
        for (Class c : annotatedClasses) {
            if (c.equals(type)) {
                try {
                    t = type.newInstance();
                    Annotation transferObject = c.getAnnotation(MapObject.class);
                    setFieldValues(c.getDeclaredFields(), source, t, ((MapObject) transferObject).hasIdField());
                    setFieldValues(c.getSuperclass().getDeclaredFields(), source, t, ((MapObject) transferObject).hasIdField());
                    Class customCodeClass = ((MapObject) transferObject).customCodeClass();
                    if (customCodeClass.getGenericInterfaces().length > 0) {
                        if (customCodeClass.getGenericInterfaces()[0].getTypeName().contains(CustomMapper.class.getTypeName())) {
                            Class[] argTypes = new Class[]{Object.class, Object.class};
                            Method m = customCodeClass.getMethod("postMap", argTypes);
                            Object[] objArray = new Object[2];
                            objArray[0] = source;
                            objArray[1] = t;

                            t = m.invoke(customCodeClass.newInstance(), objArray);
                        } else {

                            logger.severe("Annotated class must implement CustomMapper<Source S,Target T> interface!");
                            logger.severe("Omitting postMap() method from class " + customCodeClass.getTypeName());

                        }
                    }
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
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        if (t == null && GenericConverterFactory.isDebug()) {
            logger.log(Level.WARNING, " No class of type" + type + " found annotated with @MapObject annotation.");
        }
        if (GenericConverterFactory.isDebug()) {
            logger.log(Level.INFO, "Created object => " + t.toString());
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
     * Sets values for list of {@link Field} annotated with {@link MapAttribute} on classes annotated with {@link MapObject}
     *
     * @param fields
     * @param source
     * @param target
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void setFieldValues(Field[] fields, Object source, Object target, boolean hasId) throws NoSuchFieldException, IllegalAccessException {

        Object currentSource = null;
        ObjectMapper objectMapper = new ObjectMapper();

        for (Field f : fields) {
            if (f.getAnnotation(MapAttribute.class) != null) {
                String sourceField = f.getAnnotation(MapAttribute.class).value();
                Action action = objectMapper.getAction(sourceField);
                Field field = objectMapper.getField(action, source, sourceField);
                currentSource = objectMapper.getSource(action, source, sourceField, false);
                Field targetObjectField = objectMapper.getTargetObjectField(action, target, f.getName());
                Object value = objectMapper.getValue(field, currentSource, false, null, false, targetObjectField);
                if (field != null) {
                    field.setAccessible(true);
                }
                targetObjectField.setAccessible(true);
                targetObjectField.set(target, value);
            } else if (f.getAnnotation(MapList.class) != null) {
                String sourceField = f.getAnnotation(MapList.class).value();
                boolean isInherited = f.getAnnotation(MapList.class).inheritedField();
                Action action = objectMapper.getAction(sourceField);
                Field field = objectMapper.getField(action, source, sourceField);
                Field targetObjectField = objectMapper.getTargetObjectField(action, target, f.getName());
                Object value = objectMapper.getValue(field, source, true, sourceField, isInherited, targetObjectField);
                if (field != null) {
                    field.setAccessible(true);
                }
                targetObjectField.setAccessible(true);
                targetObjectField.set(target, value);
            } else if (f.getAnnotation(ConcatAttributes.class) != null) {
                String[] concatFields = f.getAnnotation(ConcatAttributes.class).concatFields();
                String separator = f.getAnnotation(ConcatAttributes.class).separator();
                Action action = objectMapper.getAction(concatFields);
                for (String sourceField : concatFields) {
                    Field field = objectMapper.getField(action, source, sourceField);
                    Object value = objectMapper.getValue(field, source, false, sourceField, false, null);
                    concatFieldsAndSetValue(source, target, f, sourceField, concatFields, value, separator);
                }
            }
        }

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
        if (GenericConverterFactory.isDebug()) {
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
