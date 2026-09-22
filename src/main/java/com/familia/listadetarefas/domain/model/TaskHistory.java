package com.familia.listadetarefas.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class TaskHistory {

    private UUID id;
    private UUID taskId; // pode ser null se a tarefa original já foi excluída
    private User changedBy;
    private String tipoAlteracao;
    private String detalhes; // snapshot em JSON (string) - ver TaskHistoryRecorder
    private OffsetDateTime createdAt;

    public TaskHistory() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public User getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }

    public String getTipoAlteracao() {
        return tipoAlteracao;
    }

    public void setTipoAlteracao(String tipoAlteracao) {
        this.tipoAlteracao = tipoAlteracao;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
