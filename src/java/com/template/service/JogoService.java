package com.template.service;
import com.template.model.dao.JogoDAO;
import com.template.model.dto.JogoDTO;
import java.util.ArrayList;
public class JogoService implements IJogoService {
    private final JogoDAO dao;
    public JogoService() {
        this.dao = new JogoDAO();
    }
    @Override
    public void cadastrarJogo(JogoDTO jogo) {
        dao.cadastrarJogo(jogo);
    }
    @Override
    public void alterarJogo(JogoDTO jogo) {
        dao.alterarJogo(jogo);
    }
    @Override
    public void excluirJogo(int id) {
        dao.excluirJogo(id);
    }
    @Override
    public ArrayList<JogoDTO> listarJogos() {
        return dao.listarJogos();
    }
}