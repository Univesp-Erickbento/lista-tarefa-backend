package com.familia.listadetarefas.adapter.in.web.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TaskUpdateRequest(
        String titulo,
        String descricao,
        OffsetDateTime horario,
        UUID assignedToId,
        Integer pontos
) {
}
