package com.template.controller;

import com.template.service.IJogoService;
import com.template.validator.IJogoValidador;
import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {

    private final IJogoService jogoService;
    private final IJogoValidador jogoValidador;

    public ControllerFactory(
            IJogoService jogoService,
            IJogoValidador jogoValidador
    ) {
        this.jogoService = jogoService;
        this.jogoValidador = jogoValidador;
    }

    @Override
    public Object call(Class<?> controllerClass) {

        if (controllerClass == JogoController.class) {
            return new JogoController(
                    jogoService,
                    jogoValidador
            );
        }

        try {
            return controllerClass
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(
                    "Erro ao criar controller.",
                    e
            );
        }
    }
}
