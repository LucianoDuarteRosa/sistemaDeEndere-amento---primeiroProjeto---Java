package Views;

import Entidades.CadProd;
import Utils.Alerts;
import Utils.Constraints;
import Utils.Util;
import bdProd.DAOProd;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class NovoCxPalletController implements Initializable {

    CadProd prod = new CadProd();

    @FXML
    private TextField txtCodInt;

    @FXML
    private TextField txtCodBarras;

    @FXML
    private Button btnConsulta;

    @FXML
    public void onConsulta() {

        int a = 0;
        if (txtCodInt.getText().isEmpty() && txtCodBarras.getText().isEmpty()) {
            Alerts.showAlert("Consulta de produto", null, "Campos de busca não podem estar vazios.", Alert.AlertType.ERROR);
            txtCodInt.clear();
            txtCodBarras.clear();
            txtResultCodInt.clear();
            txtResultCxPallet.clear();
            txtResultDesc.clear();
            txtResultEst.clear();
            txtResultMetragem.clear();
            txtAdd.clear();
            a++;
        }
        if (a == 0) {
            if (txtCodInt.getText().isEmpty()) {

            } else {
                Integer codInt = Integer.valueOf(txtCodInt.getText());
                prod = DAOProd.encontrarProd(codInt);
            }
            if (txtCodBarras.getText().isEmpty()) {

            } else {
                String codBarras = txtCodBarras.getText();
                prod = DAOProd.encontrarProdBarras(codBarras);
            }

            if (prod == null) {
                Alerts.showAlert("Consulta produto", null, "Produto não localizado.", Alert.AlertType.ERROR);
                txtCodInt.clear();
                txtCodBarras.clear();
                txtResultCodInt.clear();
                txtResultCxPallet.clear();
                txtResultDesc.clear();
                txtResultEst.clear();
                txtResultMetragem.clear();
                txtAdd.clear();
            } else {
                txtResultCodInt.setText(String.valueOf(prod.getCodInterno()));
                txtResultDesc.setText(prod.getDescricao());
                txtResultMetragem.setText(String.valueOf(prod.getQuantMinVenda()));
                txtResultEst.setText(String.valueOf(prod.getEstoque()));
                if (prod.getQuantCxPallet() == null || prod.getQuantCxPallet() == 0) {
                    txtResultCxPallet.setText("Sem informação cadastrada.");
                } else {
                    txtResultCxPallet.setText(String.valueOf(prod.getQuantCxPallet()));
                }
            }
        }
    }

    @FXML
    private TextField txtResultDesc;

    @FXML
    private TextField txtResultMetragem;

    @FXML
    private TextField txtResultCodInt;

    @FXML
    private TextField txtResultEst;

    @FXML
    private TextField txtResultCxPallet;

    @FXML
    private TextField txtAdd;

    @FXML
    private Button btnAdicionar;

    @FXML
    public void onBtnAdd() {
        if (txtAdd.getText().isEmpty()) {
            Alerts.showAlert("Quantidade caixa no pallet", null, "O Campo de quantidade de caixas não pode estar vazio.", Alert.AlertType.ERROR);
        } else {
            prod.setQuantCxPallet(Integer.parseInt(txtAdd.getText()));
            DAOProd.addQntCx(prod);
            txtCodInt.clear();
            txtCodBarras.clear();
            txtResultCodInt.clear();
            txtResultCxPallet.clear();
            txtResultDesc.clear();
            txtResultEst.clear();
            txtResultMetragem.clear();
            txtAdd.clear();
        }
    }

    @FXML
    private Button btnCancelar;

    @FXML
    public void onBtnCancelar(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnAdicionar.setDefaultButton(true);
        btnAdicionar.setOnAction((event) -> {
            onBtnAdd();
        });

        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onBtnCancelar(event);
        });

        txtCodInt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {

                onConsulta();
            }
        });
        txtCodBarras.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {

                onConsulta();
            }
        });

        Constraints.setTextFieldInteger(txtCodInt);
        Constraints.setTextFieldInteger(txtCodBarras);
        Constraints.setTextFieldInteger(txtAdd);
    }

}
