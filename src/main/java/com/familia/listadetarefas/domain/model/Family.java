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
    private String endereco;
    private String codigoConvite;
    private String tipo;
    private UUID parentFamilyId;

    public Family() {
    }

    public Family(UUID id, String nome, OffsetDateTime createdAt) {
        this.id = id;
        this.nome = nome;
        this.createdAt = createdAt;
    }

    public Family(UUID id, String nome, OffsetDateTime createdAt, String endereco, String codigoConvite, String tipo, UUID parentFamilyId) {
        this.id = id;
        this.nome = nome;
        this.createdAt = createdAt;
        this.endereco = endereco;
        this.codigoConvite = codigoConvite;
        this.tipo = tipo;
        this.parentFamilyId = parentFamilyId;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCodigoConvite() {
        return codigoConvite;
    }

    public void setCodigoConvite(String codigoConvite) {
        this.codigoConvite = codigoConvite;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public UUID getParentFamilyId() {
        return parentFamilyId;
    }

    public void setParentFamilyId(UUID parentFamilyId) {
        this.parentFamilyId = parentFamilyId;
    }
}
