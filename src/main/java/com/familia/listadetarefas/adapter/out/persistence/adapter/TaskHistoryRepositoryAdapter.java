package com.familia.listadetarefas.adapter.out.persistence.adapter;

import com.familia.listadetarefas.adapter.out.persistence.entity.TaskEntity;
import com.familia.listadetarefas.adapter.out.persistence.entity.UserEntity;
import com.familia.listadetarefas.adapter.out.persistence.mapper.PersistenceMapper;
import com.familia.listadetarefas.adapter.out.persistence.repository.TaskHistoryJpaRepository;
import com.familia.listadetarefas.adapter.out.persistence.repository.TaskJpaRepository;
import com.familia.listadetarefas.adapter.out.persistence.repository.UserJpaRepository;
import com.familia.listadetarefas.domain.model.TaskHistory;
import com.familia.listadetarefas.domain.port.out.TaskHistoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TaskHistoryRepositoryAdapter implements TaskHistoryRepository {

    private final TaskHistoryJpaRepository jpaRepository;
    private final TaskJpaRepository taskJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public TaskHistoryRepositoryAdapter(TaskHistoryJpaRepository jpaRepository, TaskJpaRepository taskJpaRepository,
                                         UserJpaRepository userJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.taskJpaRepository = taskJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public TaskHistory save(TaskHistory history) {
        TaskEntity taskRef = history.getTaskId() != null
                ? taskJpaRepository.getReferenceById(history.getTaskId())
                : null;
        UserEntity changedByRef = userJpaRepository.getReferenceById(history.getChangedBy().getId());

        var entity = PersistenceMapper.toEntity(history, taskRef, changedByRef);
        return PersistenceMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public List<TaskHistory> findByTaskId(UUID taskId) {
        return jpaRepository.findByTaskIdOrderByCreatedAtDesc(taskId).stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }
}
