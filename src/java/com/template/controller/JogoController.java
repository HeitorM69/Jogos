package com.template.controller;

import com.template.model.dao.JogoDAO;
import com.template.model.dto.JogoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import validator.UsuarioValidator;

public class JogoController implements Initializable {

    @FXML private TextField txtNome;
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

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colVersao.setCellValueFactory(new PropertyValueFactory<>("versao"));

        cbxTipo.setItems(FXCollections.observableArrayList(
                "Ação", "Aventura", "RPG", "FPS", "Estratégia", "Esportes", "Simulação", "Outros"
        ));

        carregarJogos();
        limparCampos();
    }

    private boolean isCamposValidos() {
        if (txtNome.getText().trim().isEmpty() ||
                cbxTipo.getValue() == null ||
                txtVersao.getText().trim().isEmpty()) {

            exibirMensagem("Erro: Preencha todos os campos obrigatórios!", "#E94560");
            return false;
        }
        return true;
    }

    @FXML
    private void btnCadastrarAction() {
        if (!isCamposValidos()) return;

        JogoDTO jogo = new JogoDTO();
        jogo.setNome(txtNome.getText().trim());
        jogo.setTipo(cbxTipo.getValue());
        jogo.setVersao(txtVersao.getText().trim());

        JogoDAO dao = new JogoDAO();
        dao.cadastrarJogo(jogo);

        exibirMensagem("Jogo cadastrado com sucesso!", "#A6B1E1");
        limparCampos();
        carregarJogos();
    }

    @FXML
    private void btnAlterarAction() {
        JogoDTO selecionado = tblJogo.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        if (!isCamposValidos()) return;

        if (exibirConfirmacao("Confirmar Alteração", "Deseja realmente alterar o jogo '" + selecionado.getNome() + "'?")) {
            JogoDTO jogo = new JogoDTO();
            jogo.setId(selecionado.getId());
            jogo.setNome(txtNome.getText().trim());
            jogo.setTipo(cbxTipo.getValue());
            jogo.setVersao(txtVersao.getText().trim());

            JogoDAO dao = new JogoDAO();
            dao.alterarJogo(jogo);

            exibirMensagem("Jogo alterado com sucesso!", "#A6B1E1");
            limparCampos();
            carregarJogos();
        }
    }

    @FXML
    private void btnExcluirAction() {
        JogoDTO selecionado = tblJogo.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        if (exibirConfirmacao("Confirmar Exclusão", "Deseja realmente excluir o jogo '" + selecionado.getNome() + "'?")) {
            JogoDAO dao = new JogoDAO();
            dao.excluirJogo(selecionado.getId());

            exibirMensagem("Jogo excluído com sucesso!", "#A6B1E1");
            limparCampos();
            carregarJogos();
        }
    }

    @FXML
    private void btnLimparAction() {
        limparCampos();
        lblMensagem.setText("");
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
            cbxTipo.setValue(selecionado.getTipo());
            txtVersao.setText(selecionado.getVersao());

            lblMensagem.setText("");

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

        btnCadastrar.setDisable(false);
        btnAlterar.setDisable(true);
        btnExcluir.setDisable(true);
    }

    private void exibirMensagem(String mensagem, String corHex) {
        lblMensagem.setText(mensagem);
        lblMensagem.setStyle("-fx-text-fill: " + corHex + ";");
    }

    private boolean exibirConfirmacao(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
}