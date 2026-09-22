package com.familia.listadetarefas.adapter.out.security;

import com.familia.listadetarefas.domain.port.out.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordAdapter implements PasswordEncoderPort {

    private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder();

    @Override
    public String encode(String senhaPura) {
        return delegate.encode(senhaPura);
    }

    @Override
    public boolean matches(String senhaPura, String senhaHash) {
        return delegate.matches(senhaPura, senhaHash);
    }
}
