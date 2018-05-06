package com.norberth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Novanc Norberth
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TransferObject {

    /**
     * Entity class to which this dto maps
     *
     * @return - source entity class
     */
    public Class sourceClass();
}
