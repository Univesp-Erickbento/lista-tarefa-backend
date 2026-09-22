package com.familia.listadetarefas.adapter.out.persistence.repository;

import com.familia.listadetarefas.adapter.out.persistence.entity.TaskEntity;
import com.familia.listadetarefas.domain.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<TaskEntity, UUID> {

    @Query("""
        SELECT t FROM TaskEntity t
        WHERE t.family.id = :familyId
          AND (:assignedToId IS NULL OR t.assignedTo.id = :assignedToId)
          AND (:status IS NULL OR t.status = :status)
          AND (:inicio IS NULL OR t.horario >= :inicio)
          AND (:fim IS NULL OR t.horario < :fim)
        ORDER BY t.horario ASC
        """)
    List<TaskEntity> buscarComFiltros(
            @Param("familyId") UUID familyId,
            @Param("assignedToId") UUID assignedToId,
            @Param("status") TaskStatus status,
            @Param("inicio") OffsetDateTime inicio,
            @Param("fim") OffsetDateTime fim
    );

    @Query("""
        SELECT t FROM TaskEntity t
        WHERE t.assignedTo.id = :userId
          AND t.horario >= :desde
        ORDER BY t.avaliadoEm DESC
        """)
    List<TaskEntity> historicoDoUsuario(@Param("userId") UUID userId, @Param("desde") OffsetDateTime desde);
}
