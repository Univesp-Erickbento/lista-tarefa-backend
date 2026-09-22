package com.familia.listadetarefas.adapter.in.web.mapper;

import com.familia.listadetarefas.adapter.in.web.dto.*;
import com.familia.listadetarefas.domain.model.*;
import com.familia.listadetarefas.domain.port.in.RankingUseCase;

public final class WebMapper {

    private WebMapper() {
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(), user.getNome(), user.getEmail(),
                user.getGrauParentesco().getNome(), user.isPodeGerenciarTarefas()
        );
    }

    public static GrauParentescoResponse toResponse(GrauParentesco grau) {
        return new GrauParentescoResponse(grau.getId(), grau.getNome(), grau.getCategoria().name());
    }

    public static TaskTemplateResponse toResponse(TaskTemplate template) {
        return new TaskTemplateResponse(
                template.getId(), template.getTituloPadrao(), template.getDescricaoPadrao(),
                template.getCategoria(), template.getPontosPadrao(), template.isAtivo()
        );
    }

    public static TemplateUserStatsResponse toResponse(TaskTemplateUserStats stats) {
        return new TemplateUserStatsResponse(
                stats.getUser().getId(), stats.getUser().getNome(), stats.getVezesExecutada(),
                stats.getPontosAcumulados(), stats.mediaAvaliacao(), stats.getUltimaExecucaoEm()
        );
    }

    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTaskTemplate().getId(),
                task.getTitulo(),
                task.getDescricao(),
                task.getHorario(),
                task.getStatus().name(),
                task.getPontos(),
                task.getAvaliacao() != null ? task.getAvaliacao().name() : null,
                task.getPontosGanhos(),
                toResponse(task.getAssignedTo()),
                toResponse(task.getCreatedBy()),
                task.getAvaliadoPor() != null ? toResponse(task.getAvaliadoPor()) : null,
                task.getAvaliadoEm(),
                task.getConcluidaEm()
        );
    }

    public static TaskHistoryResponse toResponse(TaskHistory history) {
        return new TaskHistoryResponse(
                history.getId(), history.getTipoAlteracao(),
                history.getChangedBy().getNome(), history.getDetalhes(), history.getCreatedAt()
        );
    }

    public static RankingEntryResponse toResponse(RankingUseCase.Entrada entrada) {
        return new RankingEntryResponse(toResponse(entrada.user()), entrada.pontosNoPeriodo(), entrada.posicao());
    }
}
