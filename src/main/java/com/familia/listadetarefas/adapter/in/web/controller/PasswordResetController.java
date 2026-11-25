package com.familia.listadetarefas.adapter.in.web.controller;

import com.familia.listadetarefas.adapter.in.web.dto.ForgotPasswordRequest;
import com.familia.listadetarefas.adapter.in.web.dto.ResetPasswordRequest;
import com.familia.listadetarefas.domain.port.in.PasswordResetUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class PasswordResetController {

    private final PasswordResetUseCase passwordResetUseCase;

    public PasswordResetController(PasswordResetUseCase passwordResetUseCase) {
        this.passwordResetUseCase = passwordResetUseCase;
    }

    @PostMapping("/esqueci-senha")
    public Map<String, String> solicitar(@Valid @RequestBody ForgotPasswordRequest request) {
        passwordResetUseCase.solicitarLinkDeSenha(request.email());
        // Mensagem SEMPRE igual, exista o e-mail ou não - evita que alguém
        // descubra quais e-mails estão cadastrados testando um por um.
        return Map.of("mensagem", "Se esse e-mail estiver cadastrado, você vai receber um link em instantes.");
    }

    @PostMapping("/redefinir-senha")
    public Map<String, String> redefinir(@Valid @RequestBody ResetPasswordRequest request) {
        passwordResetUseCase.redefinirSenha(request.token(), request.novaSenha());
        return Map.of("mensagem", "Senha definida com sucesso. Você já pode entrar.");
    }
}
