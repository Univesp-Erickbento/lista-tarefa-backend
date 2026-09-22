package com.familia.listadetarefas.domain.port.in;

import com.familia.listadetarefas.domain.model.Task;
import com.familia.listadetarefas.domain.model.TaskHistory;
import com.familia.listadetarefas.domain.model.User;
import com.familia.listadetarefas.domain.model.enums.AvaliacaoTipo;
import com.familia.listadetarefas.domain.model.enums.TaskStatus;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Todos os métodos recebem "quemEstaAgindo" (User) explicitamente, em vez
 * de a implementação ir buscar isso sozinha no SecurityContextHolder do
 * Spring. É esse detalhe que mantém a camada de aplicação sem saber que
 * existe HTTP/sessão/JWT por trás de quem a está chamando.
 */
public interface TaskUseCase {

    List<Task> listar(User quemEstaAgindo, LocalDate data, TaskStatus status, UUID assignedToId);

    Task criar(User quemEstaAgindo, UUID taskTemplateId, OffsetDateTime horario, UUID assignedToId, Integer pontos);

    Task atualizar(User quemEstaAgindo, UUID taskId, String titulo, String descricao,
                    OffsetDateTime horario, UUID assignedToId, Integer pontos);

    void excluir(User quemEstaAgindo, UUID taskId);

    Task concluir(User quemEstaAgindo, UUID taskId);

    Task avaliar(User quemEstaAgindo, UUID taskId, AvaliacaoTipo avaliacao);

    List<TaskHistory> historico(UUID taskId);
}
