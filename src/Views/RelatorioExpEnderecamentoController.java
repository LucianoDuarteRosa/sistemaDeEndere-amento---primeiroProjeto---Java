package Views;

import Utils.Util;
import bdProd.DB;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class RelatorioExpEnderecamentoController implements Initializable {

    private String tela = "exp";
    private String caminho = null;

    @FXML
    private TextField txtCaminho;

    @FXML
    private Button btnLocalizar;

    @FXML
    public void onLocalizar() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Selecionar diretório de destino");
        Stage primaryStage = new Stage();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);

        if (selectedDirectory != null) {
            caminho = selectedDirectory.getAbsolutePath();
            txtCaminho.setText(caminho);            
        }
    }

    @FXML
    private Button btnCancelar;

    @FXML
    public void onCancelar(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @FXML
    private Button btnExportar;

    @FXML
    public void onExportar() {
        Utils.Impressao.exportarEnderecamento(caminho);
        DB.update(tela, caminho);       
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        caminho = DB.encontrarCaminho("exp");
        if (caminho != null) {
            txtCaminho.setText(caminho);
        }

        btnExportar.setDefaultButton(true);
        btnExportar.setOnAction((event) -> {
            onExportar();
        });
        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onCancelar(event);
        });
    }

}
