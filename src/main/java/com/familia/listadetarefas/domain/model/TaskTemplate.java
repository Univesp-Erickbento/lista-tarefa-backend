package com.familia.listadetarefas.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class TaskTemplate {

    private UUID id;
    private Family family;
    private String tituloPadrao;
    private String descricaoPadrao;
    private String categoria;
    private Integer pontosPadrao;
    private boolean ativo;
    private OffsetDateTime createdAt;

    public TaskTemplate() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public String getTituloPadrao() {
        return tituloPadrao;
    }

    public void setTituloPadrao(String tituloPadrao) {
        this.tituloPadrao = tituloPadrao;
    }

    public String getDescricaoPadrao() {
        return descricaoPadrao;
    }

    public void setDescricaoPadrao(String descricaoPadrao) {
        this.descricaoPadrao = descricaoPadrao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getPontosPadrao() {
        return pontosPadrao;
    }

    public void setPontosPadrao(Integer pontosPadrao) {
        this.pontosPadrao = pontosPadrao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
