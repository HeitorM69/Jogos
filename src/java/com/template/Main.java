package com.template;

import com.template.controller.ControllerFactory;
import com.template.model.dao.JogoDAO;
import com.template.service.IJogoService;
import com.template.service.JogoService;
import com.template.validator.IJogoValidador;
import com.template.validator.JogoValidador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        JogoDAO jogoDAO = new JogoDAO();
        IJogoService jogoService = new JogoService(jogoDAO);
        IJogoValidador jogoValidador = new JogoValidador();

        ControllerFactory factory = new ControllerFactory(
                jogoService,
                jogoValidador
        );

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/template/main.fxml")
        );

        loader.setControllerFactory(factory);

        Parent root = loader.load();

        Scene scene = new Scene(root, 680, 620);

        primaryStage.setTitle("Biblioteca de Jogos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
