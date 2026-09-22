package com.familia.listadetarefas.adapter.in.web.security;

import com.familia.listadetarefas.domain.port.out.TokenPort;
import com.familia.listadetarefas.domain.port.out.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Repare que este filtro NÃO usa CustomUserDetailsService/AuthenticationManager
 * - ele mesmo pega o e-mail do token (via TokenPort, porta de domínio) e
 * busca o usuário (via UserRepository, também porta de domínio). O
 * "Spring Security-ismo" fica só na construção do UserPrincipal e no
 * SecurityContextHolder, ambos específicos deste adaptador.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenPort tokenPort;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(TokenPort tokenPort, UserRepository userRepository) {
        this.tokenPort = tokenPort;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        if (tokenPort.isValid(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            String email = tokenPort.extractEmail(token);

            userRepository.findByEmail(email).ifPresent(user -> {
                UserPrincipal principal = new UserPrincipal(user);
                var authToken = new UsernamePasswordAuthenticationToken(
                        principal, null, principal.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            });
        }

        filterChain.doFilter(request, response);
    }
}
