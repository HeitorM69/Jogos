package com.template.controller;
import com.template.validator.IJogoValidador;
import javafx.util.Callback;
public class ControllerFactory implements Callback<Class<?>, Object> {
    private final IJogoValidador validador;
    public ControllerFactory(IJogoValidador validador) {
        this.validador = validador;
    }
    @Override
    public Object call(Class<?> type) {
        if (type == JogoController.class) {
            return new JogoController(validador);
        }
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}