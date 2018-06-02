package com.norberth.core;

import com.norberth.config.ConverterConfig;
import com.norberth.core.annotation.MapAttribute;
import com.norberth.core.annotation.MapList;
import com.norberth.core.annotation.MapObject;
import com.norberth.core.database.DatabaseWrapper;
import com.norberth.core.database.SqlType;
import com.norberth.core.service.AttributeAccesorType;
import com.norberth.core.service.ObjectReflectiveMapper;
import com.norberth.event.CustomEvent;
import com.norberth.util.ObjectComparator;
import com.norberth.util.SortationType;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.persistence.EntityManager;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Novanc Norberth
 * @version 1.0.0
 */
public class DTOMapper implements Mapper {

    private final Logger logger = Logger.getLogger(DTOMapper.class.getSimpleName());
    private final Class type;
    private final String packageName;
    private final boolean isDebug;
    private DatabaseWrapper databaseWrapper;
    private EntityManager em;

    public DTOMapper(Class type, String packageName, boolean isDebug) {
        this.type = type;
        this.packageName = packageName;
        this.isDebug = isDebug;
    }


    /**
     * Returns transfer object from source object
     *
     * @param source
     * @return newly created transfer object
     */
    @Override
    public Object getTo(Object source) {
        Collection<URL> urls = ClasspathHelper.forPackage(this.packageName);
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(urls)
                .setScanners(new SubTypesScanner(),
                        new TypeAnnotationsScanner()));
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(MapObject.class);
        Object t = null;
        for (Class c : annotatedClasses) {
            if (c.equals(type)) {
                try {
                    t = type.newInstance();
                    Annotation transferObject = c.getAnnotation(MapObject.class);
                    setFieldValues(c.getDeclaredFields(), source, t);
                    setFieldValues(c.getSuperclass().getDeclaredFields(), source, t);
                    Class customCodeClass = ((MapObject) transferObject).customMapperClass();
                    if (customCodeClass.getGenericInterfaces().length > 0) {
                        if (customCodeClass.getGenericInterfaces()[0].getTypeName().contains(CustomEvent.class.getTypeName())) {
                            Class[] argTypes = new Class[]{Object.class, Object.class};
                            Method m = customCodeClass.getMethod("postMap", argTypes);
                            Object[] objArray = new Object[2];
                            objArray[0] = source;
                            objArray[1] = t;

                            t = m.invoke(customCodeClass.newInstance(), objArray);
                        } else {
                            logger.severe("Annotated class must implement CustomEvent<Source S,Target T> interface!");
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
        if (t == null && isDebug) {
            logger.log(Level.WARNING, " No class of type" + type + " found annotated with @MapObject core.");
        }
        if (isDebug) {
            logger.log(Level.INFO, "Created object => " + t);
        }
        return t;
    }

    /**
     * @param sourceList
     * @return
     */
    @Override
    public List<Object> getToList(List sourceList) {
        List<Object> retList = new ArrayList<>();
        for (Object sourceObj : sourceList) {
            retList.add(getTo(sourceObj));
        }
        retList.removeAll(Collections.singleton(null));
        return retList;
    }


    public List getToListFromSql(SqlType sqlType, String sql) {

        Collection<URL> urls = ClasspathHelper.forPackage(this.packageName);
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(urls)
                .setScanners(new SubTypesScanner(),
                        new TypeAnnotationsScanner()));
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(MapObject.class);
        List retList = new ArrayList();
        for (Class c : annotatedClasses) {
            if (c.equals(type)) {
                if (this.em == null) {
                    logger.log(Level.SEVERE, "Entity manager not configured! Make sure to use the setEm() method to add an EntityManager to MapperFactory");
                    return retList;
                } else {
                    databaseWrapper = new DatabaseWrapper(em);
                }
                Annotation transferObject = c.getAnnotation(MapObject.class);
                Class targetClass = ((MapObject) transferObject).fromClass();
                List entities = databaseWrapper.getObjectList(sql, targetClass, sqlType);
                logger.log(Level.INFO, "Found " + entities.size() + " entities !");
                if (entities.size() == 0) {
                    return retList;
                } else {
                    retList = getToList(entities);
                }
            }

        }
        return retList;

    }

    @Override
    public Object getToFromSql() {
        return null;
    }

    @Override
    public List<Object> getToListSortBy(List sourceList, String attribute) {
        return getToListSortBy(sourceList, attribute, null);
    }


    /**
     * @param sourceList
     * @param attribute
     * @return
     */
    @Override
    public List<Object> getToListSortBy(List sourceList, String attribute, SortationType sortationType) {
        List<Object> retList = new ArrayList<>();
        for (Object sourceObj : sourceList) {
            retList.add(getTo(sourceObj));
        }
        retList.removeAll(Collections.singleton(null));
        if (sortationType == null) {
            sortationType = SortationType.ASCENDING;
        }
        Collections.sort(retList, new ObjectComparator(attribute, sortationType));
        return retList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DTOMapper converter = (DTOMapper) o;
        return Objects.equals(type, converter.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type);
    }

    @Override
    public Class getType() {
        return type;
    }


    @Override
    public EntityManager getEm() {
        return em;
    }

    @Override
    public void setEm(EntityManager em) {
        this.em = em;
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
    private Object setFieldValues(Field[] fields, Object source, Object target) throws NoSuchFieldException, IllegalAccessException {

        Object currentSource = null;
        ObjectReflectiveMapper objectMapper = new ObjectReflectiveMapper();
        Object oldTarget = target;
        for (Field f : fields) {
            if (f.getAnnotation(MapAttribute.class) != null) {
                String sourceField = f.getAnnotation(MapAttribute.class).value();
                AttributeAccesorType attributeAccesorType = objectMapper.getAction(sourceField);
                Field field = objectMapper.getField(attributeAccesorType, source, sourceField);
                currentSource = objectMapper.getSource(attributeAccesorType, source, sourceField, false);
                Field targetObjectField = objectMapper.getTargetObjectField(attributeAccesorType, target, f.getName());
                Object value = objectMapper.getValue(field, currentSource, false, null, false, targetObjectField);
                if (field != null) {
                    field.setAccessible(true);
                }
                targetObjectField.setAccessible(true);
                targetObjectField.set(target, value);
            } else if (f.getAnnotation(MapList.class) != null) {
                String sourceField = f.getAnnotation(MapList.class).value();
                boolean isInherited = f.getAnnotation(MapList.class).inheritedField();
                AttributeAccesorType attributeAccesorType = objectMapper.getAction(sourceField);
                Field field = objectMapper.getField(attributeAccesorType, source, sourceField);
                Field targetObjectField = objectMapper.getTargetObjectField(attributeAccesorType, target, f.getName());
                Object value = objectMapper.getValue(field, source, true, sourceField, isInherited, targetObjectField);
                if (field != null) {
                    field.setAccessible(true);
                }
                targetObjectField.setAccessible(true);
                targetObjectField.set(target, value);
            } else if (f.getAnnotation(MapObject.class) != null) {
                Annotation mapObject = f.getAnnotation(MapObject.class);
                Class c = ((MapObject) mapObject).fromClass();
                String sourceField = ((MapObject) mapObject).value();
                try {
                    Field toField = oldTarget.getClass().getDeclaredField(f.getName());
                    if (f.getType().equals(List.class)) {
//                        target = new ArrayList<>();
                        ParameterizedType integerListType = (ParameterizedType) f.getGenericType();
                        Class<?> integerListClass = (Class<?>) integerListType.getActualTypeArguments()[0];
                        Field targetFieldInEntity = source.getClass().getDeclaredField(sourceField);
                        targetFieldInEntity.setAccessible(true);
                        List sourceList = (List) targetFieldInEntity.get(source);
                        target = new ArrayList();
                        if (sourceList != null) {
                            for (Object element : sourceList) {
                                Object targetObjectInList = integerListClass.newInstance();
                                Object newElement = setFieldValues(integerListClass.getDeclaredFields(), element, targetObjectInList);
                                ((ArrayList) target).add(newElement);
                            }
                            toField.setAccessible(true);
                            toField.set(oldTarget, target);
                        }
                    } else {

                        Field targetFieldInEntity = source.getClass().getDeclaredField(sourceField);
                        targetFieldInEntity.setAccessible(true);
                        source = targetFieldInEntity.get(source);
                        if (source != null) {
                            Object targetObjectInList = f.getType().newInstance();
                            Object newElement = setFieldValues(targetObjectInList.getClass().getDeclaredFields(), source, targetObjectInList);
                            toField.setAccessible(true);
                            toField.set(target, newElement);
                        }
                    }

                    target = oldTarget;

                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return target;
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
        if (isDebug) {
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
