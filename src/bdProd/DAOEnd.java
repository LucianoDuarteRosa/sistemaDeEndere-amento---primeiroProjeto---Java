package bdProd;

import Entidades.Enderecamento;
import Entidades.Endereco;
import Utils.Alerts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

public class DAOEnd {

    public static void addEnd(Endereco obj) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO predio"
                    + "(Rua, Predio)"
                    + "VALUES"
                    + "(?, ?)");
            st.setInt(1, obj.getRua());
            st.setInt(2, obj.getPredio());
            if (encontrarRua(obj.getRua(), obj.getPredio()) == null){
               st.executeUpdate(); 
               Alerts.showAlert("Cadastro de endereço", null, "Endereço adicionado com sucesso.", Alert.AlertType.INFORMATION);
            } else{
               Alerts.showAlert("Adicionar rua", null, "Nome de predío já cadastrado.", Alert.AlertType.ERROR); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        DB.closeStatement(st);
    }

    public static void addRua(Endereco obj) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO rua"
                    + "(Rua)"
                    + "VALUES"
                    + "(?)");
            st.setInt(1, obj.getRua());

            st.executeUpdate();

            Alerts.showAlert("Cadastro de rua", null, "Rua adicionado com sucesso", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            Alerts.showAlert("Adicionar rua", null, "Nome de rua já cadastrado.", Alert.AlertType.ERROR);
        }
        DB.closeStatement(st);
    }

    public static Endereco encontrarRua(Integer rua, Integer predio) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM predio WHERE Rua = ? AND Predio = ?");
            st.setInt(1, rua);
            st.setInt(2, predio);
            rs = st.executeQuery();
            if (rs.next()) {
                Endereco end = new Endereco();
                end.setRua(rs.getInt("Rua"));
                end.setPredio(rs.getInt("Predio"));
                end.setId(rs.getInt("Id"));
                
                return end;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public static List<Endereco> findAll() {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Rua ORDER BY Rua");
            rs = st.executeQuery();

            List<Endereco> list = new ArrayList<>();

            while (rs.next()) {
                Endereco obj = new Endereco();
                obj.setRua(rs.getInt("Rua"));
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    public static List<Endereco> findAllPredio2(Endereco end) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM predio WHERE Rua = ? ORDER BY predio");
            st.setInt(1 , end.getRua());
            rs = st.executeQuery();

            List<Endereco> list = new ArrayList<>();

            while (rs.next()) {
                Endereco obj = new Endereco();               
                obj.setPredio(rs.getInt("Predio"));
                obj.setRua(rs.getInt("Rua"));
                obj.setVagas(rs.getInt("Vagas"));
                obj.setId(rs.getInt("Id"));
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    public static List<Endereco> findAllPredio(Endereco end) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM predio WHERE Rua = ?");
            st.setInt(1 , end.getRua());
            rs = st.executeQuery();

            List<Endereco> list = new ArrayList<>();

            while (rs.next()) {
                Endereco obj = new Endereco();               
                obj.setPredio(rs.getInt("Predio"));
                obj.setId(rs.getInt("Id"));
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    public static void update(Endereco end) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE Predio "
                    + "SET Rua = ?, Predio = ?"
                    + "WHERE Id = ?");

            st.setInt(1, end.getRua());
            st.setInt(2, end.getPredio());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    public static void deletePredio(Integer id) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM predio WHERE Id = ?");

            st.setInt(1, id);            
            st.executeUpdate();
            Alerts.showAlert("Excluir endereço", null, "Prédio excluído com sucesso.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    public static void deleteRua(Integer id) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM rua WHERE Rua = ?");

            st.setInt(1, id);

            st.executeUpdate();
             Alerts.showAlert("Excluir endereço", null, "Rua excluída com sucesso.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
             Alerts.showAlert("Excluir endereço", null, "Rua não pode ser excluída, pois tem prédio associado.", Alert.AlertType.WARNING);
            throw new DbException(e.getMessage());           
        } finally {
            DB.closeStatement(st);
        }
    }
    
    public static Endereco encontrarEnd(Endereco end) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM predio WHERE Rua = ? AND Predio = ?");
            st.setInt(1, end.getRua());
            st.setInt(2, end.getPredio());
            rs = st.executeQuery();
            if (rs.next()) {
                Endereco end1 = new Endereco();
                end1.setRua(rs.getInt("Rua"));
                end1.setPredio(rs.getInt("Predio"));
                end1.setId(rs.getInt("Id"));
                
                return end1;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    public static Endereco encontrarEnd2(Endereco end) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM enderecamento WHERE Rua = ? AND Predio = ?");
            st.setInt(1, end.getRua());
            st.setInt(2, end.getPredio());
            rs = st.executeQuery();
            Endereco end2 = new Endereco();
            if (rs.next()) {
                Enderecamento end1 = new Enderecamento();
                end1.setDisponivel(rs.getBoolean("Disponivel"));
                end1.setPredio(rs.getInt("Predio"));
                end1.setRua(rs.getInt("Rua"));
                if (end1.getDisponivel() == true){
                    end2.setRua(end1.getRua());
                    end2.setPredio(end1.getPredio());
                    return end2;
                }    
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    public static Endereco encontrarEnd3(Endereco end) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM enderecamento WHERE Rua = ?");
            st.setInt(1, end.getRua());
            rs = st.executeQuery();
            Endereco end2 = new Endereco();
            if (rs.next()) {
                Enderecamento end1 = new Enderecamento();
                end1.setDisponivel(rs.getBoolean("Disponivel"));
                end1.setPredio(rs.getInt("Predio"));
                end1.setRua(rs.getInt("Rua"));
                if (end1.getDisponivel() == true){
                    end2.setRua(end1.getRua());
                    end2.setPredio(end1.getPredio());
                    return end2;
                }    
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
