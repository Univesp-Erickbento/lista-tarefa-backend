package com.familia.listadetarefas.application.service;

import com.familia.listadetarefas.domain.exception.ResourceNotFoundException;
import com.familia.listadetarefas.domain.model.Task;
import com.familia.listadetarefas.domain.model.TaskHistory;
import com.familia.listadetarefas.domain.model.TaskTemplate;
import com.familia.listadetarefas.domain.model.User;
import com.familia.listadetarefas.domain.model.enums.AvaliacaoTipo;
import com.familia.listadetarefas.domain.model.enums.TaskStatus;
import com.familia.listadetarefas.domain.port.in.TaskUseCase;
import com.familia.listadetarefas.domain.port.out.TaskHistoryRepository;
import com.familia.listadetarefas.domain.port.out.TaskRepository;
import com.familia.listadetarefas.domain.port.out.TaskTemplateRepository;
import com.familia.listadetarefas.domain.port.out.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService implements TaskUseCase {

    private final TaskRepository taskRepository;
    private final TaskTemplateRepository taskTemplateRepository;
    private final UserRepository userRepository;
    private final TaskHistoryRepository taskHistoryRepository;
    private final TaskHistoryRecorder taskHistoryRecorder;

    public TaskService(TaskRepository taskRepository, TaskTemplateRepository taskTemplateRepository,
                        UserRepository userRepository, TaskHistoryRepository taskHistoryRepository,
                        TaskHistoryRecorder taskHistoryRecorder) {
        this.taskRepository = taskRepository;
        this.taskTemplateRepository = taskTemplateRepository;
        this.userRepository = userRepository;
        this.taskHistoryRepository = taskHistoryRepository;
        this.taskHistoryRecorder = taskHistoryRecorder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> listar(User quemEstaAgindo, LocalDate data, TaskStatus status, UUID assignedToId) {
        OffsetDateTime inicio = data != null ? data.atStartOfDay().atOffset(ZoneOffset.UTC) : null;
        OffsetDateTime fim = data != null ? data.plusDays(1).atStartOfDay().atOffset(ZoneOffset.UTC) : null;

        return taskRepository.search(quemEstaAgindo.getFamily().getId(), assignedToId, status, inicio, fim);
    }

    @Override
    @Transactional
    public Task criar(User quemEstaAgindo, UUID taskTemplateId, OffsetDateTime horario, UUID assignedToId, Integer pontos) {
        TaskTemplate template = taskTemplateRepository.findById(taskTemplateId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa do catálogo não encontrada"));

        User assignedTo = userRepository.findById(assignedToId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário responsável não encontrado"));

        Task task = Task.novaOcorrencia(template, quemEstaAgindo.getFamily(), horario, pontos, assignedTo, quemEstaAgindo);

        Task salva = taskRepository.save(task);
        taskHistoryRecorder.registrar(salva, "CRIADA", quemEstaAgindo);
        return salva;
    }

    @Override
    @Transactional
    public Task atualizar(User quemEstaAgindo, UUID taskId, String titulo, String descricao,
                           OffsetDateTime horario, UUID assignedToId, Integer pontos) {
        Task task = buscarOuFalhar(taskId);

        if (titulo != null) task.setTitulo(titulo);
        if (descricao != null) task.setDescricao(descricao);
        if (horario != null) task.setHorario(horario);
        if (pontos != null) task.setPontos(pontos);
        if (assignedToId != null) {
            User novoResponsavel = userRepository.findById(assignedToId)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário responsável não encontrado"));
            task.setAssignedTo(novoResponsavel);
        }

        Task atualizada = taskRepository.save(task);
        taskHistoryRecorder.registrar(atualizada, "ALTERADA", quemEstaAgindo);
        return atualizada;
    }

    @Override
    @Transactional
    public void excluir(User quemEstaAgindo, UUID taskId) {
        Task task = buscarOuFalhar(taskId);
        taskHistoryRecorder.registrar(task, "EXCLUIDA", quemEstaAgindo);
        taskRepository.delete(task);
    }

    @Override
    @Transactional
    public Task concluir(User quemEstaAgindo, UUID taskId) {
        Task task = buscarOuFalhar(taskId);
        task.concluir(); // regra de transição de estado vive no domínio

        Task concluida = taskRepository.save(task);
        taskHistoryRecorder.registrar(concluida, "CONCLUIDA", quemEstaAgindo);
        return concluida;
    }

    @Override
    @Transactional
    public Task avaliar(User quemEstaAgindo, UUID taskId, AvaliacaoTipo avaliacao) {
        Task task = buscarOuFalhar(taskId);
        task.avaliar(avaliacao, quemEstaAgindo); // regra de transição de estado vive no domínio

        // pontos_ganhos e task_template_user_stats são calculados pela
        // trigger trg_tasks_avaliacao no banco, ao gravar esta mudança.
        Task avaliada = taskRepository.save(task);
        taskHistoryRecorder.registrar(avaliada, "AVALIADA", quemEstaAgindo);
        return avaliada;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskHistory> historico(UUID taskId) {
        return taskHistoryRepository.findByTaskId(taskId);
    }

    private Task buscarOuFalhar(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
    }
}
