package com.familia.listadetarefas.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record FamilyCreateRequest(
        @NotBlank String nome,
        String endereco,
        UUID parentFamilyId
) {}
