package com.familia.listadetarefas.domain.port.out;

import com.familia.listadetarefas.domain.model.TaskHistory;

import java.util.List;
import java.util.UUID;

public interface TaskHistoryRepository {
    TaskHistory save(TaskHistory history);
    List<TaskHistory> findByTaskId(UUID taskId);
}
