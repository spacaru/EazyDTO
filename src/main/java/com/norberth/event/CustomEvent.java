package com.norberth.event;

/**
 * @param <S> source entity
 * @param <T> target transfer object class
 * @author Novanc Norberth
 * @since 1.0.0
 */
public interface CustomEvent<S, T> {

    /**
     * Custom implementation should be in this method
     *
     * @param sourceEntity   source entity that is mapped
     * @param transferObject target transfer object entity that is mapping the sourceEntity
     * @return return the modified transferObject
     */
    T postMap(S sourceEntity, T transferObject);
}
