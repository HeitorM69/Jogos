package com.template.validator;

/**
 * Contrato genérico utilizado pelas validações do sistema.
 *
 * @param <T> tipo do valor validado
 */
public interface Validador<T> {
    boolean validar(T valorAtual);
    String getMensagemErro();
    T getValor();
}
