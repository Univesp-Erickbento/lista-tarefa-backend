package com.familia.listadetarefas.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record TaskTemplateCreateRequest(
        @NotBlank String tituloPadrao,
        String descricaoPadrao,
        String categoria,
        @Positive Integer pontosPadrao
) {
}
