package com.familia.listadetarefas.adapter.out.persistence.entity;

import com.familia.listadetarefas.domain.model.enums.AvaliacaoTipo;
import com.familia.listadetarefas.domain.model.enums.TaskStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_template_id", nullable = false)
    private TaskTemplateEntity taskTemplate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "family_id", nullable = false)
    private FamilyEntity family;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "text")
    private String descricao;

    @Column(nullable = false)
    private OffsetDateTime horario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "task_status")
    @ColumnTransformer(write = "?::task_status")
    private TaskStatus status = TaskStatus.PENDENTE;

    @Column(nullable = false)
    private Integer pontos;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "avaliacao_tipo")
    @ColumnTransformer(write = "?::avaliacao_tipo")
    private AvaliacaoTipo avaliacao;

    @Column(name = "pontos_ganhos", precision = 6, scale = 2)
    private BigDecimal pontosGanhos;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assigned_to_id", nullable = false)
    private UserEntity assignedTo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_id", nullable = false)
    private UserEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliado_por_id")
    private UserEntity avaliadoPor;

    @Column(name = "avaliado_em")
    private OffsetDateTime avaliadoEm;

    @Column(name = "concluida_em")
    private OffsetDateTime concluidaEm;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

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

    public FamilyEntity getFamily() {
        return family;
    }

    public void setFamily(FamilyEntity family) {
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

    public UserEntity getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UserEntity assignedTo) {
        this.assignedTo = assignedTo;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public UserEntity getAvaliadoPor() {
        return avaliadoPor;
    }

    public void setAvaliadoPor(UserEntity avaliadoPor) {
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
