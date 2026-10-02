package com.template.validator;

/**
 * Define a validação dos dados necessários para um jogo.
 */
public interface IJogoValidador {
    boolean validarJogo(
            String nome,
            String tipo,
            String versao
    );
}
