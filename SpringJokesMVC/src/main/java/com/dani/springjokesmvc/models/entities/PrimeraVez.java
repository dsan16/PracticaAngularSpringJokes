package com.dani.springjokesmvc.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "primera_vez")
public class PrimeraVez implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "programa", nullable = false, length = 255)
    private String programa;

    @NotNull
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @OneToOne
    @JoinColumn(name = "idjoke", referencedColumnName = "id", nullable = false, unique = true,
                foreignKey = @ForeignKey(name = "fk_idjoke"))
    @JsonIgnoreProperties("primeraVez")
    private Jokes joke;

    @OneToMany(mappedBy = "primeraVez", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("primeraVez")
    private List<Telefono> telefonos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Jokes getJoke() {
        return joke;
    }

    public void setJoke(Jokes joke) {
        this.joke = joke;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }
}
