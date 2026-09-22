package com.familia.listadetarefas.adapter.in.web.controller;

import com.familia.listadetarefas.adapter.in.web.dto.LoginRequest;
import com.familia.listadetarefas.adapter.in.web.dto.LoginResponse;
import com.familia.listadetarefas.adapter.in.web.mapper.WebMapper;
import com.familia.listadetarefas.domain.port.in.AuthUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        var resultado = authUseCase.login(request.email(), request.senha());
        return new LoginResponse(resultado.token(), WebMapper.toResponse(resultado.user()));
    }
}
