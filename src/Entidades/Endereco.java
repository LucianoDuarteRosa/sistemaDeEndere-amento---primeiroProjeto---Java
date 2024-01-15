package Entidades;

public class Endereco {

    private Integer rua, predio, id;
    private Integer quantPallet = 0;
    private Integer vagas = 0;

    public Endereco() {
    }

    public Endereco(Integer rua) {
        this.rua = rua;
    }

    public Endereco(Integer rua, Integer predio) {
        this.rua = rua;
        this.predio = predio;
    }

    public Endereco(Integer rua, Integer predio, Integer id) {
        this.rua = rua;
        this.predio = predio;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getQuantPallet() {
        return quantPallet;
    }

    public void setQuantPallet(Integer quantPallet) {
        this.quantPallet = quantPallet;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    @Override
    public String toString() {
        if (rua == null || rua == 0) {
            return "Prédio= " + predio;
        } else if (predio == null || predio == 0) {
            return "Rua= " + rua;
        }
        return null;
    }

}
