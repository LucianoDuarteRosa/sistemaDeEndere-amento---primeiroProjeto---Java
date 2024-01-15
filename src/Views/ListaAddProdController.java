package Views;

import Entidades.CadProd;
import Entidades.Enderecamento;
import Entidades.Endereco;
import Entidades.Lista;
import Utils.Alerts;
import Utils.Constraints;
import Utils.Util;
import bdProd.DAOEnderecamento;
import bdProd.DAOLista;
import bdProd.DB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListaAddProdController implements Initializable {

    Lista lista = new Lista();
    CadProd prod = new CadProd();

    public ListaAddProdController(Lista lista) {
        this.lista = lista;
    }

    @FXML
    private Label lblLista;

    @FXML
    private Button btnCancelar;

    @FXML
    private void onCancelar(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @FXML
    private Button btnAdicionar;

    @FXML
    private void onAdicionar() {
        btnAdicionar.setOnAction(event -> {
            Enderecamento end = tbResult.getSelectionModel().getSelectedItem();
            if (end == null) {
                Alerts.showAlert("Adiconar produtos na lista", null, "Nenhum produto selecionado.", Alert.AlertType.INFORMATION);
            } else {
                end.setLista(lista.getId());
                DAOLista.update(end);
                onConsultar();
                txtCodInt.requestFocus();
            }
        });

    }

    @FXML
    private Button btnAdd2;

    Endereco endereco = new Endereco();

    @FXML
    private void onAdd2() {
        btnAdd2.setOnAction(event -> {
            Enderecamento end = tbResult2.getSelectionModel().getSelectedItem();
            if (end == null) {
                Alerts.showAlert("Adiconar produtos na lista", null, "Nenhum produto selecionado.", Alert.AlertType.INFORMATION);
            } else {
                end.setLista(lista.getId());
                DAOLista.update(end);
                onConsultar();
                onTableViewAdd2(endereco);
                txtCodInt.requestFocus();
            }
        });

    }

    @FXML
    private TextField txtCodInt;

    @FXML
    private Button btnConsultar;

    @FXML
    private void onConsultar() {
        int a = 0;
        if (txtCodInt.getText().isEmpty()) {
            Alerts.showAlert("Consulta de produto", null, "Campos de busca não podem estar vazios.", Alert.AlertType.ERROR);
            a++;
        }
        if (a == 0) {
            Integer codInt = Integer.valueOf(txtCodInt.getText());
            if (DAOEnderecamento.encontrarLista(codInt) == null) {
                Alerts.showAlert("Consulta de produto", null, "Nenhum endereçamento localizado para o código informado.", Alert.AlertType.INFORMATION);
            } else {
                onTableViewAdd(codInt);
            }
        }
    }
    @FXML
    private TableView<Enderecamento> tbResult;

    @FXML
    private TableColumn<Enderecamento, String> tbCodInt;

    @FXML
    private TableColumn<Enderecamento, String> tbDescricao;

    @FXML
    private TableColumn<Enderecamento, String> tbTon;

    @FXML
    private TableColumn<Enderecamento, String> tbBitola;

    @FXML
    private TableColumn<Enderecamento, String> tbRua;

    @FXML
    private TableColumn<Enderecamento, String> tbPredio;

    @FXML
    private TableColumn<Enderecamento, String> tbObservacao;

    @FXML
    private TableColumn<Enderecamento, Integer> tbID;

    @FXML
    private ObservableList<Enderecamento> obsList;

    private void onTableViewAdd(Integer codInt) {
        List<Enderecamento> list = DAOLista.findAllList(codInt);
        tbCodInt.setCellValueFactory(new PropertyValueFactory<>("CodInt"));
        tbDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        tbTon.setCellValueFactory(new PropertyValueFactory<>("Ton"));
        tbBitola.setCellValueFactory(new PropertyValueFactory<>("Bitola"));
        tbRua.setCellValueFactory(new PropertyValueFactory<>("Rua"));
        tbPredio.setCellValueFactory(new PropertyValueFactory<>("Predio"));
        tbObservacao.setCellValueFactory(new PropertyValueFactory<>("Observacao"));
        tbID.setCellValueFactory(new PropertyValueFactory<>("Id"));

        obsList = FXCollections.observableArrayList(list);
        tbResult.setItems(obsList);
    }

    @FXML
    private TableView<Enderecamento> tbResult2;

    @FXML
    private TableColumn<Enderecamento, String> tbCodInt2;

    @FXML
    private TableColumn<Enderecamento, String> tbDesc2;

    @FXML
    private TableColumn<Enderecamento, String> tbTon2;

    @FXML
    private TableColumn<Enderecamento, String> tbBit2;

    @FXML
    private TableColumn<Enderecamento, Integer> tbId2;

    @FXML
    private TableColumn<Enderecamento, String> tbObs2;

    @FXML
    private ObservableList<Enderecamento> obsList2;

    private void onTableViewAdd2(Endereco endereco) {
        List<Enderecamento> list = DAOLista.findAllList2(endereco);
        tbCodInt2.setCellValueFactory(new PropertyValueFactory<>("CodInt"));
        tbDesc2.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        tbTon2.setCellValueFactory(new PropertyValueFactory<>("Ton"));
        tbBit2.setCellValueFactory(new PropertyValueFactory<>("Bitola"));
        tbObs2.setCellValueFactory(new PropertyValueFactory<>("Observacao"));
        tbId2.setCellValueFactory(new PropertyValueFactory<>("Id"));

        obsList2 = FXCollections.observableArrayList(list);
        tbResult2.setItems(obsList2);
    }

    @FXML
    private Button btnConsulta;

    @FXML
    private void onConsulta() {
        createDialogForm2("/Views/NovoConsulta.fxml", "Consultar produto");
    }

    private void createDialogForm2(String absoluteName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            loader.setController(new NovoConsultaController(prod));
            Pane pane = loader.load();
            NovoConsultaController controller = loader.getController();

            Stage dialogStage = new Stage();
            try {
                String caminho = DB.encontrarCaminho("config");
                Image logoImage = new Image(new FileInputStream(caminho));
                dialogStage.getIcons().add(logoImage);
            } catch (FileNotFoundException e) {
            }

            dialogStage.setTitle(title);
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.showAndWait();
            prod = controller.getCadProd();
            txtCodInt.setText(String.valueOf(prod.getCodInterno()));
            onConsultar();

        } catch (IOException e) {
            Alerts.showAlert("Io Exception", "Error Load View", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblLista.setText(String.valueOf(lista.getId()));
        Constraints.setTextFieldInteger(txtCodInt);
        onAdicionar();
        onAdd2();
        txtCodInt.requestFocus();

        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onCancelar(event);
        });

        btnConsultar.setDefaultButton(true);
        btnConsultar.setOnAction((event) -> {
            onConsultar();
        });

        tbResult.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Enderecamento end = tbResult.getSelectionModel().getSelectedItem();
                Endereco endereco = new Endereco(end.getRua(), end.getPredio());
                onTableViewAdd2(endereco);
                this.endereco = endereco;
            }
        });

    }
}
