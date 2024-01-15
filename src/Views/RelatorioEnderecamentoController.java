package Views;

import Entidades.Endereco;
import Entidades.Fornecedor;
import bdProd.DAOEnderecamento;
import static bdProd.DAOEnderecamento.relatorioQntPalletFornecedor;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RelatorioEnderecamentoController implements Initializable {

    @FXML
    private ComboBox comboRelatorio;

    @FXML
    private ObservableList<String> obsList;

    @FXML
    private Button btnCarregar;

    @FXML
    private void onCarregar() {
        if (comboRelatorio.getSelectionModel().getSelectedItem() != null) {
            String relatorio = (String) comboRelatorio.getSelectionModel().getSelectedItem();
            if (relatorio.equals("1 - Total de pallet por fornecedor")) {
                tbFornecedor.setVisible(true);
                tbRuaPredio.setVisible(false);
                onTableViewAdd();
            } else if (relatorio.equals("2 - Total de pallet por rua e prédio")) {
                tbFornecedor.setVisible(false);
                tbRuaPredio.setVisible(true);
                onTableViewAdd1();
            } 
        }
    }

    @FXML
    private Button btnCancelar;

    @FXML
    private void onCancelar(ActionEvent event) {
        Utils.Util.currentStage(event).close();
    }

    @FXML
    private TableView<Fornecedor> tbFornecedor;

    @FXML
    private TableColumn<Fornecedor, String> tcFornecedor;

    @FXML
    private TableColumn<Fornecedor, Integer> tcQuantPallet;

    private ObservableList<Fornecedor> obsList0;

    private void onTableViewAdd() {
        List<Fornecedor> list = relatorioQntPalletFornecedor();
        tcFornecedor.setCellValueFactory(new PropertyValueFactory<>("RazaoSocial"));
        tcQuantPallet.setCellValueFactory(new PropertyValueFactory<>("QuantPallet"));

        obsList0 = FXCollections.observableArrayList(list);
        tbFornecedor.setItems(obsList0);
    }

    @FXML
    private TableView<Endereco> tbRuaPredio;

    @FXML
    private TableColumn<Endereco, Integer> tcRua;

    @FXML
    private TableColumn<Endereco, Integer> tcPredio;

    @FXML
    private TableColumn<Endereco, Integer> tcPallets;
    
    @FXML
    private TableColumn<Endereco, Integer> tcVagas;

    private ObservableList<Endereco> obsList1;

    private void onTableViewAdd1() {
        List<Endereco> list = DAOEnderecamento.relatorioQntPalletRuaPredio();
        tcRua.setCellValueFactory(new PropertyValueFactory<>("Rua"));
        tcPredio.setCellValueFactory(new PropertyValueFactory<>("Predio"));
        tcPallets.setCellValueFactory(new PropertyValueFactory<>("QuantPallet"));
        tcVagas.setCellValueFactory(new PropertyValueFactory<>("Vagas"));

        obsList1 = FXCollections.observableArrayList(list);
        tbRuaPredio.setItems(obsList1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<String> list = new ArrayList<>();
        list.add("1 - Total de pallet por fornecedor");
        list.add("2 - Total de pallet por rua e prédio");
        obsList = FXCollections.observableArrayList(list);
        comboRelatorio.setItems(obsList);

        btnCarregar.setDefaultButton(true);
        btnCarregar.setOnAction((event) -> {
            onCarregar();
        });
        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onCancelar(event);
        });

    }

}
