package com.familia.listadetarefas.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Modelo de domínio puro - sem anotações de framework. Representa o
 * conceito de negócio "família", independente de como é persistido.
 */
public class Family {

    private UUID id;
    private String nome;
    private OffsetDateTime createdAt;

    public Family() {
    }

    public Family(UUID id, String nome, OffsetDateTime createdAt) {
        this.id = id;
        this.nome = nome;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
