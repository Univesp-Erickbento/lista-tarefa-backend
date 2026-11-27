package com.familia.listadetarefas.adapter.in.web.controller;

import com.familia.listadetarefas.adapter.in.web.dto.FamilyCreateRequest;
import com.familia.listadetarefas.adapter.in.web.dto.FamilyResponse;
import com.familia.listadetarefas.adapter.in.web.security.UserPrincipal;
import com.familia.listadetarefas.domain.model.Family;
import com.familia.listadetarefas.domain.port.out.FamilyRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/families")
public class FamilyController {

    private final FamilyRepository familyRepository;

    public FamilyController(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @GetMapping
    public List<FamilyResponse> listar(@AuthenticationPrincipal UserPrincipal principal) {
        UUID familyId = principal.getDomainUser().getFamily().getId();
        Family atual = familyRepository.findById(familyId).orElse(principal.getDomainUser().getFamily());

        UUID principalId = atual.getParentFamilyId() != null
                ? atual.getParentFamilyId()
                : atual.getId();

        Family principalFamily = familyRepository.findById(principalId).orElse(atual);
        List<FamilyResponse> resultado = new ArrayList<>();
        resultado.add(toResponse(principalFamily));
        familyRepository.findByParentFamilyId(principalId).stream()
                .map(this::toResponse)
                .forEach(resultado::add);
        return resultado;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FamilyResponse criarRamo(@AuthenticationPrincipal UserPrincipal principal,
                                    @Valid @RequestBody FamilyCreateRequest request) {
        UUID familyId = principal.getDomainUser().getFamily().getId();
        Family atual = familyRepository.findById(familyId).orElseThrow();
        UUID principalId = atual.getParentFamilyId() != null
                ? atual.getParentFamilyId()
                : atual.getId();

        if (!principal.getDomainUser().isPodeGerenciarTarefas()) {
            throw new IllegalStateException("Apenas administradores podem criar ramos da família");
        }

        Family ramo = new Family();
        ramo.setNome(request.nome().trim());
        ramo.setEndereco(request.endereco());
        ramo.setTipo("RAMO");
        ramo.setParentFamilyId(principalId);
        ramo.setCodigoConvite("RAMO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        return toResponse(familyRepository.save(ramo));
    }

    private FamilyResponse toResponse(Family family) {
        return new FamilyResponse(
                family.getId(), family.getNome(), family.getEndereco(), family.getCodigoConvite(),
                family.getTipo(), family.getParentFamilyId(), family.getCreatedAt()
        );
    }
}
