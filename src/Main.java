import controller.ControllerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import validation.IJogoValidador;
import validation.JogoValidador;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        IJogoValidador validador = new JogoValidador();
        ControllerFactory factory = new ControllerFactory(validador);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/JogoView.fxml"));
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
