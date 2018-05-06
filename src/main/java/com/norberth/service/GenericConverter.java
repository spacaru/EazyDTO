package com.norberth.service;

import com.norberth.annotation.TransferObject;
import com.norberth.annotation.TransferObjectAttribute;
import com.norberth.config.ConverterConfig;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;
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
                    setFieldValues(c.getDeclaredFields(), source, t);
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
     * Sets values for list of {@link Field} annotated with {@link TransferObjectAttribute} on classes annotated with {@link TransferObject}
     *
     * @param fields
     * @param source
     * @param target
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void setFieldValues(Field[] fields, Object source, Object target) throws NoSuchFieldException, IllegalAccessException {

        for (Field f : fields) {
            if (f.getAnnotation(TransferObjectAttribute.class) != null) {
                String sourceField = f.getAnnotation(TransferObjectAttribute.class).sourceField();
                boolean inheritedField = f.getAnnotation(TransferObjectAttribute.class).inheritedField();
                String[] concatFields = f.getAnnotation(TransferObjectAttribute.class).concatFields();
                String separator = f.getAnnotation(TransferObjectAttribute.class).separator();
                Field field = null;
                Field targetObjectField = null;
                Object value = null;
                if (sourceField.contains(ConverterConfig.getNameDelimiter())) {
                    String[] split = sourceField.split(ConverterConfig.getEscapedNameDelimiter());
                    while (split.length > 0) {
                        field = getField(split, source, split[split.length - 1]);
                    }
                    Field childField = source.getClass().getDeclaredField(split[0]);
                    childField.setAccessible(true);
                    Object val = childField.get(source);
                    value = getFieldValue(field, val);
                } else if (!inheritedField) {
                    field = source.getClass().getDeclaredField(sourceField);
                } else {
                    field = source.getClass().getSuperclass().getDeclaredField(sourceField);
                }
                if (concatFields.length > 0 && !concatFields[0].isEmpty()) {
                    concatFieldsAndSetValue(source, target, f, sourceField, concatFields, value, separator);
                } else {
//                set fields as accesible
                    field.setAccessible(true);
                    targetObjectField = target.getClass().getDeclaredField(f.getName());
                    targetObjectField.setAccessible(true);

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
        if (GenericConverterManager.isDebug()) {
            logger.info("Setting target field '" + targetObjectField.getName() + "' value : " + value);
        }
        targetObjectField.set(target, value);
    }

    private Field getField(String[] split, Object source, String name) throws NoSuchFieldException {
        Field f = null;
        Field parent = null;
        for (String s : split) {
            if (parent != null) {
                f = parent.getType().getDeclaredField(s);
                if (name.equals(f.getName())) {
                    return f;
                }
            }
            parent = source.getClass().getDeclaredField(s);
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
