package com.familia.listadetarefas.domain.port.out;

import com.familia.listadetarefas.domain.model.Task;
import com.familia.listadetarefas.domain.model.enums.TaskStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    Optional<Task> findById(UUID id);

    List<Task> search(UUID familyId, UUID assignedToId, TaskStatus status,
                       OffsetDateTime inicio, OffsetDateTime fim);

    List<Task> historicoDoUsuario(UUID userId, OffsetDateTime desde);

    Task save(Task task);

    void delete(Task task);
}
