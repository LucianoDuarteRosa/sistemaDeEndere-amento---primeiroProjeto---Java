package Views;

import Utils.Alerts;
import Utils.Util;
import Utils.Constraints;
import Entidades.CadProd;
import Entidades.Enderecamento;
import bdProd.DAOEnderecamento;
import bdProd.DAOProd;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NovoConsultaProdController implements Initializable {

    CadProd prod = new CadProd();

    @FXML
    private TableView<Enderecamento> tabViewEnd;

    @FXML
    private TableColumn<Enderecamento, String> tabTon;

    @FXML
    private TableColumn<Enderecamento, String> tabBitola;

    @FXML
    private TableColumn<Enderecamento, Integer> tabRua;

    @FXML
    private TableColumn<Enderecamento, Integer> tabPred;

    @FXML
    private TableColumn<Enderecamento, String> tabObserv;

    @FXML
    private ObservableList<Enderecamento> obsList;

    private void onTableViewAdd(Integer codInt) {
        List<Enderecamento> list = DAOEnderecamento.findAllView(codInt);
        tabTon.setCellValueFactory(new PropertyValueFactory<>("Ton"));
        tabBitola.setCellValueFactory(new PropertyValueFactory<>("Bitola"));
        tabRua.setCellValueFactory(new PropertyValueFactory<>("Rua"));
        tabPred.setCellValueFactory(new PropertyValueFactory<>("Predio"));
        tabObserv.setCellValueFactory(new PropertyValueFactory<>("Observacao"));

        obsList = FXCollections.observableArrayList(list);
        tabViewEnd.setItems(obsList);
    }

    private void onTableViewAdd(String codBarras) {
        List<Enderecamento> list = DAOEnderecamento.findAllViewBarras(codBarras);
        tabTon.setCellValueFactory(new PropertyValueFactory<>("Ton"));
        tabBitola.setCellValueFactory(new PropertyValueFactory<>("Bitola"));
        tabRua.setCellValueFactory(new PropertyValueFactory<>("Rua"));
        tabPred.setCellValueFactory(new PropertyValueFactory<>("Predio"));
        tabObserv.setCellValueFactory(new PropertyValueFactory<>("Observacao"));

        obsList = FXCollections.observableArrayList(list);
        tabViewEnd.setItems(obsList);
    }

    @FXML
    private TextField txtCodInt;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtQuantMinVenda;

    @FXML
    private TextField txtCodBarras;

    @FXML
    private TextField txtFornecedor;

    @FXML
    private TextField txtEstoque;

    @FXML
    private TextField txtCodBarrasBusca;

    @FXML
    private TextField txtCodIntBusca;

    @FXML
    private TextField txtCusto;

    @FXML
    private TextField txtCodFab;

    @FXML
    private TextField txtCxPallet;

    @FXML
    private Button btnConsultar;

    @FXML
    private Button btnCancelar;

    @FXML
    public void onBtnCancelar(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @FXML
    private void onConsultarProd() {
        tabViewEnd.refresh();
        CadProd prod = new CadProd();
        int a = 0;
        if (txtCodIntBusca.getText().isEmpty() && txtCodBarrasBusca.getText().isEmpty()) {
            Alerts.showAlert("Consulta de produto", null, "Campos de busca não podem estar vazios.", Alert.AlertType.ERROR);
            txtCodInt.clear();
            txtDescricao.clear();
            txtQuantMinVenda.clear();
            txtCodBarras.clear();
            txtFornecedor.clear();
            txtEstoque.clear();
            txtCusto.clear();
            txtCodFab.clear();
            txtCxPallet.clear();
            a++;
        }
        if (a == 0) {
            if (txtCodIntBusca.getText().isEmpty()) {

            } else {
                Integer codInt = Integer.valueOf(txtCodIntBusca.getText());
                prod = DAOProd.encontrarProd(codInt);
                onTableViewAdd(codInt);
            }
            if (txtCodBarrasBusca.getText().isEmpty()) {

            } else {
                String codBarras = txtCodBarrasBusca.getText();
                prod = DAOProd.encontrarProdBarras(codBarras);
                onTableViewAdd(codBarras);
            }

            if (prod == null) {
                Alerts.showAlert("Consulta produto", null, "Produto não localizado.", Alert.AlertType.ERROR);
                txtCodInt.clear();
                txtDescricao.clear();
                txtQuantMinVenda.clear();
                txtCodBarras.clear();
                txtFornecedor.clear();
                txtEstoque.clear();
                txtCusto.clear();
                txtCodFab.clear();
                txtCxPallet.clear();
            } else {
                txtCodInt.setText(String.valueOf(prod.getCodInterno()));
                txtDescricao.setText(prod.getDescricao());
                txtQuantMinVenda.setText(String.valueOf(prod.getQuantMinVenda()));
                txtCodBarras.setText(prod.getCodBarras());
                txtFornecedor.setText(prod.getFornecedor());
                txtEstoque.setText(String.valueOf(prod.getEstoque()));
                txtCusto.setText(String.valueOf(prod.getCusto()));
                txtCodFab.setText(prod.getCodFabricante());
                txtCxPallet.setText(String.valueOf(prod.getQuantCxPallet()));
            }
        }

    }

    @FXML
    private Button btnConsulta;

    @FXML
    private void onConsulta() {
        createDialogForm2("/Views/NovoConsulta.fxml", "Consultar produto");
    }

    @FXML
    private Button btnAlterar;

    @FXML
    private void onAlterar() {
        btnAlterar.setOnAction(evento -> {
            Enderecamento end = (Enderecamento) tabViewEnd.getSelectionModel().getSelectedItem();
            if (tabViewEnd.getSelectionModel().getSelectedItem() == null) {
                Alerts.showAlert("Editar produto", null, "Nenhum produto selecionado.", Alert.AlertType.INFORMATION);
            } else {
                createDialogForm3("/Views/NovoEndEditar.fxml", "Editar endereçamento", end);
            }
        });
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
            txtCodIntBusca.setText(String.valueOf(prod.getCodInterno()));
            onConsultarProd();

        } catch (IOException e) {
            Alerts.showAlert("Io Exception", "Error Load View", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void createDialogForm3(String absoluteName, String title, Enderecamento end) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            loader.setController(new NovoEndEditarController(end));
            Pane pane = loader.load();
            NovoEndEditarController controller = loader.getController();

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
            onConsultarProd();

        } catch (IOException e) {
            Alerts.showAlert("Io Exception", "Error Load View", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Constraints.setTextFieldInteger(txtCodIntBusca);
        Constraints.setTextFieldMaxLength(txtCodBarrasBusca, 20);
        onAlterar();

        btnConsultar.setDefaultButton(true);
        btnConsultar.setOnAction((event) -> {
            onConsultarProd();
        });

        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onBtnCancelar(event);

        });
        txtCodIntBusca.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {

                onConsultarProd();
            }
        });
        txtCodBarrasBusca.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {

                onConsultarProd();
            }
        });

    }
}
