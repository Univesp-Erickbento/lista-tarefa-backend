package com.familia.listadetarefas.domain.port.out;

import com.familia.listadetarefas.domain.model.TaskTemplateUserStats;

import java.util.List;
import java.util.UUID;

public interface TaskTemplateUserStatsRepository {
    List<TaskTemplateUserStats> findByTaskTemplateId(UUID taskTemplateId);
}
