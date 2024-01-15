package Views;

import Utils.Alerts;
import bdProd.DB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem importCsv;

    @FXML
    public void onImportCsvAction() {
        createDialogForm("/Views/ImportCsv.fxml", "Importar produtos");
    }

    @FXML
    private MenuItem attEstoque;

    @FXML
    public void onAttEstoqueAction() {
        createDialogForm("/Views/ImportAttEstoque.fxml", "Atualizar estoque");
    }

    @FXML
    private MenuItem importFornecedor;

    @FXML
    public void onImportFornecedor() {
        createDialogForm("/Views/ImportFornecedor.fxml", "Importar fornecedor");
    }

    @FXML
    private MenuItem novoProd;

    @FXML
    public void onNovoProdAction() {
        createDialogForm("/Views/NovoProd.fxml", "Adicionar produto");
    }

    @FXML
    private MenuItem novoConsulta;

    @FXML
    public void onNovoConsultaAction() {
        createDialogForm("/Views/NovoConsultaProd.fxml", "Consultar produto");
    }

    @FXML
    private MenuItem novoCxPallet;

    @FXML
    public void onNovoCxPallet() {
        createDialogForm("/Views/NovoCxPallet.fxml", "Quantidade de caixas no pallet");
    }

    @FXML
    private MenuItem novoCadPredio;

    @FXML
    private void onNovoCadPredio() {
        createDialogForm("/Views/NovoCadPredio.fxml", "Adicionar prédio");
    }

    @FXML
    private MenuItem novoCadRua;

    @FXML
    private void onNovoCadRua() {
        createDialogForm("/Views/NovoCadRua.fxml", "Adicionar rua");
    }

    @FXML
    private MenuItem novoExluir;

    @FXML
    private void onNovoEndExluir() {
        createDialogForm("/Views/NovoEndExluir.fxml", "Excluir/Alterar endereço");
    }

    @FXML
    private MenuItem enderecamento;

    @FXML
    public void onEnderecamentoAction() {
        createDialogForm("/Views/NovoEnderecamento.fxml", "Adicionar endereçamento");
    }

    @FXML
    private MenuItem novoFornecedor;

    @FXML
    public void onNovoFornecedor() {
        createDialogForm("/Views/NovoCadFornecedor.fxml", "Adicionar fornecdor");
    }

    @FXML
    private MenuItem enderecamentoAlt;

    @FXML
    public void onEnderecamentoAlt() {
        createDialogForm("/Views/NovoEnderecamentoAlt.fxml", "Alterar endereçamento");
    }

    @FXML
    private MenuItem novaLista;

    @FXML
    public void onNovaListaAction() {
        createDialogForm("/Views/ListaNovaLista.fxml", "Adicionar lista");
    }

    @FXML
    private MenuItem novoInventario;

    @FXML
    public void onNovoInventario() {
        createDialogForm("/Views/ListaNovoInventario.fxml", "Adicionar inventário");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private MenuItem relatorioEnderecamento;

    @FXML
    public void relatorioEnderecamento() {
        createDialogForm("/Views/RelatorioEnderecamento.fxml", "Relatórios Endereçamento");
    }

    @FXML
    private MenuItem relatorioExpEnderecamento;

    @FXML
    public void relatorioExpEnderecamento() {
        createDialogForm("/Views/RelatorioExpEnderecamento.fxml", "Exportar Endereçamento");
    }

    @FXML
    private MenuItem config;

    @FXML
    public void onConfig() {
        createDialogForm("/Views/Configuracao.fxml", "Configuração");
    }

    private void createDialogForm(String absoluteName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
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
        } catch (IOException e) {
            Alerts.showAlert("Io Exception", "Error Load View", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static Pane momentPane(Pane pane) {
        return pane;
    }

}
