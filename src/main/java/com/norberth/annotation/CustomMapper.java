package com.norberth.annotation;

public interface CustomMapper<S,T> {

    T postProcess(S sourceEntity, T transferObject);
}
