package Views;

import Entidades.Fornecedor;
import Utils.Alerts;
import Utils.Util;
import bdProd.DAOInventario;
import bdProd.DB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListaNovoInventarioController implements Initializable {

    Fornecedor fornecedor = new Fornecedor();
    List<Fornecedor> list = new ArrayList<>();

    @FXML
    private Button btnConsultar;

    @FXML
    private void btnConsultar() {
        createDialogForm2("/Views/ListaNovoInventarioConsulta.fxml", "Consultar fornecedor");
    }

    @FXML
    private Button btnImprimir;

    @FXML
    private void btnImprimir() throws IOException {
        DAOInventario.inventario(this.list);

    }

    @FXML
    private Button btnCancelar;

    @FXML
    public void btnCancelar(ActionEvent event) {
        Util.currentStage(event).close();
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

        tbId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tbRazaoSocial.setCellValueFactory(new PropertyValueFactory<>("RazaoSocial"));
        tbCNPJ.setCellValueFactory(new PropertyValueFactory<>("CNPJ"));

        obsList = FXCollections.observableArrayList(list);
        tbResult.setItems(obsList);
    }

    private void createDialogForm2(String absoluteName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            loader.setController(new ListaNovoInventarioConsultaController(fornecedor));
            Pane pane = loader.load();
            ListaNovoInventarioConsultaController controler = loader.getController();

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
            List<Fornecedor> list = new ArrayList<>(controler.getList());
            this.list = list;
            onTableViewAdd();

        } catch (IOException e) {
            Alerts.showAlert("Io Exception", "Error Load View", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            btnCancelar(event);
        });

    }

}
