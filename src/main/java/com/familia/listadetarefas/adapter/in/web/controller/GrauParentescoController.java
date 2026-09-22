package com.familia.listadetarefas.adapter.in.web.controller;

import com.familia.listadetarefas.adapter.in.web.dto.GrauParentescoResponse;
import com.familia.listadetarefas.adapter.in.web.mapper.WebMapper;
import com.familia.listadetarefas.domain.port.in.GrauParentescoUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/graus-parentesco")
public class GrauParentescoController {

    private final GrauParentescoUseCase grauParentescoUseCase;

    public GrauParentescoController(GrauParentescoUseCase grauParentescoUseCase) {
        this.grauParentescoUseCase = grauParentescoUseCase;
    }

    @GetMapping
    public List<GrauParentescoResponse> listar() {
        return grauParentescoUseCase.listarTodos().stream()
                .map(WebMapper::toResponse)
                .toList();
    }
}
