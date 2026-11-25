package com.familia.listadetarefas.adapter.out.persistence.adapter;

import com.familia.listadetarefas.adapter.out.persistence.entity.PasswordResetTokenEntity;
import com.familia.listadetarefas.adapter.out.persistence.entity.UserEntity;
import com.familia.listadetarefas.adapter.out.persistence.repository.PasswordResetTokenJpaRepository;
import com.familia.listadetarefas.adapter.out.persistence.repository.UserJpaRepository;
import com.familia.listadetarefas.domain.model.PasswordResetToken;
import com.familia.listadetarefas.domain.port.out.PasswordResetTokenRepository;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class PasswordResetTokenRepositoryAdapter implements PasswordResetTokenRepository {

    private final PasswordResetTokenJpaRepository jpaRepository;
    private final UserJpaRepository userJpaRepository;

    public PasswordResetTokenRepositoryAdapter(PasswordResetTokenJpaRepository jpaRepository,
                                                UserJpaRepository userJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public PasswordResetToken save(PasswordResetToken token) {
        PasswordResetTokenEntity entity;

        if (token.getId() != null) {
            entity = jpaRepository.findById(token.getId())
                    .orElseThrow(() -> new IllegalStateException("Token não encontrado para atualizar"));
        } else {
            entity = new PasswordResetTokenEntity();
            UserEntity userRef = userJpaRepository.getReferenceById(token.getUserId());
            entity.setUser(userRef);
            entity.setTokenHash(token.getTokenHash());
            entity.setExpiresAt(token.getExpiresAt());
        }

        entity.setUsedAt(token.getUsedAt());

        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<PasswordResetToken> findByTokenHash(String tokenHash) {
        return jpaRepository.findByTokenHash(tokenHash).map(this::toDomain);
    }

    @Override
    public void invalidateAllForUser(UUID userId) {
        jpaRepository.invalidateAllForUser(userId, OffsetDateTime.now());
    }

    private PasswordResetToken toDomain(PasswordResetTokenEntity entity) {
        PasswordResetToken token = new PasswordResetToken();
        token.setId(entity.getId());
        token.setUserId(entity.getUser().getId());
        token.setTokenHash(entity.getTokenHash());
        token.setExpiresAt(entity.getExpiresAt());
        token.setUsedAt(entity.getUsedAt());
        token.setCreatedAt(entity.getCreatedAt());
        return token;
    }
}
