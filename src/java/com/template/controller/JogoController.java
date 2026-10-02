package com.template.controller;

import com.template.model.dto.JogoDTO;
import com.template.service.IJogoService;
import com.template.validator.IJogoValidador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.template.util.DialogUtil.showConfirmation;
import static com.template.util.DialogUtil.showError;
import static com.template.util.DialogUtil.showInfo;

public class JogoController implements Initializable {

    @FXML private TextField txtNome;
    @FXML private ComboBox<String> cbxTipo;
    @FXML private TextField txtVersao;
    @FXML private Button btnCadastrar;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;
    @FXML private Button btnLimpar;
    @FXML private TableView<JogoDTO> tblJogo;
    @FXML private TableColumn<JogoDTO, String> colNome;
    @FXML private TableColumn<JogoDTO, String> colTipo;
    @FXML private TableColumn<JogoDTO, String> colVersao;

    private final IJogoService jogoService;
    private final IJogoValidador jogoValidador;

    public JogoController(
            IJogoService jogoService,
            IJogoValidador jogoValidador
    ) {
        this.jogoService = jogoService;
        this.jogoValidador = jogoValidador;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabela();
        configurarTipos();
        carregarJogos();
        limparCampos();
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colVersao.setCellValueFactory(new PropertyValueFactory<>("versao"));
    }

    private void configurarTipos() {
        cbxTipo.setItems(FXCollections.observableArrayList(
                "Ação",
                "Aventura",
                "RPG",
                "FPS",
                "Estratégia",
                "Esportes",
                "Simulação",
                "Terror",
                "Corrida",
                "Luta",
                "Outros"
        ));
        cbxTipo.setPromptText("Selecione um gênero");
    }

    @FXML
    private void btnCadastrarAction() {
        JogoDTO jogo = preencherDTO();
        if (!validarCampos(jogo)) return;

        try {
            jogoService.cadastrarJogo(jogo);
            showInfo("Jogo cadastrado com sucesso!");
            limparCampos();
            carregarJogos();
        } catch (RuntimeException e) {
            showError(mensagemDaExcecao(
                    e,
                    "Não foi possível cadastrar o jogo."
            ));
        }
    }

    @FXML
    private void btnAlterarAction() {
        JogoDTO selecionado = tblJogo
                .getSelectionModel()
                .getSelectedItem();

        if (selecionado == null) {
            showError("Selecione um jogo para alterar.");
            return;
        }

        JogoDTO jogo = preencherDTO();
        jogo.setId(selecionado.getId());

        if (!validarCampos(jogo)) return;

        boolean confirmou = showConfirmation(
                "Confirmar alteração",
                "Deseja realmente alterar o jogo '"
                        + selecionado.getNome()
                        + "'?"
        );

        if (!confirmou) return;

        try {
            jogoService.alterarJogo(jogo);
            showInfo("Jogo alterado com sucesso!");
            limparCampos();
            carregarJogos();
        } catch (RuntimeException e) {
            showError(mensagemDaExcecao(
                    e,
                    "Não foi possível alterar o jogo."
            ));
        }
    }

    @FXML
    private void btnExcluirAction() {
        JogoDTO selecionado = tblJogo
                .getSelectionModel()
                .getSelectedItem();

        if (selecionado == null) {
            showError("Selecione um jogo para excluir.");
            return;
        }

        boolean confirmou = showConfirmation(
                "Confirmar exclusão",
                "Deseja realmente excluir o jogo '"
                        + selecionado.getNome()
                        + "'?"
        );

        if (!confirmou) return;

        try {
            jogoService.excluirJogo(selecionado.getId());
            showInfo("Jogo excluído com sucesso!");
            limparCampos();
            carregarJogos();
        } catch (RuntimeException e) {
            showError(mensagemDaExcecao(
                    e,
                    "Não foi possível excluir o jogo."
            ));
        }
    }

    @FXML
    private void btnLimparAction() {
        limparCampos();
    }

    private JogoDTO preencherDTO() {
        JogoDTO jogo = new JogoDTO();
        jogo.setNome(txtNome.getText().trim());
        jogo.setTipo(cbxTipo.getValue());
        jogo.setVersao(txtVersao.getText().trim());
        return jogo;
    }

    private boolean validarCampos(JogoDTO jogo) {
        return jogoValidador.validarJogo(
                jogo.getNome(),
                jogo.getTipo(),
                jogo.getVersao()
        );
    }

    private void carregarJogos() {
        try {
            List<JogoDTO> lista = jogoService.listarJogos();
            ObservableList<JogoDTO> listaObservavel =
                    FXCollections.observableArrayList(lista);
            tblJogo.setItems(listaObservavel);
        } catch (RuntimeException e) {
            showError(mensagemDaExcecao(
                    e,
                    "Não foi possível carregar os jogos."
            ));
        }
    }

    @FXML
    private void carregarCampos() {
        JogoDTO selecionado = tblJogo
                .getSelectionModel()
                .getSelectedItem();

        if (selecionado == null) return;

        txtNome.setText(selecionado.getNome());
        cbxTipo.setValue(selecionado.getTipo());
        txtVersao.setText(selecionado.getVersao());

        btnCadastrar.setDisable(true);
        btnAlterar.setDisable(false);
        btnExcluir.setDisable(false);
    }

    private void limparCampos() {
        txtNome.clear();
        cbxTipo.getSelectionModel().clearSelection();
        cbxTipo.setPromptText("Selecione um gênero");
        txtVersao.clear();
        tblJogo.getSelectionModel().clearSelection();

        btnCadastrar.setDisable(false);
        btnAlterar.setDisable(true);
        btnExcluir.setDisable(true);

        txtNome.requestFocus();
    }

    private String mensagemDaExcecao(
            RuntimeException e,
            String mensagemPadrao
    ) {
        if (e.getMessage() == null || e.getMessage().isBlank()) {
            return mensagemPadrao;
        }
        return e.getMessage();
    }
}
