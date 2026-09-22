package com.familia.listadetarefas.domain.port.out;

import com.familia.listadetarefas.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    List<User> findByFamilyId(UUID familyId);
    User save(User user);
}
