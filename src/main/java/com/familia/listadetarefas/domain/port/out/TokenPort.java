package com.familia.listadetarefas.domain.port.out;

import java.util.UUID;

/**
 * Abstrai a tecnologia de token (hoje JWT, poderia ser outra amanhã sem
 * a camada de aplicação precisar mudar uma linha).
 */
public interface TokenPort {
    String generateToken(UUID userId, UUID familyId, String email, boolean admin);
    boolean isValid(String token);
    String extractEmail(String token);
    UUID extractUserId(String token);
}
