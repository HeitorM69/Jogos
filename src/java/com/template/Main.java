package com.template;

import com.template.controller.ControllerFactory;
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
        IJogoValidador validador = new JogoValidador();
        ControllerFactory factory = new ControllerFactory(validador);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/template/JogoView.fxml"));
        loader.setControllerFactory(factory);

        Parent root = loader.load();
        
        primaryStage.setTitle("Catálogo de Jogos (MVC + SOLID)");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
