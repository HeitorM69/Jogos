package com.template.validator;

public interface Validador<T> {
    boolean validar(T objeto);
}