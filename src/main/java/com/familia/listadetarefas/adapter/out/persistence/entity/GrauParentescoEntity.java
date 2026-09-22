package com.familia.listadetarefas.adapter.out.persistence.entity;

import com.familia.listadetarefas.domain.model.enums.GrauCategoria;
import jakarta.persistence.*;

@Entity
@Table(name = "graus_parentesco")
public class GrauParentescoEntity {

    @Id
    private Short id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GrauCategoria categoria;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GrauCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(GrauCategoria categoria) {
        this.categoria = categoria;
    }
}
