package com.familia.listadetarefas.application.service;

import com.familia.listadetarefas.domain.exception.CredenciaisInvalidasException;
import com.familia.listadetarefas.domain.model.User;
import com.familia.listadetarefas.domain.port.in.AuthUseCase;
import com.familia.listadetarefas.domain.port.out.PasswordEncoderPort;
import com.familia.listadetarefas.domain.port.out.TokenPort;
import com.familia.listadetarefas.domain.port.out.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Repare que esta classe NÃO importa nada de org.springframework.security.
 * A verificação de senha e a emissão do token acontecem via portas
 * (PasswordEncoderPort, TokenPort) - o Spring Security só aparece lá na
 * ponta, dentro dos adaptadores que implementam essas portas.
 */
@Service
public class AuthService implements AuthUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenPort tokenPort;

    public AuthService(UserRepository userRepository, PasswordEncoderPort passwordEncoder, TokenPort tokenPort) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenPort = tokenPort;
    }

    @Override
    public LoginResult login(String email, String senha) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CredenciaisInvalidasException("E-mail ou senha inválidos"));

        if (user.getSenhaHash() == null || !passwordEncoder.matches(senha, user.getSenhaHash())) {
            throw new CredenciaisInvalidasException("E-mail ou senha inválidos");
        }

        String token = tokenPort.generateToken(
                user.getId(), user.getFamily().getId(), user.getEmail(), user.isPodeGerenciarTarefas());

        return new LoginResult(token, user);
    }
}
