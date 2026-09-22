package com.familia.listadetarefas.domain.model;

import com.familia.listadetarefas.domain.model.enums.GrauCategoria;

public class GrauParentesco {

    private Short id;
    private String nome;
    private GrauCategoria categoria;

    public GrauParentesco() {
    }

    public GrauParentesco(Short id, String nome, GrauCategoria categoria) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
    }

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
