package com.familia.listadetarefas.adapter.out.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "task_template_user_stats")
public class TaskTemplateUserStatsEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_template_id", nullable = false)
    private TaskTemplateEntity taskTemplate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "vezes_executada", nullable = false)
    private Integer vezesExecutada = 0;

    @Column(name = "pontos_acumulados", nullable = false, precision = 8, scale = 2)
    private BigDecimal pontosAcumulados = BigDecimal.ZERO;

    @Column(name = "soma_pesos_avaliacao", nullable = false)
    private Integer somaPesosAvaliacao = 0;

    @Column(name = "ultima_execucao_em")
    private OffsetDateTime ultimaExecucaoEm;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TaskTemplateEntity getTaskTemplate() {
        return taskTemplate;
    }

    public void setTaskTemplate(TaskTemplateEntity taskTemplate) {
        this.taskTemplate = taskTemplate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
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
