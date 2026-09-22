package com.familia.listadetarefas.domain.model;

import com.familia.listadetarefas.domain.exception.BusinessException;
import com.familia.listadetarefas.domain.model.enums.AvaliacaoTipo;
import com.familia.listadetarefas.domain.model.enums.TaskStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Diferente da versão anterior (em camadas), aqui as regras de transição
 * de estado (concluir, avaliar) vivem DENTRO do próprio objeto de domínio,
 * não espalhadas no service. Isso é o que caracteriza um "rich domain
 * model" em vez de um "anemic domain model" - o Task garante sozinho que
 * nunca fica num estado inconsistente, não importa quem o chame.
 */
public class Task {

    private UUID id;
    private TaskTemplate taskTemplate;
    private Family family;
    private String titulo;
    private String descricao;
    private OffsetDateTime horario;
    private TaskStatus status;
    private Integer pontos;
    private AvaliacaoTipo avaliacao;
    private BigDecimal pontosGanhos;
    private User assignedTo;
    private User createdBy;
    private User avaliadoPor;
    private OffsetDateTime avaliadoEm;
    private OffsetDateTime concluidaEm;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Task() {
        this.status = TaskStatus.PENDENTE;
    }

    public static Task novaOcorrencia(TaskTemplate template, Family family, OffsetDateTime horario,
                                       Integer pontos, User assignedTo, User createdBy) {
        Task task = new Task();
        task.taskTemplate = template;
        task.family = family;
        task.titulo = template.getTituloPadrao();
        task.descricao = template.getDescricaoPadrao();
        task.horario = horario;
        task.pontos = pontos != null ? pontos : template.getPontosPadrao();
        task.assignedTo = assignedTo;
        task.createdBy = createdBy;
        task.status = TaskStatus.PENDENTE;
        return task;
    }

    public void concluir() {
        if (this.status == TaskStatus.CONCLUIDA) {
            throw new BusinessException("Esta tarefa já está concluída");
        }
        this.status = TaskStatus.CONCLUIDA;
        this.concluidaEm = OffsetDateTime.now();
    }

    /**
     * Só marca a intenção de avaliação (quem avaliou, com que nota) - o
     * CÁLCULO de pontos_ganhos continua acontecendo na trigger do banco
     * (trg_tasks_avaliacao), não aqui. Duplicar essa conta em dois lugares
     * (Java e SQL) seria pior do que deixar em um só - a trigger já é a
     * fonte da verdade para esse número.
     */
    public void avaliar(AvaliacaoTipo avaliacao, User avaliador) {
        if (this.status != TaskStatus.CONCLUIDA) {
            throw new BusinessException("Só é possível avaliar tarefas já concluídas");
        }
        if (this.avaliacao != null) {
            throw new BusinessException("Esta tarefa já foi avaliada");
        }
        this.avaliacao = avaliacao;
        this.avaliadoPor = avaliador;
    }

    public boolean aguardandoAvaliacao() {
        return this.status == TaskStatus.CONCLUIDA && this.avaliacao == null;
    }

    // ---- getters e setters ----

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TaskTemplate getTaskTemplate() {
        return taskTemplate;
    }

    public void setTaskTemplate(TaskTemplate taskTemplate) {
        this.taskTemplate = taskTemplate;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public OffsetDateTime getHorario() {
        return horario;
    }

    public void setHorario(OffsetDateTime horario) {
        this.horario = horario;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public AvaliacaoTipo getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(AvaliacaoTipo avaliacao) {
        this.avaliacao = avaliacao;
    }

    public BigDecimal getPontosGanhos() {
        return pontosGanhos;
    }

    public void setPontosGanhos(BigDecimal pontosGanhos) {
        this.pontosGanhos = pontosGanhos;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getAvaliadoPor() {
        return avaliadoPor;
    }

    public void setAvaliadoPor(User avaliadoPor) {
        this.avaliadoPor = avaliadoPor;
    }

    public OffsetDateTime getAvaliadoEm() {
        return avaliadoEm;
    }

    public void setAvaliadoEm(OffsetDateTime avaliadoEm) {
        this.avaliadoEm = avaliadoEm;
    }

    public OffsetDateTime getConcluidaEm() {
        return concluidaEm;
    }

    public void setConcluidaEm(OffsetDateTime concluidaEm) {
        this.concluidaEm = concluidaEm;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
