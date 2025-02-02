package com.dani.springjokesmvc.models.entities;

import jakarta.persistence.*;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "telefonos")
public class Telefono implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idprimeravez", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_idprimeravez"))
    @JsonIgnoreProperties("telefonos")
    private PrimeraVez primeraVez;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public PrimeraVez getPrimeraVez() {
        return primeraVez;
    }

    public void setPrimeraVez(PrimeraVez primeraVez) {
        this.primeraVez = primeraVez;
    }

    @Override
    public String toString() {
        return "Telefono{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", primeraVez=" + (primeraVez != null ? primeraVez.getId() : "null") +
                '}';
    }
}
