package com.familia.listadetarefas.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class PasswordResetToken {

    private UUID id;
    private UUID userId;
    private String tokenHash;
    private OffsetDateTime expiresAt;
    private OffsetDateTime usedAt;
    private OffsetDateTime createdAt;

    public PasswordResetToken() {
    }

    public boolean expirado() {
        return OffsetDateTime.now().isAfter(expiresAt);
    }

    public boolean jaUsado() {
        return usedAt != null;
    }

    public boolean valido() {
        return !expirado() && !jaUsado();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public OffsetDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(OffsetDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public OffsetDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(OffsetDateTime usedAt) {
        this.usedAt = usedAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
