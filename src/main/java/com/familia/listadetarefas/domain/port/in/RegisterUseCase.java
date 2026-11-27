package com.familia.listadetarefas.domain.port.in;

/**
 * Cria uma FAMÍLIA NOVA + a pessoa como primeiro admin dela. Diferente de
 * UserUseCase.adicionarMembro (que exige um admin já autenticado de uma
 * família existente) - este é o único caso de uso que não exige ninguém
 * logado antes, porque é assim que a primeira conta de cada família nasce.
 */
public interface RegisterUseCase {
    AuthUseCase.LoginResult registrar(String nome, String email, String senha, String nomeFamilia, String endereco);
}
