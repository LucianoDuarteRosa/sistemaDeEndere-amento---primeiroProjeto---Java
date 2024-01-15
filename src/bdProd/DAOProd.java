package bdProd;

import Entidades.CadProd;
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
import javafx.scene.control.Alert.AlertType;

public class DAOProd {

    public static void importCsv(String arq) {

        String sourceFileStr = arq;
        File sourceFile = new File(sourceFileStr);
        String sourceFolderStr = sourceFile.getParent();

        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        Integer l = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {
            String itemCsv = br.readLine();
            Integer a = 0;
            Integer u = 0;

            while (itemCsv != null) {

                String[] fields = itemCsv.split(",");
                int codInt = Integer.parseInt(fields[0]);
                String descricao = fields[1];
                Double quantMinVenda = Double.parseDouble(fields[2]);
                String codBarras = fields[3];
                String fornecedor = fields[4];
                Double estoque = Double.parseDouble(fields[5]);
                Double custo = Double.parseDouble(fields[6]);
                String codFab = fields[7];
                CadProd prod = new CadProd(codInt, descricao, quantMinVenda, codBarras, fornecedor, estoque, custo, codFab);
                if (DAOProd.encontrarProd(codInt) != null) {
                    l++;
                    u++;
                    update(prod);
                } else {
                    l++;
                    a++;
                    addProd(prod);
                }
                itemCsv = br.readLine();
            }
            Alerts.showAlert("Importação de Arquivo", null, "Arquivo importado com sucesso. " + a + " produtos foram adicionados e " + u + " foram atualizados.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            Alerts.showAlert("Importação de aquivo", null, "Erro ao abrir arquivo", AlertType.ERROR);
        } catch (RuntimeException e) {
            l++;
            Alerts.showAlert("Importação de aquivo", null, "Erro ao abrir arquivo. Linha " + l + " com erro.", AlertType.ERROR);
        } finally {
            DB.closeStatement(st);
        }
    }

    public static void importAttEstoque(String arq) {

        String sourceFileStr = arq;
        File sourceFile = new File(sourceFileStr);
        String sourceFolderStr = sourceFile.getParent();

        Connection conn = DB.getConnection();
        PreparedStatement st = null;

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {
            String itemCsv = br.readLine();

            while (itemCsv != null) {

                String[] fields = itemCsv.split(",");
                int codInt = Integer.parseInt(fields[0]);
                Double estoque = Double.parseDouble(fields[1]);

                try {
                    st = conn.prepareStatement(
                            "UPDATE produtos "
                            + "SET Estoque = ?"
                            + "WHERE CodInt = ?");

                    st.setDouble(1, estoque);
                    st.setInt(2, codInt);

                    st.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                itemCsv = br.readLine();
            }
            Alerts.showAlert("Importação de Arquivo", null, "Arquivo importado com sucesso.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            Alerts.showAlert("Importação de aquivo", null, "Erro ao abrir arquivo", AlertType.ERROR);
        } finally {
            DB.closeStatement(st);
        }
    }

    public static void addProd(CadProd prod) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO produtos"
                    + "(CodInt, Descricao, QuantMinVenda, CodBarras, Fornecedor, Estoque, Custo, CodFab )"
                    + "VALUES"
                    + "(?, ?, ?, ?, ?, ?, ?, ?)");
            st.setInt(1, prod.getCodInterno());
            st.setString(2, prod.getDescricao());
            st.setDouble(3, prod.getQuantMinVenda());
            st.setString(4, prod.getCodBarras());
            st.setString(5, prod.getFornecedor());
            st.setDouble(6, prod.getEstoque());
            st.setDouble(7, prod.getCusto());
            st.setString(8, prod.getCodFabricante());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DB.closeStatement(st);
    }

    public static CadProd encontrarProd(Integer codInt) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM produtos WHERE CodInt = ?");
            st.setInt(1, codInt);
            rs = st.executeQuery();
            if (rs.next()) {
                CadProd prod = new CadProd();
                prod.setCodInterno(rs.getInt("CodInt"));
                prod.setDescricao(rs.getString("Descricao"));
                prod.setQuantMinVenda(rs.getDouble("QuantMinVenda"));
                prod.setCodBarras(rs.getString("CodBarras"));
                prod.setFornecedor(rs.getString("Fornecedor"));
                prod.setEstoque(rs.getDouble("Estoque"));
                prod.setCusto(rs.getDouble("Custo"));
                prod.setQuantCxPallet(rs.getInt("QuantCx"));
                prod.setCodFabricante(rs.getString("CodFab"));

                return prod;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public static void addProdCad(CadProd prod) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO produtos"
                    + "(CodInt, Descricao, QuantMinVenda, CodBarras, Fornecedor, Estoque, Custo, CodFab, QuantCx )"
                    + "VALUES"
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setInt(1, prod.getCodInterno());
            st.setString(2, prod.getDescricao());
            st.setDouble(3, prod.getQuantMinVenda());
            st.setString(4, prod.getCodBarras());
            st.setString(5, prod.getFornecedor());
            st.setDouble(6, prod.getEstoque());
            st.setDouble(7, prod.getCusto());
            st.setString(8, prod.getCodFabricante());
            st.setInt(9, prod.getQuantCxPallet());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DB.closeStatement(st);
        Alerts.showAlert("Adicionar produto", null, "Produto adicionado com sucesso.", Alert.AlertType.INFORMATION);
    }

    public static CadProd encontrarProdBarras(String codBarras) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM produtos WHERE CodBarras = ?");
            st.setString(1, codBarras);
            rs = st.executeQuery();
            if (rs.next()) {
                CadProd prod = new CadProd();
                prod.setCodInterno(rs.getInt("CodInt"));
                prod.setDescricao(rs.getString("Descricao"));
                prod.setQuantMinVenda(rs.getDouble("QuantMinVenda"));
                prod.setCodBarras(rs.getString("CodBarras"));
                prod.setFornecedor(rs.getString("Fornecedor"));
                prod.setEstoque(rs.getDouble("Estoque"));
                prod.setCusto(rs.getDouble("Custo"));
                prod.setQuantCxPallet(rs.getInt("QuantCx"));
                prod.setCodFabricante(rs.getString("CodFab"));
                return prod;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public static void update(CadProd prod) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE produtos "
                    + "SET CodInt = ?, Descricao = ?, QuantMinVenda = ?, CodBarras = ?, Fornecedor = ?, Estoque = ?, Custo = ?, CodFab = ? "
                    + "WHERE CodInt = ?");

            st.setInt(1, prod.getCodInterno());
            st.setString(2, prod.getDescricao());
            st.setDouble(3, prod.getQuantMinVenda());
            st.setString(4, prod.getCodBarras());
            st.setString(5, prod.getFornecedor());
            st.setDouble(6, prod.getEstoque());
            st.setDouble(7, prod.getCusto());
            st.setString(8, prod.getCodFabricante());
            st.setInt(9, prod.getCodInterno());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    public static void addQntCx(CadProd prod) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE produtos "
                    + "SET QuantCx = ? "
                    + "WHERE CodInt = ?");

            st.setInt(1, prod.getQuantCxPallet());
            st.setInt(2, prod.getCodInterno());
            st.executeUpdate();
            Alerts.showAlert("Quantidade caixa no pallet", null, "Quantidade de caixas adicionado com sucesso.", AlertType.INFORMATION);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    public static List<CadProd> findAll(String desc) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM produtos.produtos WHERE Descricao LIKE ? ");
            st.setString(1, desc);
            rs = st.executeQuery();

            List<CadProd> list = new ArrayList<>();

            while (rs.next()) {
                CadProd prod = new CadProd();
                prod.setCodInterno(rs.getInt("CodInt"));
                prod.setDescricao(rs.getString("Descricao"));
                prod.setQuantMinVenda(rs.getDouble("QuantMinVenda"));
                prod.setCodFabricante(rs.getString("CodFab"));
                prod.setFornecedor(rs.getString("Fornecedor"));
                prod.setEstoque(rs.getDouble("Estoque"));
                list.add(prod);
            }
            if (list.isEmpty()) {
                return null;
            } else {
                return list;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

}
