package com.familia.listadetarefas.adapter.in.web.dto;

import com.familia.listadetarefas.domain.model.enums.AvaliacaoTipo;
import jakarta.validation.constraints.NotNull;

public record TaskEvaluateRequest(@NotNull AvaliacaoTipo avaliacao) {
}
