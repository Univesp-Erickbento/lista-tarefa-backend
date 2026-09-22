package com.familia.listadetarefas.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @NotBlank String nome,
        String email,
        String senha,
        @NotNull Short grauParentescoId,
        boolean podeGerenciarTarefas
) {
}
