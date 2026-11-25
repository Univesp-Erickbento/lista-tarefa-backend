package com.familia.listadetarefas.application.service;

import com.familia.listadetarefas.domain.exception.BusinessException;
import com.familia.listadetarefas.domain.model.Family;
import com.familia.listadetarefas.domain.model.GrauParentesco;
import com.familia.listadetarefas.domain.model.User;
import com.familia.listadetarefas.domain.port.in.AuthUseCase;
import com.familia.listadetarefas.domain.port.in.RegisterUseCase;
import com.familia.listadetarefas.domain.port.out.FamilyRepository;
import com.familia.listadetarefas.domain.port.out.GrauParentescoRepository;
import com.familia.listadetarefas.domain.port.out.PasswordEncoderPort;
import com.familia.listadetarefas.domain.port.out.TokenPort;
import com.familia.listadetarefas.domain.port.out.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Grau de parentesco do fundador: como a família ainda não existe antes
 * dele, não faz sentido perguntar "qual seu parentesco" no cadastro - a
 * pessoa que cria a conta é sempre rotulada como "Responsável legal/Tutor"
 * (grau já existente na tabela graus_parentesco, categoria SOCIOAFETIVO).
 * Ela pode revisar/trocar isso depois, se quisermos expor essa edição no
 * futuro - não existe tela para isso ainda.
 */
@Service
public class RegisterService implements RegisterUseCase {

    private static final String GRAU_PADRAO_FUNDADOR = "Responsável legal/Tutor";

    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;
    private final GrauParentescoRepository grauParentescoRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenPort tokenPort;

    public RegisterService(FamilyRepository familyRepository, UserRepository userRepository,
                            GrauParentescoRepository grauParentescoRepository,
                            PasswordEncoderPort passwordEncoder, TokenPort tokenPort) {
        this.familyRepository = familyRepository;
        this.userRepository = userRepository;
        this.grauParentescoRepository = grauParentescoRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenPort = tokenPort;
    }

    @Override
    @Transactional
    public AuthUseCase.LoginResult registrar(String nome, String email, String senha) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new BusinessException("Já existe uma conta com esse e-mail");
        }

        Family family = new Family();
        family.setNome(nome); // pode virar editável depois; por ora, nasce com o nome do fundador
        Family familySalva = familyRepository.save(family);

        GrauParentesco grauFundador = grauParentescoRepository.findAllOrdenadoPorCategoria().stream()
                .filter(g -> GRAU_PADRAO_FUNDADOR.equals(g.getNome()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Grau de parentesco padrão '" + GRAU_PADRAO_FUNDADOR + "' não encontrado - schema desatualizado?"));

        User admin = new User();
        admin.setFamily(familySalva);
        admin.setNome(nome);
        admin.setEmail(email);
        admin.setSenhaHash(passwordEncoder.encode(senha));
        admin.setGrauParentesco(grauFundador);
        admin.setPodeGerenciarTarefas(true);

        User adminSalvo = userRepository.save(admin);

        String token = tokenPort.generateToken(
                adminSalvo.getId(), familySalva.getId(), adminSalvo.getEmail(), true);

        return new AuthUseCase.LoginResult(token, adminSalvo);
    }
}
