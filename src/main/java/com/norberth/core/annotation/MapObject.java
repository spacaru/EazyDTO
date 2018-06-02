package com.norberth.core.annotation;

import com.norberth.event.CustomEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that must be used on transfer object classes
 *
 * @author Novanc Norberth
 * @version 1.0.0
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MapObject {

    /**
     * Entity class to which this transfer object maps
     *
     * @return source entity class
     */
    Class fromClass();

    /**
     * SQL to run ( MapperFactory must have an entityManager set for this feature to work )
     *
     * @return
     */
    String fromSql() default "";

    /**
     * Specify a custom class which implements CustomEvent<Source,Target> interface
     * to run custom code after the DTO has been mapped
     *
     * @return
     */
    Class customMapperClass() default CustomEvent.class;

    /**
     * Target entity field
     *
     * @return
     */
    String value() default "";
}
