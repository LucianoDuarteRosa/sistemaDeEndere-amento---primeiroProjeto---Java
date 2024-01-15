package Views;

import Entidades.Enderecamento;
import Utils.Alerts;
import Utils.Constraints;
import Utils.Util;
import bdProd.DAOEnderecamento;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NovoEndEditarController implements Initializable {

    Enderecamento end = new Enderecamento();

    public NovoEndEditarController(Enderecamento end) {
        this.end = end;
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
        if (txtBitNovo.getText().isEmpty() && txtTonNovo.getText().isEmpty() && txtObservNovo.getText().isEmpty()
                && txtRuaNovo.getText().isEmpty() && txtPredioNovo.getText().isEmpty()) {
            Alerts.showAlert("Alterar endereçamento", null, "Nenhum campo preenchido para alteração.", Alert.AlertType.INFORMATION);
        } else {
            if (txtBitNovo.getText().isEmpty()) {
            } else {
                end.setBitola(txtBitNovo.getText());
            }
            if (txtTonNovo.getText().isEmpty()) {
            } else {
                end.setTon(txtTonNovo.getText());
            }
            if (txtObservNovo.getText().isEmpty()) {
            } else {
                end.setObservacao(txtObservNovo.getText());
            }
            if (txtRuaNovo.getText().isEmpty()) {
            } else {
                end.setRua(Integer.parseInt(txtRuaNovo.getText()));
            }
            if (txtPredioNovo.getText().isEmpty()) {
            } else {
                end.setPredio(Integer.parseInt(txtPredioNovo.getText()));
            }
            DAOEnderecamento.updateAllEnd(end);
            btnCancelar.fire();
        }
    }

    @FXML
    private TextField txtDescResult;

    @FXML
    private TextField txtCodigoResult;

    @FXML
    private TextField txtRuaResult;

    @FXML
    private TextField txtPredioResult;

    @FXML
    private TextField txtTonResult;

    @FXML
    private TextField txtBitResult;

    @FXML
    private TextField txtObservResult;

    @FXML
    private TextField txtRuaNovo;

    @FXML
    private TextField txtPredioNovo;

    @FXML
    private TextField txtTonNovo;

    @FXML
    private TextField txtBitNovo;

    @FXML
    private TextField txtObservNovo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Constraints.setTextFieldUppercase(txtTonNovo);
        Constraints.setTextFieldUppercase(txtBitNovo);
        Constraints.setTextFieldUppercase(txtObservNovo);
        Constraints.setTextFieldMaxLength(txtObservNovo, 60);
        Constraints.setTextFieldMaxLength(txtTonNovo, 10);
        Constraints.setTextFieldMaxLength(txtBitNovo, 10);
        Constraints.setTextFieldInteger(txtRuaNovo);
        Constraints.setTextFieldInteger(txtPredioNovo);

        txtCodigoResult.setText(String.valueOf(end.getCodInt()));
        txtDescResult.setText(end.getDescricao());
        txtTonResult.setText(end.getTon());
        txtBitResult.setText(end.getBitola());
        txtObservResult.setText(end.getObservacao());
        txtRuaResult.setText(String.valueOf(end.getRua()));
        txtPredioResult.setText(String.valueOf(end.getPredio()));

        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onCancelar(event);
        });

        btnAlterar.setDefaultButton(true);
        btnAlterar.setOnAction((event) -> {
            onAlterar();
        });

    }

}
