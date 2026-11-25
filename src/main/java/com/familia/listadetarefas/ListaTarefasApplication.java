package com.familia.listadetarefas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;


/**
 * UserDetailsServiceAutoConfiguration é excluída de propósito: o projeto
 * não usa UserDetailsService/AuthenticationManager do Spring Security - a
 * autenticação é feita manualmente em AuthService, via portas do domínio
 * (PasswordEncoderPort, TokenPort). Sem essa exclusão, o Spring Boot cria
 * um usuário "user" com senha aleatória a cada boot (o aviso "Using
 * generated security password" no log) - nunca usado, mas gera ruído e
 * uma falsa sensação de que existe autenticação HTTP Basic ativa.
 */
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class ListaTarefasApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListaTarefasApplication.class, args);
    }
}
