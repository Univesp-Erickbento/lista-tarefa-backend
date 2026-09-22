package com.familia.listadetarefas.adapter.in.web.controller;

import com.familia.listadetarefas.adapter.in.web.dto.*;
import com.familia.listadetarefas.adapter.in.web.mapper.WebMapper;
import com.familia.listadetarefas.adapter.in.web.security.UserPrincipal;
import com.familia.listadetarefas.domain.port.in.TaskTemplateUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task-templates")
public class TaskTemplateController {

    private final TaskTemplateUseCase taskTemplateUseCase;

    public TaskTemplateController(TaskTemplateUseCase taskTemplateUseCase) {
        this.taskTemplateUseCase = taskTemplateUseCase;
    }

    @GetMapping
    public List<TaskTemplateResponse> listar(@AuthenticationPrincipal UserPrincipal principal) {
        return taskTemplateUseCase.listarCatalogo(principal.getDomainUser()).stream()
                .map(WebMapper::toResponse)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskTemplateResponse criar(@AuthenticationPrincipal UserPrincipal principal,
                                       @Valid @RequestBody TaskTemplateCreateRequest request) {
        var template = taskTemplateUseCase.criar(principal.getDomainUser(), request.tituloPadrao(),
                request.descricaoPadrao(), request.categoria(), request.pontosPadrao());
        return WebMapper.toResponse(template);
    }

    @GetMapping("/{id}/stats")
    public List<TemplateUserStatsResponse> estatisticas(@PathVariable UUID id) {
        return taskTemplateUseCase.estatisticasPorPessoa(id).stream()
                .map(WebMapper::toResponse)
                .toList();
    }
}
