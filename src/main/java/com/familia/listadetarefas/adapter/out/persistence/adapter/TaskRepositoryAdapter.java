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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component
public class TaskRepositoryAdapter implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;
    private final TaskTemplateJpaRepository taskTemplateJpaRepository;
    private final FamilyJpaRepository familyJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TaskEntity> query = cb.createQuery(TaskEntity.class);
        Root<TaskEntity> root = query.from(TaskEntity.class);
        java.util.ArrayList<Predicate> predicates = new java.util.ArrayList<>();

        predicates.add(cb.equal(root.get("family").get("id"), familyId));
        if (assignedToId != null) {
            predicates.add(cb.equal(root.get("assignedTo").get("id"), assignedToId));
        }
        if (status != null) {
            predicates.add(cb.equal(root.get("status"), status));
        }
        if (inicio != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("horario"), inicio));
        }
        if (fim != null) {
            predicates.add(cb.lessThan(root.get("horario"), fim));
        }

        query.select(root).where(predicates.toArray(new Predicate[0]))
                .orderBy(cb.asc(root.get("horario")));

        return entityManager.createQuery(query).getResultList().stream()
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
