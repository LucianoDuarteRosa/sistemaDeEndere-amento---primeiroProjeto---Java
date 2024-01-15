package Views;

import Utils.Alerts;
import Utils.Util;
import bdProd.DAOProd;
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

public class ImportCsvController implements Initializable {

    @FXML
    private TextField caminhoTxt;

    @FXML
    private Button btnImportar;

    @FXML
    private Button btnCancelar;
    
    @FXML
    private Button btnLocalizar;

    @FXML
    private void onImportarCsv(ActionEvent event) {
        String arq = caminhoTxt.getText();

        if (caminhoTxt.getText() == null || caminhoTxt.getText().trim().equals("")) {
            Alerts.showAlert("Importação de Arquivo", null, "Campo de busca inválido.", Alert.AlertType.INFORMATION);
        } else {
            DAOProd.importCsv(arq);
        }

    }
    
    @FXML
    public void localizar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar arquivo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv", "*.txt")
        );
        Stage primaryStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
           caminhoTxt.setText(selectedFile.getAbsolutePath());
        }
    }


    @FXML
    public void onBtnCancelar(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onBtnCancelar(event);
        });
        
        btnImportar.setDefaultButton(true);
        btnImportar.setOnAction((event) -> {
            onImportarCsv(event);
        });

    }

}
