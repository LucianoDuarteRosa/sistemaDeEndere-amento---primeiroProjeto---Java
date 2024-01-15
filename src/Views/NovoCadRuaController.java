package Views;

import Utils.Alerts;
import Entidades.Endereco;
import Utils.Constraints;
import Utils.Util;
import bdProd.DAOEnd;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NovoCadRuaController implements Initializable {

    @FXML
    private TextField txtRua;

    @FXML
    private Button btnAdicionar;

    @FXML
    private void onBtnAddAction() {
        if (txtRua.getText().isEmpty()) {
            Alerts.showAlert("Adiconar nova rua", null, "O Campo não pode estar vazio.", Alert.AlertType.INFORMATION);
        } else {

            Integer rua = Integer.parseInt(txtRua.getText());
            Endereco obj = new Endereco(rua);
            DAOEnd.addRua(obj);
            txtRua.clear();
        }
    }

    @FXML
    private Button btnCancelar;

    @FXML
    private void onBtnCancelarAction(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Constraints.setTextFieldInteger(txtRua);

        btnAdicionar.setDefaultButton(true);
        btnAdicionar.setOnAction((event) -> {
            onBtnAddAction();
        });
        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onBtnCancelarAction(event);
        });

    }
}
