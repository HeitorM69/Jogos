package com.template.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsável exclusivamente pela conexão com o banco de dados.
 */
public class Conexao {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/jogo";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    private static final Logger LOGGER =
            Logger.getLogger(Conexao.class.getName());

    public Connection conectarBD() {
        try {
            return DriverManager.getConnection(
                    URL,
                    USUARIO,
                    SENHA
            );
        } catch (SQLException e) {
            LOGGER.log(
                    Level.SEVERE,
                    "Erro ao conectar ao banco de dados.",
                    e
            );
            throw new RuntimeException(
                    "Falha na conexão com o banco de dados.",
                    e
            );
        }
    }
}
