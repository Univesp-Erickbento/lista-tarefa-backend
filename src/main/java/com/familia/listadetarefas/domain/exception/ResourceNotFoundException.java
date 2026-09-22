package com.familia.listadetarefas.domain.exception;

/**
 * Exceção de domínio - não sabe nada sobre HTTP. É o adaptador de entrada
 * web (GlobalExceptionHandler) quem decide que isso vira um 404.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
