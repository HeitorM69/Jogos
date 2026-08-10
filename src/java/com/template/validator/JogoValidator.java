package com.template.validator;

import com.template.model.dto.JogoDTO;

public class JogoValidator implements Validador<JogoDTO> {

    @Override
    public boolean validar(JogoDTO jogo) {
        // Valida se os campos estão vazios ou nulos
        if (jogo.getNome() == null || jogo.getNome().trim().isEmpty() ||
                jogo.getTipo() == null || jogo.getTipo().trim().isEmpty() ||
                jogo.getVersao() == null || jogo.getVersao().trim().isEmpty()) {
            return false;
        }
        return true;
    }
}