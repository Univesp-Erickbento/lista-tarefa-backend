package com.familia.listadetarefas.adapter.out.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Entidade de persistência - só existe aqui, no adaptador. O domínio
 * (com.familia.listadetarefas.domain.model.Family) não sabe que isso
 * existe.
 */
@Entity
@Table(name = "families")
public class FamilyEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(length = 255)
    private String endereco;

    @Column(name = "codigo_convite", nullable = false, length = 20, unique = true)
    private String codigoConvite;

    @Column(nullable = false, length = 20)
    private String tipo = "PRINCIPAL";

    @Column(name = "parent_family_id")
    private UUID parentFamilyId;

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
