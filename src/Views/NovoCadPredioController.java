package Views;

import Utils.Alerts;
import Entidades.Endereco;
import Utils.Constraints;
import Utils.Util;
import bdProd.DAOEnd;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class NovoCadPredioController implements Initializable {

    @FXML
    private ComboBox<Endereco> comboBoxRua;

    @FXML
    private ObservableList<Endereco> obsList;

    @FXML
    private void onComboBoxRua() {
        Endereco rua = comboBoxRua.getSelectionModel().getSelectedItem();
    }

    @FXML
    private TextField txtPredio;

    @FXML
    private Button btnAdd;

    @FXML
    private void onBtnAddAction() {
        if (comboBoxRua.getValue() == null){
            Alerts.showAlert("Adicionar prédio", null, "Campo da rua não pode estar vazio.", Alert.AlertType.INFORMATION);
        }else{
        Endereco ruaa = comboBoxRua.getValue();
        Integer rua = ruaa.getRua();
        if (txtPredio.getText().isEmpty()) {
            Alerts.showAlert("Adiconar nova rua", null, "O Campo não pode estar vazio.", Alert.AlertType.INFORMATION);
        } else {
            Integer predio = Integer.parseInt(txtPredio.getText());
            Endereco obj = new Endereco(rua, predio);
            DAOEnd.addEnd(obj);
            txtPredio.clear();
        }
    }
    }
    @FXML
    private Button btnCancelar;

    @FXML
    private void onBtnCancelarAction(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @FXML
    private Button btnNovaRua;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Constraints.setTextFieldInteger(txtPredio);

        List<Endereco> list = new ArrayList<>();
        list = DAOEnd.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxRua.setItems(obsList);

        btnAdd.setDefaultButton(true);
        btnAdd.setOnAction((event) -> {
            onBtnAddAction();
        });
        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onBtnCancelarAction(event);

        });

    }
}
