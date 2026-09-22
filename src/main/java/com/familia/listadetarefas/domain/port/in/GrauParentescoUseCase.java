package com.familia.listadetarefas.domain.port.in;

import com.familia.listadetarefas.domain.model.GrauParentesco;

import java.util.List;

public interface GrauParentescoUseCase {
    List<GrauParentesco> listarTodos();
}
