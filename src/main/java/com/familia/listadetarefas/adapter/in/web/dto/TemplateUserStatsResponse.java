package com.familia.listadetarefas.adapter.in.web.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TemplateUserStatsResponse(
        UUID userId,
        String userNome,
        Integer vezesExecutada,
        BigDecimal pontosAcumulados,
        double mediaAvaliacao,
        OffsetDateTime ultimaExecucaoEm
) {
}
