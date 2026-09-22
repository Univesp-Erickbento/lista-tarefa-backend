package com.familia.listadetarefas.domain.port.in;

import com.familia.listadetarefas.domain.model.User;

import java.util.List;

public interface UserUseCase {

    List<User> listarFamilia(User quemEstaAgindo);

    User adicionarMembro(User quemEstaAgindo, String nome, String email, String senha,
                          Short grauParentescoId, boolean podeGerenciarTarefas);
}
