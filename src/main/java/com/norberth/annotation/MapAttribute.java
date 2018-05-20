package com.norberth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Novanc Norberth
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapAttribute {

    /**
     * Source field (mandatory)
     *
     * @return
     */
    public String sourceField();


    /**
     * Set this flag to true when mappedField is inherited from superclass
     *
     * @return
     */
    public boolean inheritedField() default false;
}
