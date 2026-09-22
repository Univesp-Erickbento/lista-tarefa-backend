package com.familia.listadetarefas.adapter.out.persistence.repository;

import com.familia.listadetarefas.adapter.out.persistence.entity.GrauParentescoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrauParentescoJpaRepository extends JpaRepository<GrauParentescoEntity, Short> {
    List<GrauParentescoEntity> findAllByOrderByCategoriaAscNomeAsc();
}
