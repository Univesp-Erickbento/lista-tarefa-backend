package com.familia.listadetarefas.domain.port.out;

import com.familia.listadetarefas.domain.model.Family;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de saída: contrato que o DOMÍNIO define para persistência.
 * Quem implementa é o adaptador (adapter/out/persistence), nunca o
 * contrário - o domínio não sabe que existe JPA, Postgres ou Spring Data.
 */
public interface FamilyRepository {
    Optional<Family> findById(UUID id);
    Family save(Family family);
    List<Family> findAll();
    List<Family> findByParentFamilyId(UUID parentFamilyId);
    Optional<Family> findByCodigoConvite(String codigoConvite);
}
