package com.familia.listadetarefas.adapter.out.persistence.repository;

import com.familia.listadetarefas.adapter.out.persistence.entity.FamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FamilyJpaRepository extends JpaRepository<FamilyEntity, UUID> {
    List<FamilyEntity> findByParentFamilyId(UUID parentFamilyId);
    Optional<FamilyEntity> findByCodigoConvite(String codigoConvite);
}
