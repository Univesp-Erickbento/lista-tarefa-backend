package com.familia.listadetarefas.domain.port.out;

/**
 * Abstrai o algoritmo de hash de senha. A implementação real (BCrypt) fica
 * no adaptador - o domínio só sabe que existe "codificar" e "verificar".
 */
public interface PasswordEncoderPort {
    String encode(String senhaPura);
    boolean matches(String senhaPura, String senhaHash);
}
