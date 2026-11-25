package com.familia.listadetarefas.application.service;

import com.familia.listadetarefas.domain.exception.BusinessException;
import com.familia.listadetarefas.domain.exception.ResourceNotFoundException;
import com.familia.listadetarefas.domain.model.TaskTemplate;
import com.familia.listadetarefas.domain.model.TaskTemplateUserStats;
import com.familia.listadetarefas.domain.model.User;
import com.familia.listadetarefas.domain.port.in.TaskTemplateUseCase;
import com.familia.listadetarefas.domain.port.out.TaskTemplateRepository;
import com.familia.listadetarefas.domain.port.out.TaskTemplateUserStatsRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TaskTemplateService implements TaskTemplateUseCase {

    private final TaskTemplateRepository taskTemplateRepository;
    private final TaskTemplateUserStatsRepository statsRepository;

    public TaskTemplateService(TaskTemplateRepository taskTemplateRepository,
                                TaskTemplateUserStatsRepository statsRepository) {
        this.taskTemplateRepository = taskTemplateRepository;
        this.statsRepository = statsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTemplate> listarCatalogo(User quemEstaAgindo) {
        return taskTemplateRepository.findByFamilyIdAndAtivoTrue(quemEstaAgindo.getFamily().getId());
    }

    @Override
    @Transactional
    public TaskTemplate criar(User quemEstaAgindo, String tituloPadrao, String descricaoPadrao,
                               String categoria, Integer pontosPadrao) {
        TaskTemplate template = new TaskTemplate();
        template.setFamily(quemEstaAgindo.getFamily());
        template.setTituloPadrao(tituloPadrao);
        template.setDescricaoPadrao(descricaoPadrao);
        template.setCategoria(categoria);
        template.setPontosPadrao(pontosPadrao);
        template.setAtivo(true);

        try {
            return taskTemplateRepository.save(template);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Já existe uma tarefa com esse nome no catálogo da família");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTemplateUserStats> estatisticasPorPessoa(UUID taskTemplateId) {
        if (!taskTemplateRepository.existsById(taskTemplateId)) {
            throw new ResourceNotFoundException("Tarefa do catálogo não encontrada");
        }
        return statsRepository.findByTaskTemplateId(taskTemplateId);
    }
}
