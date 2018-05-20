package com.norberth.annotation;

public @interface ConcatAttributes {

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
