package com.familia.listadetarefas.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Mantida automaticamente pela trigger trg_tasks_avaliacao no banco - a
 * aplicação só LÊ esta informação, nunca escreve. Por isso não tem um
 * método de escrita/porta de saída dedicada, só leitura.
 */
public class TaskTemplateUserStats {

    private UUID id;
    private UUID taskTemplateId;
    private User user;
    private Integer vezesExecutada;
    private BigDecimal pontosAcumulados;
    private Integer somaPesosAvaliacao;
    private OffsetDateTime ultimaExecucaoEm;

    public TaskTemplateUserStats() {
    }

    public double mediaAvaliacao() {
        if (vezesExecutada == null || vezesExecutada == 0) {
            return 0.0;
        }
        return somaPesosAvaliacao.doubleValue() / vezesExecutada;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTaskTemplateId() {
        return taskTemplateId;
    }

    public void setTaskTemplateId(UUID taskTemplateId) {
        this.taskTemplateId = taskTemplateId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getVezesExecutada() {
        return vezesExecutada;
    }

    public void setVezesExecutada(Integer vezesExecutada) {
        this.vezesExecutada = vezesExecutada;
    }

    public BigDecimal getPontosAcumulados() {
        return pontosAcumulados;
    }

    public void setPontosAcumulados(BigDecimal pontosAcumulados) {
        this.pontosAcumulados = pontosAcumulados;
    }

    public Integer getSomaPesosAvaliacao() {
        return somaPesosAvaliacao;
    }

    public void setSomaPesosAvaliacao(Integer somaPesosAvaliacao) {
        this.somaPesosAvaliacao = somaPesosAvaliacao;
    }

    public OffsetDateTime getUltimaExecucaoEm() {
        return ultimaExecucaoEm;
    }

    public void setUltimaExecucaoEm(OffsetDateTime ultimaExecucaoEm) {
        this.ultimaExecucaoEm = ultimaExecucaoEm;
    }
}
