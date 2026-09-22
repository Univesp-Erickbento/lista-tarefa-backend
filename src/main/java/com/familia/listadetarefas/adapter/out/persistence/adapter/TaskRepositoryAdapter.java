package com.familia.listadetarefas.adapter.out.persistence.adapter;

import com.familia.listadetarefas.adapter.out.persistence.entity.*;
import com.familia.listadetarefas.adapter.out.persistence.mapper.PersistenceMapper;
import com.familia.listadetarefas.adapter.out.persistence.repository.*;
import com.familia.listadetarefas.domain.model.Task;
import com.familia.listadetarefas.domain.model.enums.TaskStatus;
import com.familia.listadetarefas.domain.port.out.TaskRepository;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TaskRepositoryAdapter implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;
    private final TaskTemplateJpaRepository taskTemplateJpaRepository;
    private final FamilyJpaRepository familyJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public TaskRepositoryAdapter(TaskJpaRepository taskJpaRepository, TaskTemplateJpaRepository taskTemplateJpaRepository,
                                  FamilyJpaRepository familyJpaRepository, UserJpaRepository userJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
        this.taskTemplateJpaRepository = taskTemplateJpaRepository;
        this.familyJpaRepository = familyJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return taskJpaRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public List<Task> search(UUID familyId, UUID assignedToId, TaskStatus status, OffsetDateTime inicio, OffsetDateTime fim) {
        return taskJpaRepository.buscarComFiltros(familyId, assignedToId, status, inicio, fim).stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Task> historicoDoUsuario(UUID userId, OffsetDateTime desde) {
        return taskJpaRepository.historicoDoUsuario(userId, desde).stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Task save(Task task) {
        TaskTemplateEntity templateRef = taskTemplateJpaRepository.getReferenceById(task.getTaskTemplate().getId());
        FamilyEntity familyRef = familyJpaRepository.getReferenceById(task.getFamily().getId());
        UserEntity assignedToRef = userJpaRepository.getReferenceById(task.getAssignedTo().getId());
        UserEntity createdByRef = userJpaRepository.getReferenceById(task.getCreatedBy().getId());
        UserEntity avaliadoPorRef = task.getAvaliadoPor() != null
                ? userJpaRepository.getReferenceById(task.getAvaliadoPor().getId())
                : null;

        TaskEntity entity = PersistenceMapper.toEntity(task, templateRef, familyRef, assignedToRef, createdByRef, avaliadoPorRef);
        return PersistenceMapper.toDomain(taskJpaRepository.save(entity));
    }

    @Override
    public void delete(Task task) {
        taskJpaRepository.deleteById(task.getId());
    }
}
