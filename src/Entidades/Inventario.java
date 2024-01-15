package Entidades;

public class Inventario {

    private int codInterno;
    private String descricao;
    private Double quantMinVenda;
    private String codBarras;
    private String fornecedor;
    private Double estoque;
    private Integer quantCxPallet;
    private Integer quantCxInv = 0;
    private Double custo;
    private String codFabricante;   
    private String obs;
    private Integer inventario = 0; 
    private String ton1 = "", ton2 = "", ton3 = "", ton4= "", ton5= "";
    private String bit1= "", bit2= "", bit3 = "", bit4= "", bit5= "";
    private Integer Cx1 = 0, Cx2 = 0, Cx3 = 0, Cx4 = 0, Cx5 = 0;

    public Inventario() {
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

    public Integer getQuantCxInv() {
        return quantCxInv;
    }

    public void setQuantCxInv(Integer quantCxInv) {
        this.quantCxInv = quantCxInv;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Integer getInventario() {
        return inventario;
    }

    public void setInventario(Integer inventario) {
        this.inventario = inventario;
    }

    public String getTon1() {
        return ton1;
    }

    public void setTon1(String ton1) {
        this.ton1 = ton1;
    }

    public String getTon2() {
        return ton2;
    }

    public void setTon2(String ton2) {
        this.ton2 = ton2;
    }

    public String getTon3() {
        return ton3;
    }

    public void setTon3(String ton3) {
        this.ton3 = ton3;
    }

    public String getTon4() {
        return ton4;
    }

    public void setTon4(String ton4) {
        this.ton4 = ton4;
    }

    public String getTon5() {
        return ton5;
    }

    public void setTon5(String ton5) {
        this.ton5 = ton5;
    }

    public String getBit1() {
        return bit1;
    }

    public void setBit1(String bit1) {
        this.bit1 = bit1;
    }

    public String getBit2() {
        return bit2;
    }

    public void setBit2(String bit2) {
        this.bit2 = bit2;
    }

    public String getBit3() {
        return bit3;
    }

    public void setBit3(String bit3) {
        this.bit3 = bit3;
    }

    public String getBit4() {
        return bit4;
    }

    public void setBit4(String bit4) {
        this.bit4 = bit4;
    }

    public String getBit5() {
        return bit5;
    }

    public void setBit5(String bit5) {
        this.bit5 = bit5;
    }

    public Integer getCx1() {
        return Cx1;
    }

    public void setCx1(Integer Cx1) {
        this.Cx1 = Cx1;
    }

    public Integer getCx2() {
        return Cx2;
    }

    public void setCx2(Integer Cx2) {
        this.Cx2 = Cx2;
    }

    public Integer getCx3() {
        return Cx3;
    }

    public void setCx3(Integer Cx3) {
        this.Cx3 = Cx3;
    }

    public Integer getCx4() {
        return Cx4;
    }

    public void setCx4(Integer Cx4) {
        this.Cx4 = Cx4;
    }

    public Integer getCx5() {
        return Cx5;
    }

    public void setCx5(Integer Cx5) {
        this.Cx5 = Cx5;
    }

    @Override
    public String toString() {
        return "Inventario{" + "codInterno=" + codInterno + ", descricao=" + descricao + ", quantMinVenda=" + quantMinVenda + ", codBarras=" + codBarras + ", fornecedor=" + fornecedor + ", estoque=" + estoque + ", quantCxPallet=" + quantCxPallet + ", quantCxInv=" + quantCxInv + ", custo=" + custo + ", codFabricante=" + codFabricante + ", obs=" + obs + ", ton1=" + ton1 + ", ton2=" + ton2 + ", ton3=" + ton3 + ", ton4=" + ton4 + ", ton5=" + ton5 + ", bit1=" + bit1 + ", bit2=" + bit2 + ", bit3=" + bit3 + ", bit4=" + bit4 + ", bit5=" + bit5 + ", Cx1=" + Cx1 + ", Cx2=" + Cx2 + ", Cx3=" + Cx3 + ", Cx4=" + Cx4 + ", Cx5=" + Cx5 + '}';
    }
    
    
}
