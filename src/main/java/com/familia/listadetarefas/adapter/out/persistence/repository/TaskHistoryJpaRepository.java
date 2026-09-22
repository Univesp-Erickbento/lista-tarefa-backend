package com.familia.listadetarefas.adapter.out.persistence.repository;

import com.familia.listadetarefas.adapter.out.persistence.entity.TaskHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskHistoryJpaRepository extends JpaRepository<TaskHistoryEntity, UUID> {
    List<TaskHistoryEntity> findByTaskIdOrderByCreatedAtDesc(UUID taskId);
}
