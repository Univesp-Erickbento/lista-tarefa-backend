package com.familia.listadetarefas.adapter.in.web.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TaskHistoryResponse(
        UUID id,
        String tipoAlteracao,
        String changedByNome,
        String detalhes,
        OffsetDateTime createdAt
) {
}
