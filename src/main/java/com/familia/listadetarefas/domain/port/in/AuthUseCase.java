package com.familia.listadetarefas.domain.port.in;

public interface AuthUseCase {

    LoginResult login(String email, String senha);

    record LoginResult(String token, com.familia.listadetarefas.domain.model.User user) {
    }
}
