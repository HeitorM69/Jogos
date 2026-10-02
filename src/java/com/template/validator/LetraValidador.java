package com.template.validator;

public class LetraValidador implements Validador<String> {

    private final String valor;

    public LetraValidador(
            String valor
    ) {
        this.valor = valor;
    }

    @Override
    public boolean validar(
            String valorAtual
    ) {

        return valorAtual != null
                && !valorAtual.trim().isEmpty()
                && valorAtual.matches("^\\d+(\\.\\d+){0,2}$");
    }

    @Override
    public String getMensagemErro() {

        return "O campo 'Versão' deve conter apenas números.";
    }

    @Override
    public String getValor() {

        return valor;
    }
}
