package bdProd;

import Entidades.Enderecamento;
import Entidades.Endereco;
import Entidades.Fornecedor;
import Utils.Alerts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

public class DAOEnderecamento {

    public static void addEnderecamento(Enderecamento enderecamento) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO enderecamento"
                    + "(CodInt, Descricao, QuantMinVenda,CodBarras, Ton, Bitola, Rua, Predio, Observacao, Disponivel, CodFab, Fornecedor)"
                    + "VALUES"
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)");
            st.setInt(1, enderecamento.getCodInt());
            st.setString(2, enderecamento.getDescricao());
            st.setDouble(3, enderecamento.getQuantMinVenda());
            st.setString(4, enderecamento.getCodBarras());
            st.setString(5, enderecamento.getTon());
            st.setString(6, enderecamento.getBitola());
            st.setInt(7, enderecamento.getRua());
            st.setInt(8, enderecamento.getPredio());
            st.setString(9, enderecamento.getObservacao());
            st.setBoolean(10, enderecamento.getDisponivel());
            st.setString(11, enderecamento.getCodFab());
            st.setString(12, enderecamento.getFornecedor());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DB.closeStatement(st);
    }

    public static Enderecamento encontrarLista(Integer codInt) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE CodInt = ? AND Disponivel = 1");
            st.setInt(1, codInt);
            rs = st.executeQuery();
            while (rs.next()) {
                Enderecamento end = new Enderecamento();
                end.setCodInt(rs.getInt("CodInt"));
                end.setDescricao(rs.getString("Descricao"));
                end.setQuantMinVenda(rs.getDouble("QuantMinVenda"));
                end.setCodBarras(rs.getString("CodBarras"));
                end.setDisponivel(rs.getBoolean("Disponivel"));
                if (end.getDisponivel() == true) {
                    return end;
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

    public static List<Enderecamento> findAll(Integer codInt) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE Disponivel = 1 ORDER BY CodInt");
            rs = st.executeQuery();

            List<Enderecamento> list = new ArrayList<>();

            while (rs.next()) {
                Enderecamento end = new Enderecamento();
                end.setCodInt(rs.getInt("CodInt"));
                end.setDescricao(rs.getString("Descricao"));
                end.setQuantMinVenda(rs.getDouble("QuantMinVenda"));
                end.setCodBarras(rs.getString("CodBarras"));
                end.setTon(rs.getString("Ton"));
                end.setBitola(rs.getString("Bitola"));
                end.setRua(rs.getInt("Rua"));
                end.setPredio(rs.getInt("Predio"));
                end.setObservacao(rs.getString("Observacao"));
                end.setDisponivel(rs.getBoolean("Disponivel"));
                end.setId(rs.getInt("Id"));
                end.setFornecedor(rs.getString("Fornecedor"));
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

    public static List<Enderecamento> findAllBarras(Integer codBarras) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE Disponivel = 1 ORDER BY CodBarras");
            rs = st.executeQuery();

            List<Enderecamento> list = new ArrayList<>();

            while (rs.next()) {
                Enderecamento end = new Enderecamento();
                end.setCodInt(rs.getInt("CodInt"));
                end.setDescricao(rs.getString("Descricao"));
                end.setQuantMinVenda(rs.getDouble("QuantMinVenda"));
                end.setCodBarras(rs.getString("CodBarras"));
                end.setTon(rs.getString("Ton"));
                end.setBitola(rs.getString("Bitola"));
                end.setRua(rs.getInt("Rua"));
                end.setPredio(rs.getInt("Predio"));
                end.setObservacao(rs.getString("Observacao"));
                end.setDisponivel(rs.getBoolean("Disponivel"));
                end.setId(rs.getInt("Id"));
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

    public static List<Enderecamento> findAllView(Integer codInt) {
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
                end.setQuantMinVenda(rs.getDouble("QuantMinVenda"));
                end.setCodFab(rs.getString("CodFab"));
                end.setTon(rs.getString("Ton"));
                end.setBitola(rs.getString("Bitola"));
                end.setObservacao(rs.getString("Observacao"));
                end.setDisponivel(rs.getBoolean("Disponivel"));
                end.setRua(rs.getInt("Rua"));
                end.setPredio(rs.getInt("Predio"));
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

    public static List<Enderecamento> findAllViewBarras(String codBarras) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE CodBarras = ? AND Disponivel = 1");

            st.setString(1, codBarras);
            rs = st.executeQuery();

            List<Enderecamento> list = new ArrayList<>();

            while (rs.next()) {
                Enderecamento end = new Enderecamento();
                end.setId(rs.getInt("Id"));
                end.setCodInt(rs.getInt("CodInt"));
                end.setDescricao(rs.getString("Descricao"));
                end.setQuantMinVenda(rs.getDouble("QuantMinVenda"));
                end.setCodFab(rs.getString("CodFab"));
                end.setTon(rs.getString("Ton"));
                end.setBitola(rs.getString("Bitola"));
                end.setObservacao(rs.getString("Observacao"));
                end.setDisponivel(rs.getBoolean("Disponivel"));
                end.setRua(rs.getInt("Rua"));
                end.setPredio(rs.getInt("Predio"));
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

    public static List<Enderecamento> findAllEndereco(Endereco end1) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE Rua = ? AND Predio = ? AND Disponivel = 1 ORDER BY Descricao");
            st.setInt(1, end1.getRua());
            st.setInt(2, end1.getPredio());
            rs = st.executeQuery();

            List<Enderecamento> list = new ArrayList<>();

            while (rs.next()) {
                Enderecamento end = new Enderecamento();
                end.setId(rs.getInt("Id"));
                end.setCodInt(rs.getInt("CodInt"));
                end.setDescricao(rs.getString("Descricao"));
                end.setQuantMinVenda(rs.getDouble("QuantMinVenda"));
                end.setCodFab(rs.getString("CodFab"));
                end.setTon(rs.getString("Ton"));
                end.setBitola(rs.getString("Bitola"));
                end.setObservacao(rs.getString("Observacao"));
                end.setDisponivel(rs.getBoolean("Disponivel"));
                end.setCodBarras(rs.getString("CodBarras"));
                end.setRua(rs.getInt("Rua"));
                end.setPredio(rs.getInt("Predio"));
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

    public static Endereco findAllEnd(Endereco end1) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Enderecamento WHERE Rua = ? AND Predio = ?");
            st.setInt(1, end1.getRua());
            st.setInt(2, end1.getPredio());
            rs = st.executeQuery();

            Enderecamento end2 = new Enderecamento();
            Endereco end = new Endereco();
            while (rs.next()) {
                end2.setDisponivel(rs.getBoolean("Disponivel"));
                if (end2.getDisponivel() == true) {
                    end.setPredio(rs.getInt("Predio"));
                    end.setRua(rs.getInt("Rua"));
                }
            }
            return end;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public static void updateIndisp(Enderecamento end) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE enderecamento "
                    + "SET Disponivel = ? "
                    + "WHERE Id = ?");

            st.setBoolean(1, false);
            st.setInt(2, end.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    public static void updateEnd(Enderecamento end) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE enderecamento "
                    + "SET Rua = ?, Predio = ? "
                    + "WHERE Id = ?");

            st.setInt(1, end.getRua());
            st.setInt(2, end.getPredio());
            st.setInt(3, end.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    public static void updateAllEnd(Enderecamento end) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE enderecamento "
                    + "SET Rua = ?, Predio = ?, Ton = ?, Bitola = ?, Observacao = ? "
                    + "WHERE Id = ?");

            st.setInt(1, end.getRua());
            st.setInt(2, end.getPredio());
            st.setString(3, end.getTon());
            st.setString(4, end.getBitola());
            st.setString(5, end.getObservacao());
            st.setInt(6, end.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
        Alerts.showAlert("Alterar endereçamento", null, "Endereçamento alterado.", Alert.AlertType.INFORMATION);
    }

    public static List<Fornecedor> relatorioQntPalletFornecedor() {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Fornecedor> list = DAOFornecedor.encontrarFornecedor();
        List<Fornecedor> listReturn = new ArrayList<>();
        for (Fornecedor fornecedor : list) {
            try {
                st = conn.prepareStatement(
                        "SELECT * FROM Enderecamento WHERE Fornecedor = ? AND Disponivel = 1");
                st.setString(1, fornecedor.getRazaoSocial());
                rs = st.executeQuery();
                while (rs.next()) {
                    fornecedor.setQuantPallet(fornecedor.getQuantPallet() + 1);
                }
                if (fornecedor.getQuantPallet() > 0) {
                    listReturn.add(fornecedor);
                }

            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
                DB.closeResultSet(rs);
            }

        }
        return listReturn;
    }

    public static List<Endereco> relatorioQntPalletRuaPredio() {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Endereco> listReturn = new ArrayList<>();
        List<Endereco> list1 = DAOEnd.findAll();
        for (Endereco end : list1) {
            List<Endereco> list2 = DAOEnd.findAllPredio2(end);
            for (Endereco end2 : list2) {

                try {
                    st = conn.prepareStatement(
                            "SELECT * FROM Enderecamento WHERE Rua = ? AND Predio = ? AND Disponivel = 1");
                    st.setInt(1, end2.getRua());
                    st.setInt(2, end2.getPredio());
                    rs = st.executeQuery();

                    while (rs.next()) {
                        end2.setQuantPallet(end2.getQuantPallet() + 1);
                    }
                    if (end2.getPredio() != 99) {
                        if (end2.getQuantPallet() > 0) {
                            end2.setVagas(end2.getVagas() - end2.getQuantPallet());
                            listReturn.add(end2);
                        }
                    }
                } catch (SQLException e) {
                    throw new DbException(e.getMessage());
                } finally {
                    DB.closeStatement(st);
                    DB.closeResultSet(rs);
                }

            }

        }
        return listReturn;
    }
}
