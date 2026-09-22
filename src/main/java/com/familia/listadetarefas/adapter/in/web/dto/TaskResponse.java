package com.familia.listadetarefas.adapter.in.web.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TaskResponse(
        UUID id,
        UUID taskTemplateId,
        String titulo,
        String descricao,
        OffsetDateTime horario,
        String status,
        Integer pontos,
        String avaliacao,
        BigDecimal pontosGanhos,
        UserResponse assignedTo,
        UserResponse createdBy,
        UserResponse avaliadoPor,
        OffsetDateTime avaliadoEm,
        OffsetDateTime concluidaEm
) {
}
