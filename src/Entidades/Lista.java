
package Entidades;

import java.time.LocalDate;

public class Lista {
    Integer id;
    Boolean disponivel = true;
    String nome , disp;

    public Lista() {
    }

    public Lista(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
        if (disponivel == true){
            setDisp("Disponível");
        }else{
            setDisp("Finalizada");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDisp() {
        return disp;
    }

    public void setDisp(String disp) {
        this.disp = disp;
    }

    
}
