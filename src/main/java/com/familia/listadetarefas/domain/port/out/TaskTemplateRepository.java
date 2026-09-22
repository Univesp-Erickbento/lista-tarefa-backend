package com.familia.listadetarefas.domain.port.out;

import com.familia.listadetarefas.domain.model.TaskTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskTemplateRepository {
    Optional<TaskTemplate> findById(UUID id);
    List<TaskTemplate> findByFamilyIdAndAtivoTrue(UUID familyId);
    boolean existsById(UUID id);
    TaskTemplate save(TaskTemplate template);
}
