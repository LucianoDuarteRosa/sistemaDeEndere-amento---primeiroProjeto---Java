package Views;

import Entidades.Endereco;
import Utils.Alerts;
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

public class NovoEndExluirController implements Initializable {

    Endereco end = new Endereco();

    @FXML
    public void attPredio() {
        if(comboRua.getSelectionModel().getSelectedItem() != null){
        Endereco end = comboRua.getSelectionModel().getSelectedItem();
        List<Endereco> list = new ArrayList<>();
        list = DAOEnd.findAllPredio(end);
        obsList = FXCollections.observableArrayList(list);
        comboPredio.setItems(obsList);
        this.end.setRua(end.getRua());
        }
    }

    @FXML
    private ObservableList<Endereco> obsList;

    @FXML
    private ComboBox<Endereco> comboRua;

    @FXML
    private ComboBox<Endereco> comboPredio;

    @FXML
    private Button btnExcluir;

    @FXML
    private void onExcluir() {
        Endereco end = new Endereco();
        if (comboPredio.getSelectionModel().getSelectedItem() == null && this.end.getRua() != null) {
            end.setRua(this.end.getRua());
            end = DAOEnd.encontrarEnd3(end);
            if (end == null) {
                DAOEnd.deleteRua(this.end.getRua());
                atualizarRua();
            } else {
                Alerts.showAlert("Excluir rua/prédio", null, "Rua/prédio com produto assossiado. Não é possível excluir.", Alert.AlertType.ERROR);
            }              
            
        } else {
            end = comboPredio.getSelectionModel().getSelectedItem();
            end.setRua(this.end.getRua());
            this.end = end;
            end = DAOEnd.encontrarEnd2(end);
            if (end == null) {
                DAOEnd.deletePredio(this.end.getId());
                attPredio(); 
                atualizarRua();                              
            } else {
                Alerts.showAlert("Excluir rua/prédio", null, "Rua/prédio com produto assossiado. Não é possível excluir.", Alert.AlertType.ERROR);
            }
        }
    }
    
    private void atualizarRua(){
        List<Endereco> list = new ArrayList<>();
        list = DAOEnd.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboRua.setItems(obsList);
    }
    
    

    @FXML
    private Button btnCancelar;

    @FXML
    private void onCancelar(ActionEvent event
    ) {
        Util.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        atualizarRua();

        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onCancelar(event);
        });
    }

}
