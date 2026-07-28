package com.template.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Responsável por gerenciar a conexão com o banco de dados.
 */
public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/jogo";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    public Connection conectarBD() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.err.println("Erro ao tentar conectar com o banco de dados: " + e.getMessage());
            throw new RuntimeException("Falha na conexão com o banco de dados.", e);
        }
    }
}