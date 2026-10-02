package com.template.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public final class DialogUtil {

    private DialogUtil() {
    }

    public static void showError(String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void showInfo(String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static boolean showConfirmation(
            String titulo,
            String mensagem
    ) {
        ButtonType confirmar = new ButtonType(
                "Confirmar",
                ButtonBar.ButtonData.OK_DONE
        );

        ButtonType cancelar = new ButtonType(
                "Cancelar",
                ButtonBar.ButtonData.CANCEL_CLOSE
        );

        Alert alert = new Alert(
                AlertType.CONFIRMATION,
                mensagem,
                confirmar,
                cancelar
        );

        alert.setTitle(titulo);
        alert.setHeaderText(null);

        return alert.showAndWait().orElse(cancelar) == confirmar;
    }
}
