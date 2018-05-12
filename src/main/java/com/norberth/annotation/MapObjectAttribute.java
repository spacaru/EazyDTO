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
public @interface MapObjectAttribute {

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

    /**
     * List of additional fields to be concatenated to sourceField
     * Source field must be of type String , otherwise concatenation is not possible
     *
     * @return
     */
    String[] concatFields() default "";

    /**
     * Field separator for field concatenation
     *
     * @return
     */
    String separator() default " ";
}
