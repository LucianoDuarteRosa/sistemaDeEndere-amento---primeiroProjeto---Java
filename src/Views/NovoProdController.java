package Views;

import Utils.Alerts;
import Entidades.CadProd;
import Entidades.Fornecedor;
import Utils.Util;
import bdProd.DAOProd;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Utils.Constraints;
import bdProd.DAOFornecedor;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

public class NovoProdController implements Initializable {

    @FXML
    private ObservableList<Fornecedor> obsList;

    @FXML
    private TextField codInt;
    
    @FXML
    private TextField precoCusto;
    
    @FXML
    private TextField codFab;
    
    @FXML
    private TextField qntCxPallet;

    @FXML
    private TextField descricao;

    @FXML
    private TextField quantMinVenda;

    @FXML
    private TextField codBarras;

    @FXML
    private ComboBox comboBoxFornecedor;

    @FXML
    private TextField estoque;

    @FXML
    private Button btnAdd;

    @FXML
    public void onBtnAddAciton() {
        try {
            if (descricao.getText() == null || descricao.getText().trim().equals("")
                    || codBarras.getText() == null || codBarras.getText().trim().equals("")
                    || comboBoxFornecedor.getSelectionModel().getSelectedItem() == null
                    || codFab.getText() == null || codFab.getText().trim().equals("")
                    ) {
                Alerts.showAlert("Adicionar novo produto", null, "Todos os campos devem ser preenchidos.", Alert.AlertType.ERROR);

            } else {             
                Integer c = Integer.valueOf(codInt.getText());
                if (DAOProd.encontrarProd(c)!=null){
                Alerts.showAlert("Adicionar novo produto", null, "Produto já cadastrado", Alert.AlertType.ERROR);
                }else{
                String d = descricao.getText();
                Double q = Double.valueOf(quantMinVenda.getText());
                String cod = codBarras.getText();               
                Fornecedor fornecedor = (Fornecedor) comboBoxFornecedor.getSelectionModel().getSelectedItem();
                String f = fornecedor.getRazaoSocial();
                Double e = Double.valueOf(estoque.getText());
                Integer cx = Integer.valueOf(qntCxPallet.getText());
                Double custo = Double.valueOf(precoCusto.getText());
                String fab = codFab.getText();
                
                CadProd prod = new CadProd(c, d, q, cod, f, e , cx , custo , fab);
                DAOProd.addProdCad(prod);
                }
            }
        } catch (RuntimeException e) {
            Alerts.showAlert("Adicionar novo produto", null, "Todos os campos devem ser preenchidos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private Button btnCancelar;

    @FXML
    public void onBtnCancelarAciton(ActionEvent event) {
        Util.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Constraints.setTextFieldInteger(codInt);
        Constraints.setTextFieldMaxLength(descricao, 60);
        Constraints.setTextFieldUppercase(descricao);
        Constraints.setTextFieldDouble(quantMinVenda);
        Constraints.setTextFieldMaxLength(codBarras, 20);
        Constraints.setTextFieldUppercase(codBarras);
        Constraints.setTextFieldDouble(estoque);
        Constraints.setTextFieldDouble(precoCusto);
        Constraints.setTextFieldInteger(qntCxPallet);

        List<Fornecedor> list = DAOFornecedor.encontrarFornecedor();
        obsList = FXCollections.observableArrayList(list);
        comboBoxFornecedor.setItems(obsList);
        
        btnAdd.setDefaultButton(true);
        btnAdd.setOnAction((event) -> {
            onBtnAddAciton();
        });
        btnCancelar.setCancelButton(true);
        btnCancelar.setOnAction((event) -> {
            onBtnCancelarAciton(event);
        });
    }
}
