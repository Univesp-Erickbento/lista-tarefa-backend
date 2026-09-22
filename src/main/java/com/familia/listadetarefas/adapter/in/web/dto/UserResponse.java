package com.familia.listadetarefas.adapter.in.web.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String nome,
        String email,
        String grauParentesco,
        boolean podeGerenciarTarefas
) {
}
