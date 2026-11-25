package com.familia.listadetarefas.adapter.out.persistence.repository;

import com.familia.listadetarefas.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * JOIN FETCH é essencial aqui: este método é chamado pelo
     * JwtAuthenticationFilter, que roda FORA de qualquer @Transactional
     * (filtros não têm transação do Spring). Sem o JOIN FETCH, family e
     * grauParentesco (ambos @ManyToOne LAZY) explodem com
     * LazyInitializationException assim que o PersistenceMapper tenta
     * lê-los - a sessão do Hibernate já fechou. O JOIN FETCH força o
     * carregamento dentro da MESMA query, então os dados já vêm prontos.
     */
    @Query("SELECT u FROM UserEntity u JOIN FETCH u.family JOIN FETCH u.grauParentesco WHERE u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.family JOIN FETCH u.grauParentesco WHERE u.family.id = :familyId")
    List<UserEntity> findByFamilyId(@Param("familyId") UUID familyId);
}
