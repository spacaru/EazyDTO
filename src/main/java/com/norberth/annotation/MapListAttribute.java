package com.norberth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapListAttribute {

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
