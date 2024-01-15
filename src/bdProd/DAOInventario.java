package bdProd;

import Entidades.CadProd;
import Entidades.Enderecamento;
import Entidades.Fornecedor;
import Entidades.Inventario;
import Utils.Alerts;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

public class DAOInventario {

    public static void inventario(List<Fornecedor> listForn) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Inventario> list = new ArrayList<>();
        List<Inventario> listFinal = new ArrayList<>();
        List<CadProd> listProd = new ArrayList<>();
        for (Fornecedor fornecedor : listForn) {

            try {
                st = conn.prepareStatement(
                        "SELECT * FROM Produtos WHERE Fornecedor = ? AND Estoque > 0");
                st.setString(1, fornecedor.getRazaoSocial());
                rs = st.executeQuery();

                while (rs.next()) {
                    CadProd prod = new CadProd();
                    prod.setCodInterno(rs.getInt("CodInt"));
                    prod.setEstoque(rs.getDouble("Estoque"));
                    prod.setFornecedor(rs.getString("Fornecedor"));
                    prod.setDescricao(rs.getString("Descricao"));
                    prod.setCodFabricante(rs.getString("CodFab"));
                    prod.setQuantMinVenda(rs.getDouble("QuantMinVenda"));
                    prod.setCusto(rs.getDouble("Custo"));
                    prod.setCodBarras(rs.getString("CodBarras"));
                    prod.setQuantCxPallet(rs.getInt("QuantCx"));

                    listProd.add(prod);
                }

                for (CadProd produtos : listProd) {
                    int cont1 = 0;
                    int cont2 = 0;
                    Inventario inventario = new Inventario();
                    inventario.setCodInterno(produtos.getCodInterno());
                    inventario.setCodFabricante(produtos.getCodFabricante());
                    inventario.setCusto(produtos.getCusto());
                    inventario.setDescricao(produtos.getDescricao());
                    inventario.setEstoque(produtos.getEstoque());
                    inventario.setQuantMinVenda(produtos.getQuantMinVenda());
                    inventario.setFornecedor(produtos.getFornecedor());
                    inventario.setCodBarras(produtos.getCodBarras());
                    inventario.setQuantCxPallet(produtos.getQuantCxPallet());

                    st = conn.prepareStatement(
                            "SELECT * FROM Enderecamento WHERE CodInt = ? AND Disponivel = 1");
                    st.setInt(1, produtos.getCodInterno());
                    rs = st.executeQuery();
                    
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
                        end.setId(rs.getInt("Id"));
                        end.setFornecedor(rs.getString("Fornecedor"));
                        end.setCodFab(rs.getString("CodFab"));
                        
                        if (end.getObservacao().isEmpty() || end.getObservacao() == null) {
                            if (inventario.getTon1().equals(end.getTon())) {
                                inventario.setCx1(inventario.getCx1() + produtos.getQuantCxPallet());
                            } else if (inventario.getTon2().equals(end.getTon())) {
                                inventario.setCx2(inventario.getCx2() + produtos.getQuantCxPallet());
                            } else if (inventario.getTon3().equals(end.getTon())) {
                                inventario.setCx3(inventario.getCx3() + produtos.getQuantCxPallet());
                            } else if (inventario.getTon4().equals(end.getTon())) {
                                inventario.setCx4(inventario.getCx4() + produtos.getQuantCxPallet());
                            } else if (inventario.getTon5().equals(end.getTon())) {
                                inventario.setCx5(inventario.getCx5() + produtos.getQuantCxPallet());
                            } else {
                                if (inventario.getTon1() == "") {
                                    inventario.setTon1(end.getTon());
                                    inventario.setBit1(end.getBitola());
                                    inventario.setCx1(inventario.getCx1() + produtos.getQuantCxPallet());
                                } else if (inventario.getTon2() == "") {
                                    inventario.setTon2(end.getTon());
                                    inventario.setBit2(end.getBitola());
                                    inventario.setCx2(inventario.getCx2() + produtos.getQuantCxPallet());
                                } else if (inventario.getTon3() == "") {
                                    inventario.setTon3(end.getTon());
                                    inventario.setBit3(end.getBitola());
                                    inventario.setCx3(inventario.getCx3() + produtos.getQuantCxPallet());
                                } else if (inventario.getTon4() == "") {
                                    inventario.setTon4(end.getTon());
                                    inventario.setBit4(end.getBitola());
                                    inventario.setCx4(inventario.getCx4() + produtos.getQuantCxPallet());
                                } else if (inventario.getTon5() == "") {
                                    inventario.setTon5(end.getTon());
                                    inventario.setBit5(end.getBitola());
                                    inventario.setCx5(inventario.getCx5() + produtos.getQuantCxPallet());
                                } else {
                                    Alerts.showAlert("Criar Aquivo de inventário.", null, "Produto " + end.getCodInt() + " " + end.getDescricao() + " não foi possivel adicionar póis exede o máximo de ton/bit possível.", Alert.AlertType.ERROR);
                                }
                            }
                            cont1++;
                        } else {
                            Inventario inventario1 = new Inventario();
                            inventario1.setCodInterno(end.getCodInt());
                            inventario1.setEstoque(produtos.getEstoque());
                            inventario1.setFornecedor(end.getFornecedor());
                            inventario1.setDescricao(end.getDescricao());
                            inventario1.setCodFabricante(end.getCodFab());
                            inventario1.setQuantMinVenda(end.getQuantMinVenda());
                            inventario1.setCusto(produtos.getCusto());
                            inventario1.setObs(end.getObservacao());
                            inventario1.setTon1(end.getTon());
                            inventario1.setBit1(end.getBitola());
                            list.add(inventario1);

                            cont2++;
                        }
                    }

                    if (cont1 > 0) {
                        listFinal.add(inventario);
                    }
                    if (cont1 == 0 && cont2 > 0) {
                        listFinal.add(inventario);
                    }
                    if (cont1 == 0 && cont2 == 0) {
                        listFinal.add(inventario);
                    }
                }
            } catch (SQLException e) {
                throw new DbException(e.getMessage());

            }
            try {
                Utils.Impressao.impressaoInvOcorrencias(list);
                Utils.Impressao.impressaoInventario(listFinal);

            } catch (IOException e) {
                Alerts.showAlert("Error ao criar arquivo", null, "Não foi possível criar arquivo de ocorrências ou inventário.", Alert.AlertType.ERROR);
            }
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
