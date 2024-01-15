package Entidades;

public class CadProd {

    private int codInterno;
    private String descricao;
    private Double quantMinVenda;
    private String codBarras;
    private String fornecedor;
    private Double estoque;
    private Integer quantCxPallet;
    private Double custo;
    private String codFabricante;

    public CadProd() {

    }

    public CadProd(int codInterno, String descricao, Double quantMinVenda) {
        this.codInterno = codInterno;
        this.descricao = descricao;
        this.quantMinVenda = quantMinVenda;
    }

    public CadProd(int codInterno, String descricao, Double quantMinVenda, String codBarras, String fornecedor, Double estoque, Double custo, String codFabricante) {
        this.codInterno = codInterno;
        this.descricao = descricao;
        this.quantMinVenda = quantMinVenda;
        this.codBarras = codBarras;
        this.fornecedor = fornecedor;
        this.estoque = estoque;
        this.custo = custo;
        this.codFabricante = codFabricante;
    }

    
    public CadProd(int codInterno, String descricao, Double quantMinVenda, String codBarras, String fornecedor, Double estoque, Integer quantCxPallet, Double custo, String codFabricante) {
        this.codInterno = codInterno;
        this.descricao = descricao;
        this.quantMinVenda = quantMinVenda;
        this.codBarras = codBarras;
        this.fornecedor = fornecedor;
        this.estoque = estoque;
        this.quantCxPallet = quantCxPallet;
        this.custo = custo;
        this.codFabricante = codFabricante;
    }

    
    public int getCodInterno() {
        return codInterno;
    }

    public void setCodInterno(int codInterno) {
        this.codInterno = codInterno;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getQuantMinVenda() {
        return quantMinVenda;
    }

    public void setQuantMinVenda(Double quantMinVenda) {
        this.quantMinVenda = quantMinVenda;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Double getEstoque() {
        return estoque;
    }

    public void setEstoque(Double estoque) {
        this.estoque = estoque;
    }

    public Integer getQuantCxPallet() {
        return quantCxPallet;
    }

    public void setQuantCxPallet(Integer quantCxPallet) {
        this.quantCxPallet = quantCxPallet;
    }
    

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }

    public String getCodFabricante() {
        return codFabricante;
    }

    public void setCodFabricante(String codFabricante) {
        this.codFabricante = codFabricante;
    }

}
