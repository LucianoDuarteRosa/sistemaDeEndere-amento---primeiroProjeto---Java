package Views;

import Entidades.Fornecedor;
import Utils.Alerts;
import Utils.Constraints;
import Utils.Util;
import bdProd.DAOFornecedor;
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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NovoCadFornecedorController implements Initializable {

    Fornecedor fornecedor = new Fornecedor();

    @FXML
    private TextField txtRazaoSocial;

    @FXML
    private TextField txtCNPJ;

    @FXML
    private Button btnNovo;

    @FXML
    private void btnNovo() {
        txtCNPJ.clear();
        txtRazaoSocial.clear();
        txtCNPJ.setDisable(false);
        txtRazaoSocial.setDisable(false);
        btnAtualizar.setDisable(true);
        btnEditar.setDisable(true);
        btnAdicionar.setDisable(false);
        
    }

    @FXML
    private Button btnConsultar;

    @FXML
    private void btnConsultar() {
        createDialogForm2("/Views/NovoCadFornecedorConsulta.fxml", "Consultar fornecedor");
    }

    @FXML
    private Button btnAdicionar;

    @FXML
    private void btnAdicionar() {
        if (txtCNPJ.getText().isEmpty() || txtRazaoSocial.getText().isEmpty()) {
            Alerts.showAlert("Adicionar fornecedor", null, "Campos não podem estar vazios.", Alert.AlertType.WARNING);
        } else {
            Fornecedor fornecedor = new Fornecedor(txtRazaoSocial.getText(), txtCNPJ.getText());
            DAOFornecedor.addFornecedor(fornecedor);
            Alerts.showAlert("Adicionar fornecedor", null, "Fornecedor adicionado com sucesso.", Alert.AlertType.INFORMATION);
            txtCNPJ.setDisable(true);
            txtRazaoSocial.setDisable(true);
            txtCNPJ.clear();
            txtRazaoSocial.clear();
        }
    }
    
    @FXML
    private Button btnEditar;
    
    @FXML
    private void btnEditar(){
        if(txtCNPJ.getText().isEmpty() || txtRazaoSocial.getText().isEmpty()) {
            Alerts.showAlert("Atualizar fornecedor", null, "Nenhum fornecedor selecionado. Lozalize na pesquisa", Alert.AlertType.WARNING);
        }else{
            txtCNPJ.setDisable(false);
            txtRazaoSocial.setDisable(false);
            btnAdicionar.setDisable(true);
            btnConsultar.setDisable(true);
        }
    }

    @FXML
    private Button btnAtualizar;

    @FXML
    public void btnAtualizar() {
        if (txtCNPJ.getText().isEmpty() || txtRazaoSocial.getText().isEmpty()) {
            Alerts.showAlert("Adicionar fornecedor", null, "Campos não podem estar vazios.", Alert.AlertType.WARNING);
        } else {
            Fornecedor fornecedor = new Fornecedor(txtRazaoSocial.getText(), txtCNPJ.getText());
            fornecedor.setId(this.fornecedor.getId());
            DAOFornecedor.update(fornecedor);
            txtCNPJ.setDisable(true);
            txtRazaoSocial.setDisable(true);
            txtCNPJ.clear();
            txtRazaoSocial.clear();
            btnAtualizar.setDisable(true);
            btnEditar.setDisable(true);
            btnConsultar.setDisable(false);
            
        }
    }
    
    @FXML
    private Button btnCancelar;

    @FXML
    public void onBtnCancelar(ActionEvent event) {
        Util.currentStage(event).close();
    }

    private void createDialogForm2(String absoluteName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            loader.setController(new NovoCadFornecedorConsultaController(fornecedor));
            Pane pane = loader.load();
            NovoCadFornecedorConsultaController controler = loader.getController();

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
            fornecedor = controler.getFornecedor();
            atualizar();
            if(fornecedor != null){
                btnEditar.setDisable(false);
                btnAtualizar.setDisable(false);
                btnAdicionar.setDisable(true);
            }
        } catch (IOException e) {
            Alerts.showAlert("Io Exception", "Error Load View", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void atualizar(){
        if(fornecedor.getId() != null)
        txtRazaoSocial.setText(this.fornecedor.getRazaoSocial());
        txtCNPJ.setText(this.fornecedor.getCNPJ());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onBtnCancelar(event);
        });
        Constraints.setTextFieldMaxLength(txtCNPJ, 18);
        Constraints.setTextFieldMaxLength(txtRazaoSocial, 60);
        Constraints.setTextFieldUppercase(txtRazaoSocial);
        
        atualizar();
    }
 
}
