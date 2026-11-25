package com.familia.listadetarefas.application.service;

import com.familia.listadetarefas.domain.exception.BusinessException;
import com.familia.listadetarefas.domain.model.PasswordResetToken;
import com.familia.listadetarefas.domain.model.User;
import com.familia.listadetarefas.domain.port.in.PasswordResetUseCase;
import com.familia.listadetarefas.domain.port.out.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class PasswordResetService implements PasswordResetUseCase {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final EmailSenderPort emailSender;
    private final String frontendUrl;
    private final long expirationMinutes;

    private static final SecureRandom RANDOM = new SecureRandom();

    public PasswordResetService(UserRepository userRepository, PasswordResetTokenRepository tokenRepository,
                                 PasswordEncoderPort passwordEncoder, EmailSenderPort emailSender,
                                 @Value("${app.frontend-url}") String frontendUrl,
                                 @Value("${app.password-reset.expiration-minutes}") long expirationMinutes) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
        this.frontendUrl = frontendUrl;
        this.expirationMinutes = expirationMinutes;
    }

    @Override
    @Transactional
    public void solicitarLinkDeSenha(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            tokenRepository.invalidateAllForUser(user.getId());

            String tokenBruto = gerarTokenAleatorio();
            String tokenHash = hashSha256(tokenBruto);

            PasswordResetToken token = new PasswordResetToken();
            token.setUserId(user.getId());
            token.setTokenHash(tokenHash);
            token.setExpiresAt(OffsetDateTime.now().plusMinutes(expirationMinutes));
            tokenRepository.save(token);

            String link = frontendUrl + "/redefinir-senha?token=" + tokenBruto;
            enviarEmail(user, link);
        });
    }

    @Override
    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        String tokenHash = hashSha256(token);

        PasswordResetToken resetToken = tokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new BusinessException("Link inválido ou já utilizado"));

        if (!resetToken.valido()) {
            throw new BusinessException(
                    resetToken.jaUsado() ? "Este link já foi utilizado" : "Este link expirou - solicite um novo");
        }

        User user = userRepository.findById(resetToken.getUserId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        user.setSenhaHash(passwordEncoder.encode(novaSenha));
        userRepository.save(user);

        resetToken.setUsedAt(OffsetDateTime.now());
        tokenRepository.save(resetToken);
    }

    private void enviarEmail(User user, String link) {
        String assunto = "Lista de Tarefas - Defina sua senha";
        String corpo = """
                <p>Olá, %s!</p>
                <p>Clique no link abaixo para definir sua senha (válido por %d minutos):</p>
                <p><a href="%s">%s</a></p>
                <p>Se você não pediu isso, pode ignorar este e-mail com segurança.</p>
                """.formatted(user.getNome(), expirationMinutes, link, link);

        emailSender.enviar(user.getEmail(), assunto, corpo);
    }

    private String gerarTokenAleatorio() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hashSha256(String valor) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(valor.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao gerar hash do token", e);
        }
    }
}
