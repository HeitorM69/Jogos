package com.template.service;

import com.template.model.dao.JogoDAO;
import com.template.model.dto.JogoDTO;
import java.util.List;

public class JogoService implements IJogoService {

    private final JogoDAO jogoDAO;

    public JogoService(JogoDAO jogoDAO) {
        this.jogoDAO = jogoDAO;
    }

    @Override
    public void cadastrarJogo(JogoDTO jogo) {
        jogoDAO.cadastrarJogo(jogo);
    }

    @Override
    public void alterarJogo(JogoDTO jogo) {
        jogoDAO.alterarJogo(jogo);
    }

    @Override
    public void excluirJogo(int id) {
        jogoDAO.excluirJogo(id);
    }

    @Override
    public List<JogoDTO> listarJogos() {
        return jogoDAO.listarJogos();
    }
}
