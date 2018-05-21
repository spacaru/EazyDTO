package com.norberth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapList {

    /**
     * Source field (mandatory)
     *
     * @return
     */
    public String value();

    /**
     * Set this flag to true when mappedField is inherited from superclass
     *
     * @return
     */
    public boolean inheritedField() default false;
}
