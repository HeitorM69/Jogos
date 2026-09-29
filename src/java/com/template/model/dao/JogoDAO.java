package com.template.model.dao;
import com.template.model.Conexao;
import com.template.model.dto.JogoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import static com.template.util.DialogUtil.showError;
public class JogoDAO {
    private Connection conn;
    private static final Logger logger = Logger.getLogger(JogoDAO.class.getName());
    public void cadastrarJogo(JogoDTO objJogoDTO) {
        String sql = "INSERT INTO jogo (nome, tipo, versao) VALUES (?, ?, ?)";
        this.conn = new Conexao().conectarBD();
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, objJogoDTO.getNome());
            pstm.setString(2, objJogoDTO.getTipo());
            pstm.setString(3, objJogoDTO.getVersao());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao cadastrar jogo", erro);
            showError("Erro ao cadastrar jogo");
        }
    }
    public ArrayList<JogoDTO> listarJogos() {
        String sql = "SELECT * FROM jogo";
        ArrayList<JogoDTO> lista = new ArrayList<>();
        this.conn = new Conexao().conectarBD();
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                JogoDTO jogo = new JogoDTO();
                jogo.setId(rs.getInt("id"));
                jogo.setNome(rs.getString("nome"));
                jogo.setTipo(rs.getString("tipo"));
                jogo.setVersao(rs.getString("versao"));
                lista.add(jogo);
            }
            pstm.close();
        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao listar jogo", erro);
            showError("Erro ao listar jogo");
        }
        return lista;
    }
    public void alterarJogo(JogoDTO objJogoDTO) {
        String sql = "UPDATE jogo SET nome = ?, tipo = ?, versao = ? WHERE id = ?";
        this.conn = new Conexao().conectarBD();
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, objJogoDTO.getNome());
            pstm.setString(2, objJogoDTO.getTipo());
            pstm.setString(3, objJogoDTO.getVersao());
            pstm.setInt(4, objJogoDTO.getId());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao alterar jogo", erro);
            showError("Erro ao alterar jogo");
        }
    }
    public void excluirJogo(int id) {
        String sql = "DELETE FROM jogo WHERE id = ?";
        this.conn = new Conexao().conectarBD();
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao excluir jogo", erro);
            showError("Erro ao excluir jogo");
        }
    }
}