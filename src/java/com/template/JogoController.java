package com.template;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class JogoController implements Initializable {

    @FXML private TextField txtNome;
    // UX Melhoria 1: ComboBox ao invés de TextField para opções restritas
    @FXML private ComboBox<String> cbxTipo;
    @FXML private TextField txtVersao;

    @FXML private Button btnCadastrar;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;
    @FXML private Button btnLimpar;

    @FXML private Label lblMensagem;

    @FXML private TableView<JogoDTO> tblJogo;
    @FXML private TableColumn<JogoDTO, String> colNome;
    @FXML private TableColumn<JogoDTO, String> colTipo;
    @FXML private TableColumn<JogoDTO, String> colVersao;

    private String acaoPendente = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colVersao.setCellValueFactory(new PropertyValueFactory<>("versao"));

        // Preenche o ComboBox com os gêneros de jogos
        cbxTipo.setItems(FXCollections.observableArrayList(
                "Ação", "Aventura", "RPG", "FPS", "Estratégia", "Esportes", "Simulação", "Outros"
        ));

        carregarJogos();
        limparCampos(); // Já inicia bloqueando os botões corretos e limpando

        Platform.runLater(() -> {
            txtNome.getScene().setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    confirmarAcaoPendente();
                }
            });
        });
    }

    // UX Melhoria 2: Método para validar campos obrigatórios
    private boolean isCamposValidos() {
        if (txtNome.getText().trim().isEmpty() ||
                cbxTipo.getValue() == null ||
                txtVersao.getText().trim().isEmpty()) {

            lblMensagem.setText("Erro: Preencha todos os campos obrigatórios!");
            lblMensagem.setStyle("-fx-text-fill: #E94560;"); // Vermelho erro
            return false;
        }
        return true;
    }

    @FXML
    private void btnCadastrarAction() {
        if (!isCamposValidos()) return; // Barra a execução se for inválido

        JogoDTO jogo = new JogoDTO();
        jogo.setNome(txtNome.getText());
        jogo.setTipo(cbxTipo.getValue()); // Pega do ComboBox
        jogo.setVersao(txtVersao.getText());

        JogoDAO dao = new JogoDAO();
        dao.cadastrarJogo(jogo);

        lblMensagem.setText("Jogo cadastrado com sucesso!");
        lblMensagem.setStyle("-fx-text-fill: #A6B1E1;");

        limparCampos();
        carregarJogos();
    }

    @FXML
    private void btnAlterarAction() {
        if (!isCamposValidos()) return;

        JogoDTO selecionado = tblJogo.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            acaoPendente = "ALTERAR";
            lblMensagem.setText("Aviso: Pressione ENTER para confirmar a ALTERAÇÃO de '" + selecionado.getNome() + "'.");
            lblMensagem.setStyle("-fx-text-fill: #E94560;");
        }
    }

    @FXML
    private void btnExcluirAction() {
        JogoDTO selecionado = tblJogo.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            acaoPendente = "EXCLUIR";
            lblMensagem.setText("Aviso: Pressione ENTER para confirmar a EXCLUSÃO de '" + selecionado.getNome() + "'.");
            lblMensagem.setStyle("-fx-text-fill: #E94560;");
        }
    }

    private void confirmarAcaoPendente() {
        if (acaoPendente.equals("")) return;

        JogoDTO selecionado = tblJogo.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        JogoDAO dao = new JogoDAO();

        if (acaoPendente.equals("ALTERAR")) {
            JogoDTO jogo = new JogoDTO();
            jogo.setId(selecionado.getId());
            jogo.setNome(txtNome.getText());
            jogo.setTipo(cbxTipo.getValue());
            jogo.setVersao(txtVersao.getText());
            dao.alterarJogo(jogo);
            lblMensagem.setText("Jogo alterado com sucesso!");
            lblMensagem.setStyle("-fx-text-fill: #A6B1E1;");

        } else if (acaoPendente.equals("EXCLUIR")) {
            dao.excluirJogo(selecionado.getId());
            lblMensagem.setText("Jogo excluído com sucesso!");
            lblMensagem.setStyle("-fx-text-fill: #A6B1E1;");
        }

        acaoPendente = "";
        limparCampos();
        carregarJogos();
    }

    @FXML
    private void btnLimparAction() {
        limparCampos();
        lblMensagem.setText(""); // Limpa os avisos
    }

    @FXML
    private void carregarJogos() {
        JogoDAO dao = new JogoDAO();
        ArrayList<JogoDTO> lista = dao.listarJogos();
        ObservableList<JogoDTO> obsLista = FXCollections.observableArrayList(lista);
        tblJogo.setItems(obsLista);
    }

    @FXML
    private void carregarCampos() {
        JogoDTO selecionado = tblJogo.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            txtNome.setText(selecionado.getNome());
            cbxTipo.setValue(selecionado.getTipo()); // Joga pro ComboBox
            txtVersao.setText(selecionado.getVersao());

            acaoPendente = "";
            lblMensagem.setText("");

            // UX Melhoria 3: Habilita edição, bloqueia cadastro
            btnCadastrar.setDisable(true);
            btnAlterar.setDisable(false);
            btnExcluir.setDisable(false);
        }
    }

    private void limparCampos() {
        txtNome.clear();
        cbxTipo.getSelectionModel().clearSelection();
        cbxTipo.setPromptText("Selecione um gênero");
        txtVersao.clear();
        txtNome.requestFocus();

        tblJogo.getSelectionModel().clearSelection();
        acaoPendente = "";

        // UX Melhoria 3: Estado padrão (só permite cadastrar)
        btnCadastrar.setDisable(false);
        btnAlterar.setDisable(true);
        btnExcluir.setDisable(true);
    }
}