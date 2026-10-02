package com.template.model.dao;

import com.template.model.Conexao;
import com.template.model.dto.JogoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JogoDAO {

    private static final Logger LOGGER =
            Logger.getLogger(JogoDAO.class.getName());

    public void cadastrarJogo(JogoDTO jogo) {
        String sql =
                "INSERT INTO jogo (nome, tipo, versao) VALUES (?, ?, ?)";

        try (
                Connection conn = new Conexao().conectarBD();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ) {
            pstm.setString(1, jogo.getNome());
            pstm.setString(2, jogo.getTipo());
            pstm.setString(3, jogo.getVersao());
            pstm.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao cadastrar jogo.", e);
            throw new RuntimeException("Erro ao cadastrar jogo.", e);
        }
    }

    public List<JogoDTO> listarJogos() {
        String sql =
                "SELECT id, nome, tipo, versao FROM jogo ORDER BY id";
        List<JogoDTO> lista = new ArrayList<>();

        try (
                Connection conn = new Conexao().conectarBD();
                PreparedStatement pstm = conn.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()
        ) {
            while (rs.next()) {
                JogoDTO jogo = new JogoDTO();
                jogo.setId(rs.getInt("id"));
                jogo.setNome(rs.getString("nome"));
                jogo.setTipo(rs.getString("tipo"));
                jogo.setVersao(rs.getString("versao"));
                lista.add(jogo);
            }
            return lista;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao listar jogos.", e);
            throw new RuntimeException("Erro ao listar jogos.", e);
        }
    }

    public void alterarJogo(JogoDTO jogo) {
        String sql =
                "UPDATE jogo SET nome = ?, tipo = ?, versao = ? WHERE id = ?";

        try (
                Connection conn = new Conexao().conectarBD();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ) {
            pstm.setString(1, jogo.getNome());
            pstm.setString(2, jogo.getTipo());
            pstm.setString(3, jogo.getVersao());
            pstm.setInt(4, jogo.getId());

            int linhasAfetadas = pstm.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new RuntimeException("Jogo não encontrado.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao alterar jogo.", e);
            throw new RuntimeException("Erro ao alterar jogo.", e);
        }
    }

    public void excluirJogo(int id) {
        String sql = "DELETE FROM jogo WHERE id = ?";

        try (
                Connection conn = new Conexao().conectarBD();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ) {
            pstm.setInt(1, id);

            int linhasAfetadas = pstm.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new RuntimeException("Jogo não encontrado.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao excluir jogo.", e);
            throw new RuntimeException("Erro ao excluir jogo.", e);
        }
    }
}
