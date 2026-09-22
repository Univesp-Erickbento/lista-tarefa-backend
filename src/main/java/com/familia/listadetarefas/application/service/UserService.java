package com.familia.listadetarefas.application.service;

import com.familia.listadetarefas.domain.exception.ResourceNotFoundException;
import com.familia.listadetarefas.domain.model.GrauParentesco;
import com.familia.listadetarefas.domain.model.User;
import com.familia.listadetarefas.domain.port.in.UserUseCase;
import com.familia.listadetarefas.domain.port.out.GrauParentescoRepository;
import com.familia.listadetarefas.domain.port.out.PasswordEncoderPort;
import com.familia.listadetarefas.domain.port.out.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepository;
    private final GrauParentescoRepository grauParentescoRepository;
    private final PasswordEncoderPort passwordEncoder;

    public UserService(UserRepository userRepository, GrauParentescoRepository grauParentescoRepository,
                        PasswordEncoderPort passwordEncoder) {
        this.userRepository = userRepository;
        this.grauParentescoRepository = grauParentescoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> listarFamilia(User quemEstaAgindo) {
        return userRepository.findByFamilyId(quemEstaAgindo.getFamily().getId());
    }

    @Override
    public User adicionarMembro(User quemEstaAgindo, String nome, String email, String senha,
                                 Short grauParentescoId, boolean podeGerenciarTarefas) {
        GrauParentesco grau = grauParentescoRepository.findById(grauParentescoId)
                .orElseThrow(() -> new ResourceNotFoundException("Grau de parentesco não encontrado"));

        User novo = new User();
        novo.setFamily(quemEstaAgindo.getFamily());
        novo.setNome(nome);
        novo.setEmail(email);
        novo.setGrauParentesco(grau);
        novo.setPodeGerenciarTarefas(podeGerenciarTarefas);

        if (senha != null && !senha.isBlank()) {
            novo.setSenhaHash(passwordEncoder.encode(senha));
        }

        return userRepository.save(novo);
    }
}
