package Entidades;

public class Fornecedor {

    String razaoSocial, CNPJ;
    Integer Id;
    Integer quantPallet = 0;

    public Fornecedor() {
    }

    public Fornecedor(String razaoSocial, String CNPJ, Integer Id) {
        this.razaoSocial = razaoSocial;
        this.CNPJ = CNPJ;
        this.Id = Id;
    }

    public Fornecedor(String razaoSocial, String CNPJ) {
        this.razaoSocial = razaoSocial;
        this.CNPJ = CNPJ;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getQuantPallet() {
        return quantPallet;
    }

    public void setQuantPallet(Integer quantPallet) {
        this.quantPallet = quantPallet;
    }

    @Override
    public String toString() {
        return  razaoSocial + " - " + CNPJ ;
    }

}
