package Views;

import Utils.Alerts;
import Utils.Util;
import bdProd.DAOFornecedor;
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

public class ImportFornecedorController implements Initializable {

    @FXML
    private TextField txtCaminho;

    @FXML
    private Button btnImportar;

    @FXML
    private void btnImportar() {
        String arq = txtCaminho.getText();

        if (txtCaminho.getText().isEmpty()) {
            Alerts.showAlert("Importação de Arquivo", null, "Campo de busca inválido.", Alert.AlertType.INFORMATION);
        } else {
            DAOFornecedor.importFornecedor(arq);
        }
    }

    @FXML
    private Button btnLocalizar;

    @FXML
    public void localizar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar arquivo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv")
        );
        Stage primaryStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            txtCaminho.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private Button btnCancelar;

    @FXML
    public void btnCancelar(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnImportar.setDefaultButton(true);
        btnImportar.setOnAction((event) -> {
            btnImportar();
        });
        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            btnCancelar(event);
        });

    }

}
