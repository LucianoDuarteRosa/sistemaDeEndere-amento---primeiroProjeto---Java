
package Views;

import Entidades.Fornecedor;
import Utils.Alerts;
import Utils.Util;
import bdProd.DAOFornecedor;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ListaNovoInventarioConsultaController implements Initializable {

    Fornecedor fornecedor = new Fornecedor();
    List<Fornecedor> list = new ArrayList<>();
   

    public ListaNovoInventarioConsultaController(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public List<Fornecedor> getList() {
        return list;
    }
    
    @FXML
    private Button btnSelecionar;

    @FXML
    private void btnSelecionar() {
         btnSelecionar.setOnAction(event -> {
           Fornecedor fornecedor = (Fornecedor) tbResult.getSelectionModel().getSelectedItem();
            if (fornecedor == null) {
                Alerts.showAlert("Consultar fornecedor", null, "Nenhum fornecedor selecionado.", Alert.AlertType.INFORMATION);
            } else {                            
                list.add(fornecedor);
                Alerts.showAlert("Lista de fornecedores para inventário", null, "Fornecedor adicionado com sucesso.", Alert.AlertType.INFORMATION);
            }
        });
    
    }

    @FXML
    private TableView tbResult;

    @FXML
    private TableColumn<Fornecedor, String> tbRazaoSocial;

    @FXML
    private TableColumn<Fornecedor, String> tbCNPJ;
    
    @FXML
    private TableColumn<Fornecedor, Integer> tbId;
    
    @FXML
    private ObservableList<Fornecedor> obsList;
    
    private void onTableViewAdd() {
        List<Fornecedor> list = DAOFornecedor.encontrarFornecedor();
        tbId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tbRazaoSocial.setCellValueFactory(new PropertyValueFactory<>("RazaoSocial"));
        tbCNPJ.setCellValueFactory(new PropertyValueFactory<>("CNPJ"));

        obsList = FXCollections.observableArrayList(list);
        tbResult.setItems(obsList);
    }
    
    

    @FXML
    private Button btnCancelar;

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
        btnSelecionar();
        onTableViewAdd();
    }

}
