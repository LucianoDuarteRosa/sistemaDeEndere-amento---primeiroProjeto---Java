package Views;

import Entidades.Enderecamento;
import Entidades.Fornecedor;
import Entidades.Lista;
import Utils.Alerts;
import Utils.Constraints;
import Utils.Util;
import bdProd.DAOLista;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaConsultarListasController implements Initializable {

    Lista lista = new Lista();

    public ListaConsultarListasController(Lista lista) {
        this.lista = lista;
    }

    public Lista getLista() {
        return lista;
    }

    @FXML
    private TextField txtLista;

    @FXML
    private Button btnConsulta;

    @FXML
    private CheckBox checkDisp;

    @FXML
    private CheckBox checkFinal;

    @FXML
    private CheckBox checkTodas;

    @FXML
    private void onCheck() {
        checkDisp.setOnAction(e -> {
            if (checkDisp.isSelected()) {
                onTableViewAdd(DAOLista.findAllCheckBox("Disponivel"));
                checkTodas.setSelected(false);
                checkFinal.setSelected(false);
            }
        });
        checkFinal.setOnAction(e -> {
            if (checkFinal.isSelected()) {
                onTableViewAdd(DAOLista.findAllCheckBox("Finalizada"));
                checkDisp.setSelected(false);
                checkTodas.setSelected(false);
            }
        });

        checkTodas.setOnAction(e -> {
            if (checkTodas.isSelected()) {
                onTableViewAdd(DAOLista.findAllCheckBox("Tudo"));
                checkFinal.setSelected(false);
                checkDisp.setSelected(false);
            }
        });
    }

    @FXML
    private void onConsulta() {
        if (txtLista.getText().isEmpty() || txtLista.getText() == null) {
            Alerts.showAlert("Consultar lista", null, "Campo de busca não pode estar vazio.", Alert.AlertType.INFORMATION);
        } else {
            checkDisp.setSelected(false);
            checkFinal.setSelected(false);
            checkTodas.setSelected(true);
            onTableViewConsulta(Integer.parseInt(txtLista.getText()));
        }
    }

    @FXML
    private Button btnSelecionar;

    @FXML
    private void onselect() {
        btnSelecionar.setOnAction(event -> {
            Lista lista = (Lista) tbResult.getSelectionModel().getSelectedItem();
            if (lista == null) {
                Alerts.showAlert("Selecionar lista", null, "Nenhum lista selecionada.", Alert.AlertType.INFORMATION);
            } else {
                this.lista.setId(lista.getId());
                this.lista.setNome(lista.getNome());
                this.lista.setDisponivel(lista.getDisponivel());

                onCancelar(event);
            }
        });
    }

    @FXML
    private TableView tbResult;

    @FXML
    private TableColumn<Enderecamento, Integer> tbId;

    @FXML
    private TableColumn<Enderecamento, String> tbNome;

    @FXML
    private TableColumn<Enderecamento, String> tbDisponivel;

    @FXML
    private ObservableList<Lista> obsList;

    private void onTableViewAdd(List<Lista> list) {

        tbId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tbNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tbDisponivel.setCellValueFactory(new PropertyValueFactory<>("Disp"));

        obsList = FXCollections.observableArrayList(list);
        tbResult.setItems(obsList);
    }

    private void onTableViewConsulta(Integer id) {
        List<Lista> list = DAOLista.findOne(id);
        tbId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tbNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tbDisponivel.setCellValueFactory(new PropertyValueFactory<>("Disp"));

        obsList = FXCollections.observableArrayList(list);
        tbResult.setItems(obsList);
        if (list.isEmpty()) {
            Alerts.showAlert("Consultar lista", null, "Lista não localizada.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private Button btnCancelar;

    @FXML
    public void onCancelar(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @FXML
    private void onClick() {
        tbResult.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Lista lista = (Lista) tbResult.getSelectionModel().getSelectedItem();
                if (lista == null) {
                    Alerts.showAlert("Consultar produto", null, "Nenhuma lista selecionada.", Alert.AlertType.INFORMATION);
                } else {
                    btnSelecionar.fire();
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Constraints.setTextFieldInteger(txtLista);
        onselect();
        onCheck();
        onClick();
        onTableViewAdd(DAOLista.findAllCheckBox("Disponivel"));

        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onCancelar(event);
        });

        btnConsulta.setDefaultButton(true);
        btnConsulta.setOnAction((event) -> {
            onConsulta();
        });

    }
}
