package com.familia.listadetarefas.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TaskCreateRequest(
        @NotNull UUID taskTemplateId,
        @NotNull OffsetDateTime horario,
        @NotNull UUID assignedToId,
        @Positive Integer pontos
) {
}
