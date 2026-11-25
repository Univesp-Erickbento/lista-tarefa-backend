package com.familia.listadetarefas.domain.port.in;

/**
 * Serve tanto para "esqueci minha senha" quanto para "ainda não defini
 * minha senha" (conta criada pelo admin sem senha ainda) - é a mesma
 * operação nos dois casos.
 */
public interface PasswordResetUseCase {

    /**
     * Nunca revela se o e-mail existe ou não na resposta (evita que
     * alguém descubra quais e-mails estão cadastrados testando um por
     * um) - o controller sempre retorna a mesma mensagem genérica,
     * exista o e-mail ou não.
     */
    void solicitarLinkDeSenha(String email);

    void redefinirSenha(String token, String novaSenha);
}
