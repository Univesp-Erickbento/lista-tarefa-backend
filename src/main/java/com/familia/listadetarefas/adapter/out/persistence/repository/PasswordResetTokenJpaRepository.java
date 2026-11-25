package com.familia.listadetarefas.adapter.out.persistence.repository;

import com.familia.listadetarefas.adapter.out.persistence.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenJpaRepository extends JpaRepository<PasswordResetTokenEntity, UUID> {

    Optional<PasswordResetTokenEntity> findByTokenHash(String tokenHash);

    @Modifying
    @Query("""
        UPDATE PasswordResetTokenEntity t SET t.usedAt = :agora
        WHERE t.user.id = :userId AND t.usedAt IS NULL
        """)
    void invalidateAllForUser(@Param("userId") UUID userId, @Param("agora") OffsetDateTime agora);
}
