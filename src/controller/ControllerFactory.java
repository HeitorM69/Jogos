package controller;

import javafx.util.Callback;
import validation.IJogoValidador;

public class ControllerFactory implements Callback<Class<?>, Object> {
    
    private IJogoValidador jogoValidador;

    public ControllerFactory(IJogoValidador jogoValidador) {
        this.jogoValidador = jogoValidador;
    }

    @Override
    public Object call(Class<?> param) {
        if (param == JogoController.class) {
            return new JogoController(jogoValidador);
        }
        try {
            return param.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
