package bdProd;

import Entidades.Fornecedor;
import Utils.Alerts;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

public class DAOFornecedor {

    public static void addFornecedor(Fornecedor fornecedor) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO fornecedor"
                    + "(RazaoSocial, CNPJ )"
                    + "VALUES"
                    + "(?, ?)");
            st.setString(1, fornecedor.getRazaoSocial());
            st.setString(2, fornecedor.getCNPJ());

            st.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DB.closeStatement(st);
    }

    public static List<Fornecedor> encontrarFornecedor() {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Fornecedor");
            rs = st.executeQuery();

            List<Fornecedor> list = new ArrayList<>();

            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setRazaoSocial(rs.getString("RazaoSocial"));
                fornecedor.setCNPJ(rs.getString("CNPJ"));
                fornecedor.setId(rs.getInt("Id"));
                list.add(fornecedor);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    public static List<Fornecedor> encontrarUmFornecedor(Fornecedor fornecedor) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Fornecedor "
                    + "WHERE RazaoSocial = ?");
            
            st.setString(1, fornecedor.getRazaoSocial());
            rs = st.executeQuery();

            List<Fornecedor> list = new ArrayList<>();

            while (rs.next()) {
                Fornecedor fornecedor1 = new Fornecedor();
                fornecedor1.setRazaoSocial(rs.getString("RazaoSocial"));
                fornecedor1.setCNPJ(rs.getString("CNPJ"));
                fornecedor1.setId(rs.getInt("Id"));
                list.add(fornecedor1);
                return list;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public static void update(Fornecedor fornecedor) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE fornecedor "
                    + "SET RazaoSocial = ?, CNPJ = ? "
                    + "WHERE Id = ?");

            st.setString(1, fornecedor.getRazaoSocial());
            st.setString(2, fornecedor.getCNPJ());
            st.setInt(3, fornecedor.getId());

            st.executeUpdate();

            Alerts.showAlert("Atualizar fornecedor", null, "Fornecedor atualizado com sucesso", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    public static void updateRazao(Fornecedor fornecedor) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE fornecedor "
                    + "SET CNPJ = ? "
                    + "WHERE RazaoSocial = ?");

            st.setString(1, fornecedor.getCNPJ());
            st.setString(2, fornecedor.getRazaoSocial());

            st.executeUpdate();

            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    public static void importFornecedor(String arq) {

        String sourceFileStr = arq;
        File sourceFile = new File(sourceFileStr);
        String sourceFolderStr = sourceFile.getParent();

        Connection conn = DB.getConnection();
        PreparedStatement st = null;

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {
            String itemCsv = br.readLine();

            while (itemCsv != null) {

                String[] fields = itemCsv.split(",");
                String razaoSocial = fields[0];
                String CNPJ = fields[1];
                Fornecedor fornecedor = new Fornecedor(razaoSocial, CNPJ);
                
                List lista = encontrarUmFornecedor(fornecedor);
                
                if(lista == null){
                    addFornecedor(fornecedor);
                }else{
                    updateRazao(fornecedor);
                }            
                itemCsv = br.readLine();
            }
            Alerts.showAlert("Importação de Arquivo", null, "Arquivo importado com sucesso.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            Alerts.showAlert("Importação de aquivo", null, "Erro ao abrir arquivo", Alert.AlertType.ERROR);
        } finally {
            DB.closeStatement(st);
        }
    }
}
