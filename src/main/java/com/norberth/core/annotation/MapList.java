package com.norberth.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Novanc Norberth
 * @version 1.0.0
 * MapList - core that should be used to map list of custom transfer objects from list of entities
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapList {

    /**
     * Source field (mandatory)
     *
     * @return
     */
    String value();

    /**
     * Set this flag to true when mappedField is inherited from superclass
     *
     * @return
     */
    boolean inheritedField() default false;
}
