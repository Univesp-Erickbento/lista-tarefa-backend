package com.familia.listadetarefas.application.service;

import com.familia.listadetarefas.domain.model.Task;
import com.familia.listadetarefas.domain.model.TaskHistory;
import com.familia.listadetarefas.domain.model.User;
import com.familia.listadetarefas.domain.port.out.TaskHistoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class TaskHistoryRecorder {

    private final TaskHistoryRepository taskHistoryRepository;
    private final ObjectMapper objectMapper;

    public TaskHistoryRecorder(TaskHistoryRepository taskHistoryRepository, ObjectMapper objectMapper) {
        this.taskHistoryRepository = taskHistoryRepository;
        this.objectMapper = objectMapper;
    }

    public void registrar(Task task, String tipoAlteracao, User changedBy) {
        TaskHistory historico = new TaskHistory();
        historico.setTaskId(task.getId());
        historico.setChangedBy(changedBy);
        historico.setTipoAlteracao(tipoAlteracao);
        historico.setDetalhes(montarSnapshotJson(task));

        try {
            taskHistoryRepository.save(historico);
        } catch (Exception e) {
            // Auditoria não deve derrubar a operação principal.
            // Em produção: logar com SLF4J, não System.err.
            System.err.println("Falha ao gravar histórico da tarefa " + task.getId() + ": " + e.getMessage());
        }
    }

    private String montarSnapshotJson(Task task) {
        Map<String, Object> snapshot = new LinkedHashMap<>();
        snapshot.put("taskId", task.getId());
        snapshot.put("titulo", task.getTitulo());
        snapshot.put("status", task.getStatus() != null ? task.getStatus().name() : null);
        snapshot.put("pontos", task.getPontos());
        snapshot.put("avaliacao", task.getAvaliacao() != null ? task.getAvaliacao().name() : null);
        snapshot.put("pontosGanhos", task.getPontosGanhos());
        snapshot.put("assignedToId", task.getAssignedTo() != null ? task.getAssignedTo().getId() : null);
        snapshot.put("assignedToNome", task.getAssignedTo() != null ? task.getAssignedTo().getNome() : null);
        snapshot.put("horario", task.getHorario());

        try {
            return objectMapper.writeValueAsString(snapshot);
        } catch (Exception e) {
            return "{\"erro\": \"falha ao serializar snapshot\"}";
        }
    }
}
