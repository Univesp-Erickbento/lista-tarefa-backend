package com.familia.listadetarefas.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @NotBlank String token,
        @NotBlank @Size(min = 6, message = "A senha precisa ter pelo menos 6 caracteres") String novaSenha
) {
}
