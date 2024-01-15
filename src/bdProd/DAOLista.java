package bdProd;

import Entidades.Enderecamento;
import Entidades.Endereco;
import Entidades.Lista;
import Utils.Alerts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

public class DAOLista {

    public static Lista addList(Lista lista) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO Lista"
                    + "(Nome, Disponivel)"
                    + "VALUES"
                    + "( ?, ?)");

            st.setString(1, lista.getNome());
            st.setBoolean(2, lista.getDisponivel());

            st.executeUpdate();

            st = conn.prepareStatement(
                    "SELECT * FROM Lista WHERE Nome = ?");
            st.setString(1, lista.getNome());
            rs = st.executeQuery();
            if (rs.next()) {
                lista.setId(rs.getInt("Id"));
                lista.setNome(rs.getString("Nome"));
                lista.setDisponivel(rs.getBoolean("Disponivel"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        DB.closeStatement(st);
        return lista;
    }

    public static void update(Enderecamento end) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE enderecamento "
                    + "SET Lista = ? , Obs2 = ?"
                    + "WHERE Id = ?");

            st.setInt(1, end.getLista());
            st.setString(2, "Produto adicionado na lista: " + end.getLista());
            st.setInt(3, end.getId());

            st.executeUpdate();
            Alerts.showAlert("Adicionar produto na lista", null, "Produto adicionado com sucesso.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    public static void updateRemove(Enderecamento end) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE enderecamento "
                    + "SET Lista = ? , Obs2 = ?"
                    + "WHERE Id = ?");

            st.setInt(1, 0);
            st.setString(2, "");
            st.setInt(3, end.getId());

            st.executeUpdate();
            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    public static void updateRemoveList(Lista lista) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE Lista = ? AND Disponivel = 1");
            st.setInt(1, lista.getId());
            rs = st.executeQuery();

            List<Enderecamento> list = new ArrayList<>();

            while (rs.next()) {
                Enderecamento end = new Enderecamento();
                end.setId(rs.getInt("Id"));
                updateRemove(end);
                if (end.getDisponivel() == true) {
                    list.add(end);
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        } 
    }
    
    public static Lista updateListaFinal(Lista lista) {
        Connection conn = DB.getConnection();
        lista.setDisponivel(false);
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE lista "
                    + "SET Disponivel = ? "
                    + "WHERE Id = ?");

            st.setBoolean(1, lista.getDisponivel());
            st.setInt(2, lista.getId());

            st.executeUpdate();
            
            findAllFinal(lista);
            
            Alerts.showAlert("Listas", null, "Lista finalizada com sucesso.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }return lista;
    }
    
    public static Lista updateListaDesfFinal(Lista lista) {
        Connection conn = DB.getConnection();
        lista.setDisponivel(true);
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE lista "
                    + "SET Disponivel = ? "
                    + "WHERE Id = ?");

            st.setBoolean(1, lista.getDisponivel());
            st.setInt(2, lista.getId());

            st.executeUpdate();
            
            findAllDesfFinal(lista);
            
            Alerts.showAlert("Listas", null, "Lista foi liberada para edição.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        } return lista;
    }

    public static List<Enderecamento> findAllList(Lista lista) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE Lista = ? AND Disponivel = 1");
            st.setInt(1, lista.getId());
            rs = st.executeQuery();

            List<Enderecamento> list = new ArrayList<>();

            while (rs.next()) {
                Enderecamento end = new Enderecamento();
                end.setId(rs.getInt("Id"));
                end.setCodInt(rs.getInt("CodInt"));
                end.setDescricao(rs.getString("Descricao"));
                end.setTon(rs.getString("Ton"));
                end.setBitola(rs.getString("Bitola"));
                end.setRua(rs.getInt("Rua"));
                end.setPredio(rs.getInt("Predio"));
                end.setObservacao(rs.getString("Observacao"));
                end.setObs2(rs.getString("Obs2"));
                end.setDisponivel(rs.getBoolean("Disponivel"));
                end.setCodFab(rs.getString("CodFab"));
                if (end.getDisponivel() == true) {
                    list.add(end);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    public static List<Enderecamento> findAllListFinal(Lista lista) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE Lista = ?");
            st.setInt(1, lista.getId());
            rs = st.executeQuery();

            List<Enderecamento> list = new ArrayList<>();

            while (rs.next()) {
                Enderecamento end = new Enderecamento();
                end.setId(rs.getInt("Id"));
                end.setCodInt(rs.getInt("CodInt"));
                end.setDescricao(rs.getString("Descricao"));
                end.setTon(rs.getString("Ton"));
                end.setBitola(rs.getString("Bitola"));
                end.setRua(rs.getInt("Rua"));
                end.setPredio(rs.getInt("Predio"));
                end.setObservacao(rs.getString("Observacao"));
                end.setObs2(rs.getString("Obs2"));
                end.setDisponivel(rs.getBoolean("Disponivel"));
                list.add(end);
                
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    public static List<Enderecamento> findAllList2(Endereco endereco) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE Rua = ? AND Predio = ? AND Disponivel = 1");
            st.setInt(1, endereco.getRua());
            st.setInt(2, endereco.getPredio());
            rs = st.executeQuery();

            List<Enderecamento> list = new ArrayList<>();

            while (rs.next()) {
                Enderecamento end = new Enderecamento();
                end.setId(rs.getInt("Id"));
                end.setCodInt(rs.getInt("CodInt"));
                end.setDescricao(rs.getString("Descricao"));
                end.setTon(rs.getString("Ton"));
                end.setBitola(rs.getString("Bitola"));
                end.setRua(rs.getInt("Rua"));
                end.setPredio(rs.getInt("Predio"));
                end.setObservacao(rs.getString("Observacao"));
                end.setObs2(rs.getString("Obs2"));
                end.setDisponivel(rs.getBoolean("Disponivel"));
                end.setCodFab(rs.getString("CodFab"));
                end.setLista(rs.getInt("Lista"));
                if (end.getDisponivel() == true ) {
                    if(end.getLista() == 0 || end.getLista() == null)
                    list.add(end);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public static List<Lista> findAll() {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Lista");

            rs = st.executeQuery();

            List<Lista> list = new ArrayList<>();

            while (rs.next()) {
                Lista lista = new Lista();
                lista.setId(rs.getInt("Id"));
                lista.setNome(rs.getString("Nome"));
                lista.setDisponivel(rs.getBoolean("Disponivel"));

                list.add(lista);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    public static List<Lista> findAllCheckBox(String status) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Lista> list = new ArrayList<>();
        
        if(status == "Tudo"){
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Lista");

            rs = st.executeQuery();
            
            while (rs.next()) {
                Lista lista = new Lista();
                lista.setId(rs.getInt("Id"));
                lista.setNome(rs.getString("Nome"));
                lista.setDisponivel(rs.getBoolean("Disponivel"));

                list.add(lista);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        }
        
        if(status == "Disponivel"){
        try {
            Boolean bol = true;
            st = conn.prepareStatement(
                    "SELECT * FROM Lista WHERE Disponivel = ?");
            st.setBoolean(1, bol);

            rs = st.executeQuery();
            
            while (rs.next()) {
                Lista lista = new Lista();
                lista.setId(rs.getInt("Id"));
                lista.setNome(rs.getString("Nome"));
                lista.setDisponivel(rs.getBoolean("Disponivel"));

                list.add(lista);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        }
        
        if(status == "Finalizada"){
        try {
            Boolean bol = false;
            st = conn.prepareStatement(
                    "SELECT * FROM Lista WHERE Disponivel = ?");
            st.setBoolean(1, bol);

            rs = st.executeQuery();
            
            while (rs.next()) {
                Lista lista = new Lista();
                lista.setId(rs.getInt("Id"));
                lista.setNome(rs.getString("Nome"));
                lista.setDisponivel(rs.getBoolean("Disponivel"));

                list.add(lista);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        }
        
        
        return list;
    }

    public static List<Lista> findOne(Integer id) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Lista WHERE Id = ?");
            st.setInt(1, id);

            rs = st.executeQuery();

            List<Lista> list = new ArrayList<>();

            while (rs.next()) {
                Lista lista = new Lista();
                lista.setId(rs.getInt("Id"));
                lista.setNome(rs.getString("Nome"));
                lista.setDisponivel(rs.getBoolean("Disponivel"));

                list.add(lista);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    public static List<Enderecamento> findAllList(Integer codInt) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE CodInt = ? AND Disponivel = 1");
            st.setInt(1, codInt);
            rs = st.executeQuery();

            List<Enderecamento> list = new ArrayList<>();

            while (rs.next()) {
                Enderecamento end = new Enderecamento();                
                end.setId(rs.getInt("Id"));
                end.setCodInt(rs.getInt("CodInt"));
                end.setDescricao(rs.getString("Descricao"));
                end.setTon(rs.getString("Ton"));
                end.setBitola(rs.getString("Bitola"));
                end.setRua(rs.getInt("Rua"));
                end.setPredio(rs.getInt("Predio"));
                end.setObservacao(rs.getString("Observacao"));
                end.setDisponivel(rs.getBoolean("Disponivel"));
                end.setLista(rs.getInt("Lista"));
                if (end.getDisponivel() == true) {
                    if(end.getLista() == 0 || end.getLista()== null){
                            list.add(end);
                    }
                }
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    public static List<Enderecamento> findAllFinal(Lista lista) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE Lista = ?");
            st.setInt(1, lista.getId());
            rs = st.executeQuery();

            while (rs.next()) {
                Enderecamento end = new Enderecamento();
                end.setId(rs.getInt("Id"));
                end.setDisponivel(false);
                st = conn.prepareStatement(
                    "UPDATE enderecamento "
                    + "SET Disponivel = ? "
                    + "WHERE Id = ?");

            st.setBoolean(1, end.getDisponivel());
            st.setInt(2, end.getId());

            st.executeUpdate();
               
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return null;
    }
    public static List<Enderecamento> findAllDesfFinal(Lista lista) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE Lista = ?");
            st.setInt(1, lista.getId());
            rs = st.executeQuery();

            while (rs.next()) {
                Enderecamento end = new Enderecamento();
                end.setId(rs.getInt("Id"));
                end.setDisponivel(true);
                st = conn.prepareStatement(
                    "UPDATE enderecamento "
                    + "SET Disponivel = ? "
                    + "WHERE Id = ?");

            st.setBoolean(1, end.getDisponivel());
            st.setInt(2, end.getId());

            st.executeUpdate();
               
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return null;
    }
    
    public static void deleteListaId(Integer id) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM lista WHERE Id = ?");

            st.setInt(1, id);

            st.executeUpdate();
            Alerts.showAlert("Excluir Lista", null, "Lista excluída com sucesso.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
}
