package com.familia.listadetarefas.domain.port.out;

import com.familia.listadetarefas.domain.model.GrauParentesco;

import java.util.List;
import java.util.Optional;

public interface GrauParentescoRepository {
    List<GrauParentesco> findAllOrdenadoPorCategoria();
    Optional<GrauParentesco> findById(Short id);
}
