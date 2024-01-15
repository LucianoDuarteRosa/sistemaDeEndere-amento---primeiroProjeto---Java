package Views;

import Entidades.Enderecamento;
import Entidades.Endereco;
import static Utils.Impressao.onImpressao;
import Entidades.Lista;
import Utils.Alerts;
import Utils.Util;
import bdProd.DAOLista;
import bdProd.DB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListaNovaListaController implements Initializable {

    @FXML
    private Label lblLista;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnAddProd;

    @FXML
    private void onAddProd() {
        createDialogForm("/Views/ListaAddProd.fxml", "Adicionar produto na lista");
    }

    @FXML
    private Button btnRemProd;

    @FXML
    private void onRemProd() {
        btnRemProd.setOnAction(event -> {
            Enderecamento end = (Enderecamento) tbResult.getSelectionModel().getSelectedItem();
            if (tbResult.getSelectionModel().getSelectedItem() == null) {
                Alerts.showAlert("Remover produto", null, "Nenhum produto selecionado.", Alert.AlertType.INFORMATION);
            } else {
                DAOLista.updateRemove(end);
                Alerts.showAlert("Remover produto na lista", null, "Produto removido com sucesso.", Alert.AlertType.INFORMATION);
                onCarregar();
            }
        });
    }

    @FXML
    private Button btnNovaLista;

    Lista lista = new Lista();

    @FXML
    private void onNovaLista() {

        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHoraString = dataHora.format(formatter);
        lista.setNome(dataHoraString);
        lista.setDisponivel(true);
        lista = DAOLista.addList(lista);
        lblDate.setText(lista.getNome());
        lblLista.setText(String.valueOf(lista.getId()));
        String result = String.valueOf(lista.getDisponivel());
        if (result == "true") {
            result = "Disponível";
        } else {
            result = "Finalizada";
        }
        lblStatus.setText(result);

        btnAddProd.setDisable(false);
        btnRemProd.setDisable(false);

        onCarregar();
    }

    private void onConsultaIni(Lista lista) {

        lblDate.setText(lista.getNome());
        lblLista.setText(String.valueOf(lista.getId()));
        String result = String.valueOf(lista.getDisponivel());
        if (result == "false") {
            result = "Finalizada";
            lblStatus.setStyle("-fx-text-fill: red;");
        } else {
            result = "Disponível";
            lblStatus.setStyle("-fx-text-fill: black;");
        }
        lblStatus.setText(result);
    }

    @FXML
    private void onCarregar() {
        if (lista.getDisponivel() == true) {
            onTableViewAdd(lista);
        } else {
            onTableViewAddFinal(lista);
        }

    }

    @FXML
    private Button btnListaConsulta;

    @FXML
    private void onListaConsulta() {
        createDialogForm2("/Views/ListaConsultarListas.fxml", "Consultar listas");
    }

    @FXML
    private Button btnImprimir;

    @FXML
    private void onImprimir() throws IOException, FileNotFoundException, SQLException {
        onImpressao(lista);
    }

    @FXML
    private Button btnDesfazerFinal;

    @FXML
    private void onDesfazerFinal() {
        if (lista.getId() == null) {
            Alerts.showAlert("Desfazer finalização", null, "Nenhuma lista selecionada.", Alert.AlertType.INFORMATION);
        } else {
            if (lista.getDisponivel() == true) {
                Alerts.showAlert("Desfazer finalização", null, "Lista já disponível para edição.", Alert.AlertType.INFORMATION);
            } else {
                lista = DAOLista.updateListaDesfFinal(lista);
                onConsultaIni(lista);
                btnAddProd.setDisable(false);
                btnRemProd.setDisable(false);
                onCarregar();
            }
        }
    }

    @FXML
    private Button btnFinal;

    @FXML
    private void onFinalizar() {
        DAOLista.updateListaFinal(lista);
        lblLista.setText("");
        lblDate.setText("");
        lblStatus.setText("");
        obsList.clear();
        btnAddProd.setDisable(true);
        btnRemProd.setDisable(true);
        lista.setId(null);

    }

    @FXML
    private Button btnCancelar;

    @FXML
    private void onCancelar(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @FXML
    private Button btnExcluir;

    @FXML
    private void onExcluir() {
        if (lista.getDisponivel() == true) {
            Optional<ButtonType> result = Alerts.showConfirmation("Excluir lista", "Tem certeza que deseja exlcuir?");
            if (result.get() == ButtonType.OK) {
                DAOLista.updateRemoveList(lista);
                DAOLista.deleteListaId(lista.getId());
                lblLista.setText("");
                lblDate.setText("");
                lblStatus.setText("");
                obsList.clear();
                lista.setId(null);
                btnAddProd.setDisable(true);
                btnRemProd.setDisable(true);
            }
        } else {
            Alerts.showAlert("Excluir lista", null, "Lista finalizada não pode ser excluída.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private TableView<Enderecamento> tbResult;

    @FXML
    private TableColumn<Enderecamento, Integer> tbCodInt;

    @FXML
    private TableColumn<Enderecamento, String> tbDescricao;

    @FXML
    private TableColumn<Enderecamento, String> tbCodFab;

    @FXML
    private TableColumn<Enderecamento, String> tbTon;

    @FXML
    private TableColumn<Enderecamento, String> tbBit;

    @FXML
    private TableColumn<Enderecamento, Integer> tbRua;

    @FXML
    private TableColumn<Enderecamento, Integer> tbPredio;

    @FXML
    private TableColumn<Enderecamento, String> tbObservacao;

    @FXML
    private ObservableList<Enderecamento> obsList;

    private ObservableList<Endereco> obsList2;

    private void onTableViewAdd(Lista lista) {
        List<Enderecamento> list = DAOLista.findAllList(lista);
        tbCodInt.setCellValueFactory(new PropertyValueFactory<>("CodInt"));
        tbDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        tbCodFab.setCellValueFactory(new PropertyValueFactory<>("CodFab"));
        tbTon.setCellValueFactory(new PropertyValueFactory<>("Ton"));
        tbBit.setCellValueFactory(new PropertyValueFactory<>("Bitola"));
        tbRua.setCellValueFactory(new PropertyValueFactory<>("Rua"));
        tbPredio.setCellValueFactory(new PropertyValueFactory<>("Predio"));
        tbObservacao.setCellValueFactory(new PropertyValueFactory<>("Observacao"));

        obsList = FXCollections.observableArrayList(list);
        tbResult.setItems(obsList);
    }

    private void onTableViewAddFinal(Lista lista) {
        List<Enderecamento> list = DAOLista.findAllListFinal(lista);
        tbCodInt.setCellValueFactory(new PropertyValueFactory<>("CodInt"));
        tbDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        tbTon.setCellValueFactory(new PropertyValueFactory<>("Ton"));
        tbBit.setCellValueFactory(new PropertyValueFactory<>("Bitola"));
        tbRua.setCellValueFactory(new PropertyValueFactory<>("Rua"));
        tbPredio.setCellValueFactory(new PropertyValueFactory<>("Predio"));
        tbObservacao.setCellValueFactory(new PropertyValueFactory<>("Observacao"));

        obsList = FXCollections.observableArrayList(list);
        tbResult.setItems(obsList);
        btnAddProd.setDisable(true);
        btnRemProd.setDisable(true);

    }

    private void createDialogForm(String absoluteName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            loader.setController(new ListaAddProdController(lista));
            Pane pane = loader.load();

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
            onCarregar();

        } catch (IOException e) {
            Alerts.showAlert("Io Exception", "Erro ao carregar tela.", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void createDialogForm2(String absoluteName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            loader.setController(new ListaConsultarListasController(lista));
            Pane pane = loader.load();
            ListaConsultarListasController controler = loader.getController();

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
            lista = controler.getLista();
            if (lista.getId() != null) {
                onConsultaIni(lista);
                onCarregar();
                if (lista.getDisponivel() == true) {
                    btnAddProd.setDisable(false);
                    btnRemProd.setDisable(false);
                }
            }

        } catch (IOException e) {
            Alerts.showAlert("Io Exception", "Error Load View", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        onRemProd();
        btnNovaLista.requestFocus();

        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onCancelar(event);
        });
    }

}
