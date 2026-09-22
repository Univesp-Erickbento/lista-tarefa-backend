package com.familia.listadetarefas.adapter.out.persistence.adapter;

import com.familia.listadetarefas.adapter.out.persistence.mapper.PersistenceMapper;
import com.familia.listadetarefas.adapter.out.persistence.repository.TaskTemplateUserStatsJpaRepository;
import com.familia.listadetarefas.domain.model.TaskTemplateUserStats;
import com.familia.listadetarefas.domain.port.out.TaskTemplateUserStatsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TaskTemplateUserStatsRepositoryAdapter implements TaskTemplateUserStatsRepository {

    private final TaskTemplateUserStatsJpaRepository jpaRepository;

    public TaskTemplateUserStatsRepositoryAdapter(TaskTemplateUserStatsJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<TaskTemplateUserStats> findByTaskTemplateId(UUID taskTemplateId) {
        return jpaRepository.findByTaskTemplateIdOrderByPontosAcumuladosDesc(taskTemplateId).stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }
}
