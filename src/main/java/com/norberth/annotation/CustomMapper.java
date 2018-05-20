package com.norberth.annotation;

public interface CustomMapper<S,T> {

    T postMap(S sourceEntity, T transferObject);
}
