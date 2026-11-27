package com.familia.listadetarefas.adapter.out.persistence.mapper;

import com.familia.listadetarefas.adapter.out.persistence.entity.*;
import com.familia.listadetarefas.domain.model.*;

/**
 * Único lugar do sistema que sabe converter entre "entidade JPA" e
 * "modelo de domínio". Se um dia trocarmos JPA por outra tecnologia de
 * persistência, só este arquivo (e os *Entity, *JpaRepository, *Adapter)
 * precisam mudar - o domínio e a aplicação continuam intocados.
 */
public final class PersistenceMapper {

    private PersistenceMapper() {
    }

    // ---- Family ----

    public static Family toDomain(FamilyEntity entity) {
        if (entity == null) return null;
        Family family = new Family(entity.getId(), entity.getNome(), entity.getCreatedAt());
        family.setEndereco(entity.getEndereco());
        family.setCodigoConvite(entity.getCodigoConvite());
        family.setTipo(entity.getTipo());
        family.setParentFamilyId(entity.getParentFamilyId());
        return family;
    }

    public static FamilyEntity toEntity(Family domain) {
        FamilyEntity entity = new FamilyEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setEndereco(domain.getEndereco());
        entity.setCodigoConvite(domain.getCodigoConvite());
        entity.setTipo(domain.getTipo());
        entity.setParentFamilyId(domain.getParentFamilyId());
        return entity;
    }

    // ---- GrauParentesco ----

    public static GrauParentesco toDomain(GrauParentescoEntity entity) {
        if (entity == null) return null;
        return new GrauParentesco(entity.getId(), entity.getNome(), entity.getCategoria());
    }

    // ---- User ----

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;
        User user = new User();
        user.setId(entity.getId());
        user.setFamily(toDomain(entity.getFamily()));
        user.setNome(entity.getNome());
        user.setEmail(entity.getEmail());
        user.setSenhaHash(entity.getSenhaHash());
        user.setGrauParentesco(toDomain(entity.getGrauParentesco()));
        user.setPodeGerenciarTarefas(entity.isPodeGerenciarTarefas());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());
        return user;
    }

    public static UserEntity toEntity(User domain, GrauParentescoEntity grauEntity, FamilyEntity familyEntity) {
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setFamily(familyEntity);
        entity.setNome(domain.getNome());
        entity.setEmail(domain.getEmail());
        entity.setSenhaHash(domain.getSenhaHash());
        entity.setGrauParentesco(grauEntity);
        entity.setPodeGerenciarTarefas(domain.isPodeGerenciarTarefas());
        return entity;
    }

    // ---- TaskTemplate ----

    public static TaskTemplate toDomain(TaskTemplateEntity entity) {
        if (entity == null) return null;
        TaskTemplate template = new TaskTemplate();
        template.setId(entity.getId());
        template.setFamily(toDomain(entity.getFamily()));
        template.setTituloPadrao(entity.getTituloPadrao());
        template.setDescricaoPadrao(entity.getDescricaoPadrao());
        template.setCategoria(entity.getCategoria());
        template.setPontosPadrao(entity.getPontosPadrao());
        template.setAtivo(entity.isAtivo());
        template.setCreatedAt(entity.getCreatedAt());
        return template;
    }

    public static TaskTemplateEntity toEntity(TaskTemplate domain, FamilyEntity familyEntity) {
        TaskTemplateEntity entity = new TaskTemplateEntity();
        entity.setId(domain.getId());
        entity.setFamily(familyEntity);
        entity.setTituloPadrao(domain.getTituloPadrao());
        entity.setDescricaoPadrao(domain.getDescricaoPadrao());
        entity.setCategoria(domain.getCategoria());
        entity.setPontosPadrao(domain.getPontosPadrao());
        entity.setAtivo(domain.isAtivo());
        return entity;
    }

    // ---- Task ----

    public static Task toDomain(TaskEntity entity) {
        if (entity == null) return null;
        Task task = new Task();
        task.setId(entity.getId());
        task.setTaskTemplate(toDomain(entity.getTaskTemplate()));
        task.setFamily(toDomain(entity.getFamily()));
        task.setTitulo(entity.getTitulo());
        task.setDescricao(entity.getDescricao());
        task.setHorario(entity.getHorario());
        task.setStatus(entity.getStatus());
        task.setPontos(entity.getPontos());
        task.setAvaliacao(entity.getAvaliacao());
        task.setPontosGanhos(entity.getPontosGanhos());
        task.setAssignedTo(toDomain(entity.getAssignedTo()));
        task.setCreatedBy(toDomain(entity.getCreatedBy()));
        task.setAvaliadoPor(toDomain(entity.getAvaliadoPor()));
        task.setAvaliadoEm(entity.getAvaliadoEm());
        task.setConcluidaEm(entity.getConcluidaEm());
        task.setCreatedAt(entity.getCreatedAt());
        task.setUpdatedAt(entity.getUpdatedAt());
        return task;
    }

    public static TaskEntity toEntity(Task domain, TaskTemplateEntity templateEntity, FamilyEntity familyEntity,
                                       UserEntity assignedToEntity, UserEntity createdByEntity, UserEntity avaliadoPorEntity) {
        TaskEntity entity = new TaskEntity();
        entity.setId(domain.getId());
        entity.setTaskTemplate(templateEntity);
        entity.setFamily(familyEntity);
        entity.setTitulo(domain.getTitulo());
        entity.setDescricao(domain.getDescricao());
        entity.setHorario(domain.getHorario());
        entity.setStatus(domain.getStatus());
        entity.setPontos(domain.getPontos());
        entity.setAvaliacao(domain.getAvaliacao());
        entity.setPontosGanhos(domain.getPontosGanhos());
        entity.setAssignedTo(assignedToEntity);
        entity.setCreatedBy(createdByEntity);
        entity.setAvaliadoPor(avaliadoPorEntity);
        entity.setAvaliadoEm(domain.getAvaliadoEm());
        entity.setConcluidaEm(domain.getConcluidaEm());
        return entity;
    }

    // ---- TaskHistory ----

    public static TaskHistory toDomain(TaskHistoryEntity entity) {
        if (entity == null) return null;
        TaskHistory history = new TaskHistory();
        history.setId(entity.getId());
        history.setTaskId(entity.getTask() != null ? entity.getTask().getId() : null);
        history.setChangedBy(toDomain(entity.getChangedBy()));
        history.setTipoAlteracao(entity.getTipoAlteracao());
        history.setDetalhes(entity.getDetalhes());
        history.setCreatedAt(entity.getCreatedAt());
        return history;
    }

    public static TaskHistoryEntity toEntity(TaskHistory domain, TaskEntity taskEntity, UserEntity changedByEntity) {
        TaskHistoryEntity entity = new TaskHistoryEntity();
        entity.setTask(taskEntity);
        entity.setChangedBy(changedByEntity);
        entity.setTipoAlteracao(domain.getTipoAlteracao());
        entity.setDetalhes(domain.getDetalhes());
        return entity;
    }

    // ---- TaskTemplateUserStats ----

    public static TaskTemplateUserStats toDomain(TaskTemplateUserStatsEntity entity) {
        TaskTemplateUserStats stats = new TaskTemplateUserStats();
        stats.setId(entity.getId());
        stats.setTaskTemplateId(entity.getTaskTemplate().getId());
        stats.setUser(toDomain(entity.getUser()));
        stats.setVezesExecutada(entity.getVezesExecutada());
        stats.setPontosAcumulados(entity.getPontosAcumulados());
        stats.setSomaPesosAvaliacao(entity.getSomaPesosAvaliacao());
        stats.setUltimaExecucaoEm(entity.getUltimaExecucaoEm());
        return stats;
    }
}
