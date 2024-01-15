package Views;

import Entidades.Enderecamento;
import Entidades.Endereco;
import Utils.Alerts;
import Utils.Constraints;
import Utils.Util;
import bdProd.DAOEnd;
import bdProd.DAOEnderecamento;
import bdProd.DB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NovoEnderecamentoAltController implements Initializable {

    @FXML
    private TextField txtRuaNovo;

    @FXML
    private TextField txtPredioNovo;

    @FXML
    private TextField txtRua;

    @FXML
    private TextField txtPredio;

    @FXML
    private Button btnBusca;

    @FXML
    public void onBusca() {
        Endereco end = new Endereco();
        if (txtRua.getText().isEmpty() || txtPredio.getText().isEmpty()) {
            Alerts.showAlert("Alterar/Excluir endereçamento", null, "Os dois campos de busca devem ser preenchidos.", Alert.AlertType.ERROR);
            if (obsList != null) {
                obsList.clear();
            }
        } else {
            end.setRua(Integer.parseInt(txtRua.getText()));
            end.setPredio(Integer.parseInt(txtPredio.getText()));
            Endereco end1 = DAOEnderecamento.findAllEnd(end);
            if (end1.getPredio() == null || end1.getPredio() == 0) {
                Alerts.showAlert("Alterar/Excluir endereçamento", null, "Nenhuma informação cadastrada do endereço pesquisado ou endereço inexistente.", Alert.AlertType.INFORMATION);
                obsList.clear();
            } else {
                onTableViewAdd(end);
            }
        }
    }

    @FXML
    private Button btnCancelar;

    @FXML
    private void onCancelar(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @FXML
    private Button btnAlterar;

    @FXML
    private void onAlterar() {
        btnAlterar.setOnAction(event -> {
            Enderecamento enderecamento = tabViewEnd.getSelectionModel().getSelectedItem();
            if (tabViewEnd.getSelectionModel().getSelectedItem() == null) {
                Alerts.showAlert("Alterar endereço", null, "Nenhum produto selecionado.", Alert.AlertType.INFORMATION);
            } else {
                if (txtRuaNovo.getText().isEmpty() || txtPredioNovo.getText().isEmpty()) {
                    Alerts.showAlert("Alterar endereço", null, "Campos de novo endereço podem estar vazios.", Alert.AlertType.ERROR);
                }
                Endereco end = new Endereco(Integer.parseInt(txtRuaNovo.getText()), Integer.parseInt(txtPredioNovo.getText()));
                end = DAOEnd.encontrarEnd(end);
                if (end == null) {
                    Alerts.showAlert("Alterar endereço", null, "Rua ou prédio inexistente. Verefique e tente novamente.", Alert.AlertType.ERROR);
                } else {
                    enderecamento.setRua(Integer.parseInt(txtRuaNovo.getText()));
                    enderecamento.setPredio(Integer.parseInt(txtPredioNovo.getText()));
                    DAOEnderecamento.updateEnd(enderecamento);
                    Alerts.showAlert("Alterar endereçamento", null, "Produto alterado com sucesso.", Alert.AlertType.INFORMATION);
                    Endereco end1 = new Endereco(Integer.parseInt(txtRua.getText()), Integer.parseInt(txtPredio.getText()));
                    onTableViewAdd(end1);
                }
            }
        });
    }

    @FXML
    private Button btnRemover;

    @FXML
    private void onRemover() {
        btnRemover.setOnAction(event -> {
            Enderecamento enderecamento = tabViewEnd.getSelectionModel().getSelectedItem();
            if (tabViewEnd.getSelectionModel().getSelectedItem() == null) {
                Alerts.showAlert("Remover produto", null, "Nenhum produto selecionado.", Alert.AlertType.INFORMATION);
            } else {
                Optional<ButtonType> result = Alerts.showConfirmation("Excluir o endereçamento", "Tem certeza que deseja remover?");
                if (result.get() == ButtonType.OK) {
                    DAOEnderecamento.updateIndisp(enderecamento);
                    Alerts.showAlert("Remover endereçamento", null, "Produto removido com sucesso.", Alert.AlertType.INFORMATION);
                    Endereco end = new Endereco(Integer.parseInt(txtRua.getText()), Integer.parseInt(txtPredio.getText()));
                    onTableViewAdd(end);
                }
            }
        });
    }

    @FXML
    private Button btnEditar;

    @FXML
    private void onEditar() {
        btnEditar.setOnAction(evento -> {
            Enderecamento end = (Enderecamento) tabViewEnd.getSelectionModel().getSelectedItem();
            if (tabViewEnd.getSelectionModel().getSelectedItem() == null) {
                Alerts.showAlert("Editar produto", null, "Nenhum produto selecionado.", Alert.AlertType.INFORMATION);
            } else {
                createDialogForm2("/Views/NovoEndEditar.fxml", "Editar endereçamento", end);
            }
        });
    }

    @FXML
    private TableView<Enderecamento> tabViewEnd;

    @FXML
    private TableColumn<Enderecamento, Integer> tabCodInt;

    @FXML
    private TableColumn<Enderecamento, String> tabDesc;

    @FXML
    private TableColumn<Enderecamento, String> tabCodFab;

    @FXML
    private TableColumn<Enderecamento, Double> tabQntMinVenda;

    @FXML
    private TableColumn<Enderecamento, String> tabTon;

    @FXML
    private TableColumn<Enderecamento, String> tabBitola;

    @FXML
    private TableColumn<Enderecamento, Integer> tabId;

    @FXML
    private TableColumn<Enderecamento, String> tabObserv;

    @FXML
    private ObservableList<Enderecamento> obsList;

    private void onTableViewAdd(Endereco end) {
        List<Enderecamento> list = DAOEnderecamento.findAllEndereco(end);
        tabId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tabCodInt.setCellValueFactory(new PropertyValueFactory<>("CodInt"));
        tabDesc.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        tabCodFab.setCellValueFactory(new PropertyValueFactory<>("CodFab"));
        tabQntMinVenda.setCellValueFactory(new PropertyValueFactory<>("QuantMinVenda"));
        tabTon.setCellValueFactory(new PropertyValueFactory<>("Ton"));
        tabBitola.setCellValueFactory(new PropertyValueFactory<>("Bitola"));
        tabObserv.setCellValueFactory(new PropertyValueFactory<>("Observacao"));

        obsList = FXCollections.observableArrayList(list);
        tabViewEnd.setItems(obsList);
    }

    private void createDialogForm2(String absoluteName, String title, Enderecamento end) {
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
            onBusca();

        } catch (IOException e) {
            Alerts.showAlert("Io Exception", "Error Load View", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Constraints.setTextFieldInteger(txtRua);
        Constraints.setTextFieldInteger(txtPredio);
        Constraints.setTextFieldInteger(txtRuaNovo);
        Constraints.setTextFieldInteger(txtPredioNovo);

        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onCancelar(event);
        });

        btnBusca.setDefaultButton(true);
        btnBusca.setOnAction((event) -> {
            onBusca();
        });
        onAlterar();
        onRemover();
        onEditar();
    }

}
