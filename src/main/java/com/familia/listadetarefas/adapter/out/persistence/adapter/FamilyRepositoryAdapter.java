package com.familia.listadetarefas.adapter.out.persistence.adapter;

import com.familia.listadetarefas.adapter.out.persistence.entity.FamilyEntity;
import com.familia.listadetarefas.adapter.out.persistence.mapper.PersistenceMapper;
import com.familia.listadetarefas.adapter.out.persistence.repository.FamilyJpaRepository;
import com.familia.listadetarefas.domain.model.Family;
import com.familia.listadetarefas.domain.port.out.FamilyRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FamilyRepositoryAdapter implements FamilyRepository {

    private final FamilyJpaRepository jpaRepository;

    public FamilyRepositoryAdapter(FamilyJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Family> findById(UUID id) {
        return jpaRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public Family save(Family family) {
        FamilyEntity entity = PersistenceMapper.toEntity(family);
        return PersistenceMapper.toDomain(jpaRepository.save(entity));
    }

    public List<Family> findAll() {
        return jpaRepository.findAll().stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Family> findByParentFamilyId(UUID parentFamilyId) {
        return jpaRepository.findByParentFamilyId(parentFamilyId).stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Family> findByCodigoConvite(String codigoConvite) {
        return jpaRepository.findByCodigoConvite(codigoConvite).map(PersistenceMapper::toDomain);
    }
}
