package com.familia.listadetarefas.adapter.out.persistence.adapter;

import com.familia.listadetarefas.adapter.out.persistence.mapper.PersistenceMapper;
import com.familia.listadetarefas.adapter.out.persistence.repository.GrauParentescoJpaRepository;
import com.familia.listadetarefas.domain.model.GrauParentesco;
import com.familia.listadetarefas.domain.port.out.GrauParentescoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GrauParentescoRepositoryAdapter implements GrauParentescoRepository {

    private final GrauParentescoJpaRepository jpaRepository;

    public GrauParentescoRepositoryAdapter(GrauParentescoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<GrauParentesco> findAllOrdenadoPorCategoria() {
        return jpaRepository.findAllByOrderByCategoriaAscNomeAsc().stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<GrauParentesco> findById(Short id) {
        return jpaRepository.findById(id).map(PersistenceMapper::toDomain);
    }
}
