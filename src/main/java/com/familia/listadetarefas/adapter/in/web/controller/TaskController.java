package com.familia.listadetarefas.adapter.in.web.controller;

import com.familia.listadetarefas.adapter.in.web.dto.*;
import com.familia.listadetarefas.adapter.in.web.mapper.WebMapper;
import com.familia.listadetarefas.adapter.in.web.security.UserPrincipal;
import com.familia.listadetarefas.domain.model.enums.TaskStatus;
import com.familia.listadetarefas.domain.port.in.TaskUseCase;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskUseCase taskUseCase;

    public TaskController(TaskUseCase taskUseCase) {
        this.taskUseCase = taskUseCase;
    }

    @GetMapping
    public List<TaskResponse> listar(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) UUID assignedTo
    ) {
        return taskUseCase.listar(principal.getDomainUser(), data, status, assignedTo).stream()
                .map(WebMapper::toResponse)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse criar(@AuthenticationPrincipal UserPrincipal principal,
                               @Valid @RequestBody TaskCreateRequest request) {
        var task = taskUseCase.criar(principal.getDomainUser(), request.taskTemplateId(),
                request.horario(), request.assignedToId(), request.pontos());
        return WebMapper.toResponse(task);
    }

    @PutMapping("/{id}")
    public TaskResponse atualizar(@AuthenticationPrincipal UserPrincipal principal,
                                   @PathVariable UUID id, @RequestBody TaskUpdateRequest request) {
        var task = taskUseCase.atualizar(principal.getDomainUser(), id, request.titulo(), request.descricao(),
                request.horario(), request.assignedToId(), request.pontos());
        return WebMapper.toResponse(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@AuthenticationPrincipal UserPrincipal principal, @PathVariable UUID id) {
        taskUseCase.excluir(principal.getDomainUser(), id);
    }

    @PatchMapping("/{id}/concluir")
    public TaskResponse concluir(@AuthenticationPrincipal UserPrincipal principal, @PathVariable UUID id) {
        return WebMapper.toResponse(taskUseCase.concluir(principal.getDomainUser(), id));
    }

    @PatchMapping("/{id}/avaliar")
    public TaskResponse avaliar(@AuthenticationPrincipal UserPrincipal principal,
                                 @PathVariable UUID id, @Valid @RequestBody TaskEvaluateRequest request) {
        return WebMapper.toResponse(taskUseCase.avaliar(principal.getDomainUser(), id, request.avaliacao()));
    }

    @GetMapping("/{id}/history")
    public List<TaskHistoryResponse> historico(@PathVariable UUID id) {
        return taskUseCase.historico(id).stream().map(WebMapper::toResponse).toList();
    }
}
