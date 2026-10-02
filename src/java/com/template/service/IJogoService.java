package com.template.service;

import com.template.model.dto.JogoDTO;
import java.util.List;

/**
 * Define as operações disponíveis para o gerenciamento de jogos.
 */
public interface IJogoService {
    void cadastrarJogo(JogoDTO jogo);
    void alterarJogo(JogoDTO jogo);
    void excluirJogo(int id);
    List<JogoDTO> listarJogos();
}
