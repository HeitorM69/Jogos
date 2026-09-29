package com.template.service;
import com.template.model.dto.JogoDTO;
import java.util.ArrayList;
public interface IJogoService {
    void cadastrarJogo(JogoDTO jogo);
    void alterarJogo(JogoDTO jogo);
    void excluirJogo(int id);
    ArrayList<JogoDTO> listarJogos();
}