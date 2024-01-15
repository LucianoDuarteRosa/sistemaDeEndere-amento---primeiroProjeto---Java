package Views;

import Utils.Alerts;
import Utils.Util;
import bdProd.DB;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ConfiguracaoController implements Initializable {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCaminho;


    @FXML
    private Button btnLocalizar;

    @FXML
    public void onLocalizar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar arquivo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Arquivos JPG, JPEG ou PNG ", "*.jpg", "*.jpeg", "*.png")
        );
        Stage primaryStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            txtCaminho.setText(selectedFile.getAbsolutePath());
            DB.update("config", txtCaminho.getText());
        }
    }

    
    @FXML
    private Button btnGravar;

    @FXML
    private void onGravar() {
        if (!txtCaminho.getText().isEmpty()) {
            DB.update("config", txtCaminho.getText());
        }
        if (!txtNome.getText().isEmpty()) {
            DB.update("nome", txtNome.getText());
        }
        Alerts.showAlert("Configuração", null, "Informações salvas com sucesso. Para atualizar os dados, reinicie a aplicação.", Alert.AlertType.INFORMATION);

    }

    @FXML
    private Button btnCancelar;

    @FXML
    public void onCancelar(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtCaminho.setText(DB.encontrarCaminho("config"));
        txtNome.setText(DB.encontrarCaminho("nome"));

        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onCancelar(event);

        });
    }

}
