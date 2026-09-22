package com.familia.listadetarefas.adapter.out.persistence.adapter;

import com.familia.listadetarefas.adapter.out.persistence.entity.FamilyEntity;
import com.familia.listadetarefas.adapter.out.persistence.entity.GrauParentescoEntity;
import com.familia.listadetarefas.adapter.out.persistence.entity.UserEntity;
import com.familia.listadetarefas.adapter.out.persistence.mapper.PersistenceMapper;
import com.familia.listadetarefas.adapter.out.persistence.repository.FamilyJpaRepository;
import com.familia.listadetarefas.adapter.out.persistence.repository.GrauParentescoJpaRepository;
import com.familia.listadetarefas.adapter.out.persistence.repository.UserJpaRepository;
import com.familia.listadetarefas.domain.model.User;
import com.familia.listadetarefas.domain.port.out.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final FamilyJpaRepository familyJpaRepository;
    private final GrauParentescoJpaRepository grauParentescoJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository, FamilyJpaRepository familyJpaRepository,
                                  GrauParentescoJpaRepository grauParentescoJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.familyJpaRepository = familyJpaRepository;
        this.grauParentescoJpaRepository = grauParentescoJpaRepository;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(PersistenceMapper::toDomain);
    }

    @Override
    public List<User> findByFamilyId(UUID familyId) {
        return userJpaRepository.findByFamilyId(familyId).stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public User save(User user) {
        // getReferenceById evita um SELECT extra: cria um proxy JPA que só
        // é resolvido se algum campo além do ID for acessado.
        FamilyEntity familyRef = familyJpaRepository.getReferenceById(user.getFamily().getId());
        GrauParentescoEntity grauRef = grauParentescoJpaRepository.getReferenceById(user.getGrauParentesco().getId());

        UserEntity entity = PersistenceMapper.toEntity(user, grauRef, familyRef);
        UserEntity salvo = userJpaRepository.save(entity);
        return PersistenceMapper.toDomain(salvo);
    }
}
