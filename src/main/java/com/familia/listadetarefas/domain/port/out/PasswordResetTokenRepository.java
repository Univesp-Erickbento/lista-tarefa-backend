package com.familia.listadetarefas.domain.port.out;

import com.familia.listadetarefas.domain.model.PasswordResetToken;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepository {
    PasswordResetToken save(PasswordResetToken token);
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);

    /** Invalida (marca como usado) qualquer token anterior ainda válido do usuário. */
    void invalidateAllForUser(UUID userId);
}
