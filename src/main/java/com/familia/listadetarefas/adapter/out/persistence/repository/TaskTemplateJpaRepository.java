package com.familia.listadetarefas.adapter.out.persistence.repository;

import com.familia.listadetarefas.adapter.out.persistence.entity.TaskTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskTemplateJpaRepository extends JpaRepository<TaskTemplateEntity, UUID> {
    List<TaskTemplateEntity> findByFamilyIdAndAtivoTrue(UUID familyId);
}
