package com.familia.listadetarefas.adapter.out.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "task_templates")
public class TaskTemplateEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "family_id", nullable = false)
    private FamilyEntity family;

    @Column(name = "titulo_padrao", nullable = false, length = 150)
    private String tituloPadrao;

    @Column(name = "descricao_padrao", columnDefinition = "text")
    private String descricaoPadrao;

    @Column(length = 50)
    private String categoria;

    @Column(name = "pontos_padrao", nullable = false)
    private Integer pontosPadrao;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FamilyEntity getFamily() {
        return family;
    }

    public void setFamily(FamilyEntity family) {
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
