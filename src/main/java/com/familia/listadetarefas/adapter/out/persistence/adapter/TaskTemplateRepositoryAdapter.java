package com.familia.listadetarefas.adapter.out.persistence.adapter;

import com.familia.listadetarefas.adapter.out.persistence.entity.FamilyEntity;
import com.familia.listadetarefas.adapter.out.persistence.mapper.PersistenceMapper;
import com.familia.listadetarefas.adapter.out.persistence.repository.FamilyJpaRepository;
import com.familia.listadetarefas.adapter.out.persistence.repository.TaskTemplateJpaRepository;
import com.familia.listadetarefas.domain.model.TaskTemplate;
import com.familia.listadetarefas.domain.port.out.TaskTemplateRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TaskTemplateRepositoryAdapter implements TaskTemplateRepository {

    private final TaskTemplateJpaRepository jpaRepository;
    private final FamilyJpaRepository familyJpaRepository;

    public TaskTemplateRepositoryAdapter(TaskTemplateJpaRepository jpaRepository, FamilyJpaRepository familyJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.familyJpaRepository = familyJpaRepository;
    }

    @Override
    public Optional<TaskTemplate> findById(UUID id) {
        return jpaRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public List<TaskTemplate> findByFamilyIdAndAtivoTrue(UUID familyId) {
        return jpaRepository.findByFamilyIdAndAtivoTrue(familyId).stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public TaskTemplate save(TaskTemplate template) {
        FamilyEntity familyRef = familyJpaRepository.getReferenceById(template.getFamily().getId());
        var entity = PersistenceMapper.toEntity(template, familyRef);
        return PersistenceMapper.toDomain(jpaRepository.save(entity));
    }
}
