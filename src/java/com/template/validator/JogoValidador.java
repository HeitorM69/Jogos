package com.template.validator;

import java.util.ArrayList;
import java.util.List;
import static com.template.util.DialogUtil.showError;

public class JogoValidador implements IJogoValidador {

    @Override
    public boolean validarJogo(
            String nome,
            String tipo,
            String versao
    ) {
        List<Validador<String>> validadores = new ArrayList<>();

        validadores.add(
                new CamposObrigatoriosValidador(
                        "Nome do Jogo",
                        nome
                )
        );

        validadores.add(
                new CamposObrigatoriosValidador(
                        "Tipo / Gênero",
                        tipo
                )
        );

        validadores.add(
                new CamposObrigatoriosValidador(
                        "Versão",
                        versao
                )
        );
        validadores.add(
                new LetraValidador(
                        versao
                )
        );


        for (Validador<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                showError(validador.getMensagemErro());
                return false;
            }
        }

        return true;
    }
}
