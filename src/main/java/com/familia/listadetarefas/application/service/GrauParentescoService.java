package com.familia.listadetarefas.application.service;

import com.familia.listadetarefas.domain.model.GrauParentesco;
import com.familia.listadetarefas.domain.port.in.GrauParentescoUseCase;
import com.familia.listadetarefas.domain.port.out.GrauParentescoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrauParentescoService implements GrauParentescoUseCase {

    private final GrauParentescoRepository repository;

    public GrauParentescoService(GrauParentescoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GrauParentesco> listarTodos() {
        return repository.findAllOrdenadoPorCategoria();
    }
}
