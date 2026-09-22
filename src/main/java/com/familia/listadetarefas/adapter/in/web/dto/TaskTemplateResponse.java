package com.familia.listadetarefas.adapter.in.web.dto;

import java.util.UUID;

public record TaskTemplateResponse(
        UUID id,
        String tituloPadrao,
        String descricaoPadrao,
        String categoria,
        Integer pontosPadrao,
        boolean ativo
) {
}
