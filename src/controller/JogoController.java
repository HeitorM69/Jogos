package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Jogo;
import validation.CamposObrigatoriosValidador;
import validation.IJogoValidador;
import validation.PrecoValidador;
import java.util.List;

public class JogoController {

    @FXML private TextField txtTitulo;
    @FXML private TextField txtPreco;
    @FXML private Label lblMensagem;

    private IJogoValidador validador;

    public JogoController(IJogoValidador validador) {
        this.validador = validador;
    }

    @FXML
    public void cadastrar() {
        processarAcao(true);
    }

    @FXML
    public void atualizar() {
        processarAcao(false);
    }

    private void processarAcao(boolean isCadastro) {
        validador.limparValidadores();

        String titulo = txtTitulo.getText();
        Double preco = null;
        
        try {
            preco = Double.parseDouble(txtPreco.getText());
        } catch (NumberFormatException e) {}

        validador.adicionarValidador(new CamposObrigatoriosValidador(titulo, "Título do Jogo"));
        validador.adicionarValidador(new PrecoValidador(preco));

        List<String> erros = validador.validarTodos();

        if (erros.isEmpty()) {
            Jogo jogo = new Jogo(titulo, preco);
            lblMensagem.setText(isCadastro ? "Jogo cadastrado com sucesso!" : "Jogo atualizado com sucesso!");
            lblMensagem.setStyle("-fx-text-fill: green;");
        } else {
            lblMensagem.setText(String.join("\n", erros));
            lblMensagem.setStyle("-fx-text-fill: red;");
        }
    }
}
