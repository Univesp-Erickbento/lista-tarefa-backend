package com.familia.listadetarefas.adapter.in.web.controller;

import com.familia.listadetarefas.adapter.in.web.dto.LoginResponse;
import com.familia.listadetarefas.adapter.in.web.dto.RegisterRequest;
import com.familia.listadetarefas.adapter.in.web.mapper.WebMapper;
import com.familia.listadetarefas.domain.port.in.RegisterUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RegisterController {

    private final RegisterUseCase registerUseCase;

    public RegisterController(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse cadastrar(@Valid @RequestBody RegisterRequest request) {
        var resultado = registerUseCase.registrar(request.nome(), request.email(), request.senha(), request.nomeFamilia(), request.endereco());
        // Já retorna token, igual ao login - cadastro entra direto, sem
        // precisar logar de novo em seguida (decisão tomada: sem
        // confirmação de e-mail no MVP).
        return new LoginResponse(resultado.token(), WebMapper.toResponse(resultado.user()));
    }
}
