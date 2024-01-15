package Entidades;

public class Enderecamento {

    String observacao, obs2, ton, bitola, descricao, codBarras, codFab, fornecedor;
    Double quantMinVenda;
    Integer codInt, rua, predio, id, lista, inventario;
    Boolean disponivel;

    public Enderecamento() {
        disponivel = true;
    }

    public Enderecamento(String observacao, String ton, String bitola, Integer rua, Integer predio) {
        this.observacao = observacao;
        this.ton = ton;
        this.bitola = bitola;
        this.rua = rua;
        this.predio = predio;

    }

    public Enderecamento(String observacao, String ton, String bitola, String descricao, String codBarras, Double quantMinVenda, Integer codInt, Integer rua, Integer predio, String fornecedor) {
        this.observacao = observacao;
        this.ton = ton;
        this.bitola = bitola;
        this.codBarras = codBarras;
        this.descricao = descricao;
        this.quantMinVenda = quantMinVenda;
        this.codInt = codInt;
        this.rua = rua;
        this.predio = predio;
        this.fornecedor = fornecedor;
        disponivel = true;
    }

    public String getCodFab() {
        return codFab;
    }

    public void setCodFab(String codFab) {
        this.codFab = codFab;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getTon() {
        return ton;
    }

    public void setTon(String ton) {
        this.ton = ton;
    }

    public String getBitola() {
        return bitola;
    }

    public void setBitola(String bitola) {
        this.bitola = bitola;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public Double getQuantMinVenda() {
        return quantMinVenda;
    }

    public void setQuantMinVenda(Double quantMinVenda) {
        this.quantMinVenda = quantMinVenda;
    }

    public Integer getCodInt() {
        return codInt;
    }

    public void setCodInt(Integer codInt) {
        this.codInt = codInt;
    }

    public Integer getRua() {
        return rua;
    }

    public void setRua(Integer rua) {
        this.rua = rua;
    }

    public Integer getPredio() {
        return predio;
    }

    public void setPredio(Integer predio) {
        this.predio = predio;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Integer getLista() {
        return lista;
    }

    public void setLista(Integer lista) {
        this.lista = lista;
    }

    public String getObs2() {
        return obs2;
    }

    public void setObs2(String obs2) {
        this.obs2 = obs2;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Integer getInventario() {
        return inventario;
    }

    public void setInventario(Integer inventario) {
        this.inventario = inventario;
    }
    
    

}
