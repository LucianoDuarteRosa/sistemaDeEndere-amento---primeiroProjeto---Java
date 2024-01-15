package Views;

import Entidades.CadProd;
import Utils.Alerts;
import Utils.Constraints;
import Utils.Util;
import bdProd.DAOProd;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class NovoConsultaController implements Initializable {

    CadProd prod = new CadProd();

    public NovoConsultaController(CadProd prod) {
        this.prod = prod;
    }

    public CadProd getCadProd() {
        return prod;
    }

    @FXML
    private TextField txtConsulta;

    @FXML
    private Button btnConsultar;

    @FXML
    private void onConsultar() {
        if (txtConsulta.getText().isEmpty()) {
            Alerts.showAlert("Consulta produto", null, "Campo de busca não pode estar vazio.", Alert.AlertType.INFORMATION);
        } else {
            String text = "%" + txtConsulta.getText() + "%";
            List<CadProd> list = DAOProd.findAll(text);
            if (list == null) {
                Alerts.showAlert("Consultar produto", null, "Produto não localizado.", Alert.AlertType.ERROR);
            } else {
                onTableViewAdd(text);
            }
        }
    }

    @FXML
    private Button btnSelecionar;

    @FXML
    private void onSelecionar() {
        btnSelecionar.setOnAction(evento -> {
            CadProd prod = (CadProd) tbResult.getSelectionModel().getSelectedItem();
            if (tbResult.getSelectionModel().getSelectedItem() == null) {
                Alerts.showAlert("Consultar produto", null, "Nenhum produto selecionado.", Alert.AlertType.INFORMATION);
            } else {
                this.prod = prod;
                onCancelar(evento);
            }
        });
    }

    @FXML
    private void onClick() {
        tbResult.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                CadProd prod = tbResult.getSelectionModel().getSelectedItem();
                if (prod == null) {
                    Alerts.showAlert("Consultar produto", null, "Nenhum produto selecionado.", Alert.AlertType.INFORMATION);
                } else {
                    this.prod = prod;
                    btnSelecionar.fire();
                }
            }
        });
    }

    @FXML
    private Button btnCancelar;

    @FXML
    private void onCancelar(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @FXML
    private TableView<CadProd> tbResult;

    @FXML
    private TableColumn<CadProd, Integer> tbCodInt;

    @FXML
    private TableColumn<CadProd, String> tbDesc;

    @FXML
    private TableColumn<CadProd, String> tbFornecedor;

    @FXML
    private TableColumn<CadProd, Double> tbQuantMinVenda;

    @FXML
    private TableColumn<CadProd, Double> tbEstoque;

    @FXML
    private TableColumn<CadProd, String> tbCodFab;

    @FXML
    private ObservableList<CadProd> obsList;

    private void onTableViewAdd(String desc) {
        List<CadProd> list = DAOProd.findAll(desc);
        tbCodInt.setCellValueFactory(new PropertyValueFactory<>("CodInterno"));
        tbDesc.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        tbQuantMinVenda.setCellValueFactory(new PropertyValueFactory<>("QuantMinVenda"));
        tbEstoque.setCellValueFactory(new PropertyValueFactory<>("Estoque"));
        tbFornecedor.setCellValueFactory(new PropertyValueFactory<>("Fornecedor"));
        tbCodFab.setCellValueFactory(new PropertyValueFactory<>("CodFabricante"));

        obsList = FXCollections.observableArrayList(list);
        tbResult.setItems(obsList);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onCancelar(event);
        });

        btnConsultar.setDefaultButton(true);
        btnConsultar.setOnAction((event) -> {
            onConsultar();
        });

        onSelecionar();
        onClick();

        Constraints.setTextFieldUppercase(txtConsulta);
    }
}
