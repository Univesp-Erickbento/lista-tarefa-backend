package com.familia.listadetarefas.adapter.in.web.controller;

import com.familia.listadetarefas.adapter.in.web.dto.UserCreateRequest;
import com.familia.listadetarefas.adapter.in.web.dto.UserResponse;
import com.familia.listadetarefas.adapter.in.web.mapper.WebMapper;
import com.familia.listadetarefas.adapter.in.web.security.UserPrincipal;
import com.familia.listadetarefas.domain.port.in.UserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping
    public List<UserResponse> listar(@AuthenticationPrincipal UserPrincipal principal) {
        return userUseCase.listarFamilia(principal.getDomainUser()).stream()
                .map(WebMapper::toResponse)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse adicionar(@AuthenticationPrincipal UserPrincipal principal,
                                   @Valid @RequestBody UserCreateRequest request) {
        var novo = userUseCase.adicionarMembro(principal.getDomainUser(), request.nome(), request.email(),
                request.senha(), request.grauParentescoId(), request.podeGerenciarTarefas());
        return WebMapper.toResponse(novo);
    }
}
