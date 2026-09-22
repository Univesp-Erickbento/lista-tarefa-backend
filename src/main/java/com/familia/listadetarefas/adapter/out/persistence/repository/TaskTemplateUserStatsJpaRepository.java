package com.familia.listadetarefas.adapter.out.persistence.repository;

import com.familia.listadetarefas.adapter.out.persistence.entity.TaskTemplateUserStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskTemplateUserStatsJpaRepository extends JpaRepository<TaskTemplateUserStatsEntity, UUID> {
    List<TaskTemplateUserStatsEntity> findByTaskTemplateIdOrderByPontosAcumuladosDesc(UUID taskTemplateId);
}
