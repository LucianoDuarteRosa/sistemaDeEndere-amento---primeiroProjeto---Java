package Views;

import Utils.Alerts;
import Entidades.CadProd;
import Entidades.Enderecamento;
import Entidades.Endereco;
import Utils.Constraints;
import Utils.Util;
import bdProd.DAOEnd;
import bdProd.DAOEnderecamento;
import bdProd.DAOProd;
import bdProd.DB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NovoEnderecamentoController implements Initializable {

    CadProd prod = new CadProd();

    @FXML
    private TextField txtQuant;
    
    @FXML
    private TextField txtCodInt;

    @FXML
    private TextField txtCodBarras;

    @FXML
    private TextField txtCodBarrasResult;

    @FXML
    private TextField txtCodIntResult;

    @FXML
    private TextField txtDescResult;

    @FXML
    private TextField txtCodFabResult;

    @FXML
    private TextField txtQntMinVendaResult;

    @FXML
    private TextField txtFornecedorResult;

    @FXML
    private TextField txtRua;

    @FXML
    private TextField txtPredio;

    @FXML
    private TextField txtTon;

    @FXML
    private TextField txtBit;

    @FXML
    private TextField txtObserv;

    @FXML
    private Button btnConsultar;

    @FXML
    private void onBtnConsultarAcion() {
        if (txtCodInt.getText().isEmpty() && txtCodBarras.getText().isEmpty()) {
            Alerts.showAlert("Consulta de produto", null, "Campos de busca não podem estar vazios.", Alert.AlertType.ERROR);
        }
        if (txtCodInt.getText().isEmpty()) {
        } else {
            CadProd prod = DAOProd.encontrarProd(Integer.parseInt(txtCodInt.getText()));
            if (prod == null) {
                Alerts.showAlert("Consulta de produto", null, "Produto não localizado.", Alert.AlertType.INFORMATION);
                txtCodIntResult.clear();
                txtDescResult.clear();
                txtQntMinVendaResult.clear();
                txtCodBarrasResult.clear();
                txtCodFabResult.clear();
                txtFornecedorResult.clear();
                txtCodInt.clear();
                txtCodBarras.clear();
            } else {
                txtCodIntResult.setText(String.valueOf(prod.getCodInterno()));
                txtDescResult.setText(prod.getDescricao());
                txtQntMinVendaResult.setText(String.valueOf(prod.getQuantMinVenda()));
                txtCodBarrasResult.setText(prod.getCodBarras());
                txtCodFabResult.setText(prod.getCodFabricante());
                txtFornecedorResult.setText(prod.getFornecedor());
            }
        }
        if (txtCodBarras.getText().isEmpty()) {
        } else {
            CadProd prod = DAOProd.encontrarProdBarras(txtCodBarras.getText());
            if (prod == null) {
                Alerts.showAlert("Consulta de produto", null, "Produto não localizado.", Alert.AlertType.INFORMATION);
            }
            txtCodIntResult.setText(String.valueOf(prod.getCodInterno()));
            txtDescResult.setText(prod.getDescricao());
            txtQntMinVendaResult.setText(String.valueOf(prod.getQuantMinVenda()));
            txtCodBarrasResult.setText(prod.getCodBarras());
            txtCodFabResult.setText(prod.getCodFabricante());
            txtFornecedorResult.setText(prod.getFornecedor());

        }
    }

    @FXML
    private Button btnAdicionar;

    @FXML
    private void onBtnAdicionarAcion() {
        if (txtCodIntResult.getText().isEmpty() || txtDescResult.getText().isEmpty() || txtQntMinVendaResult.getText().isEmpty()
                || txtRua.getText().isEmpty() || txtPredio.getText().isEmpty() || txtTon.getText().isEmpty() || txtBit.getText().isEmpty()|| Integer.parseInt(txtQuant.getText())<1) {
            Alerts.showAlert("Adicionar endereço", null, "Todos os dados devem ser preenchidos, exeto o campo de observações.", Alert.AlertType.ERROR);
        } else {
            Endereco end = new Endereco();
            end = DAOEnd.encontrarRua(Integer.parseInt(txtRua.getText()), Integer.parseInt(txtPredio.getText()));
            if (end == null) {
                Alerts.showAlert("Adiconar endereço", null, "Rua ou Prédio não localizado na base de dados.", Alert.AlertType.ERROR);
            } else {
                Enderecamento enderecamento = new Enderecamento(txtObserv.getText(), txtTon.getText(), txtBit.getText(), txtDescResult.getText(), txtCodBarrasResult.getText(),
                        Double.parseDouble(txtQntMinVendaResult.getText()), Integer.parseInt(txtCodIntResult.getText()), Integer.parseInt(txtRua.getText()),
                        Integer.parseInt(txtPredio.getText()), txtFornecedorResult.getText());
                enderecamento.setCodFab(txtCodFabResult.getText());
                int quant = Integer.parseInt(txtQuant.getText());
                for(int i =0 ; i < quant ; i++){
                DAOEnderecamento.addEnderecamento(enderecamento);
                }
                Alerts.showAlert("Adicionar endereço", null, "Endereçamento adicionado com sucesso.", Alert.AlertType.INFORMATION);
            }
            txtCodBarras.clear();
            txtCodInt.clear();
            txtCodIntResult.clear();
            txtDescResult.clear();
            txtQntMinVendaResult.clear();
            txtObserv.clear();
            txtTon.clear();
            txtBit.clear();
            txtQuant.setText("1");
            txtCodInt.requestFocus();
        }
    }
    @FXML
    private Button btnCancelar;

    @FXML
    private void onBtnCancelarAction(ActionEvent event) {
        Util.currentStage(event).close();
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
            this.prod = controller.getCadProd();
            txtCodInt.setText(String.valueOf(prod.getCodInterno()));
            onBtnConsultarAcion();

        } catch (IOException e) {
            Alerts.showAlert("Io Exception", "Error Load View", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        Constraints.setTextFieldInteger(txtCodInt);
        Constraints.setTextFieldInteger(txtCodBarras);
        Constraints.setTextFieldInteger(txtPredio);
        Constraints.setTextFieldInteger(txtRua);
        Constraints.setTextFieldInteger(txtQuant);
        Constraints.setTextFieldMaxLength(txtObserv, 50);
        Constraints.setTextFieldUppercase(txtObserv);
        Constraints.setTextFieldMaxLength(txtTon, 10);
        Constraints.setTextFieldUppercase(txtTon);
        Constraints.setTextFieldMaxLength(txtBit, 5);
        Constraints.setTextFieldUppercase(txtBit);
        
        txtQuant.setText("1");

        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onBtnCancelarAction(event);
        });
        txtCodInt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {

                onBtnConsultarAcion();
            }
        });
        txtCodBarras.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {

                onBtnConsultarAcion();
            }
        });
        btnAdicionar.setDefaultButton(true);
        btnAdicionar.setOnAction((event) -> {
            onBtnAdicionarAcion();
        });

    }

}
