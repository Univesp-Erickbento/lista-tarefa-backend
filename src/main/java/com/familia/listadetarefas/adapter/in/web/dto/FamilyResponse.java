package com.familia.listadetarefas.adapter.in.web.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record FamilyResponse(
        UUID id,
        String nome,
        String endereco,
        String codigoConvite,
        String tipo,
        UUID parentFamilyId,
        OffsetDateTime createdAt
) {}
