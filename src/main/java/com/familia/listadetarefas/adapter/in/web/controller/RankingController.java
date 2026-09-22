package com.familia.listadetarefas.adapter.in.web.controller;

import com.familia.listadetarefas.adapter.in.web.dto.RankingEntryResponse;
import com.familia.listadetarefas.adapter.in.web.mapper.WebMapper;
import com.familia.listadetarefas.adapter.in.web.security.UserPrincipal;
import com.familia.listadetarefas.domain.port.in.RankingUseCase;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RankingController {

    private final RankingUseCase rankingUseCase;

    public RankingController(RankingUseCase rankingUseCase) {
        this.rankingUseCase = rankingUseCase;
    }

    @GetMapping("/ranking")
    public List<RankingEntryResponse> ranking(@AuthenticationPrincipal UserPrincipal principal,
                                               @RequestParam(defaultValue = "semana") String periodo) {
        return rankingUseCase.ranking(principal.getDomainUser(), periodo).stream()
                .map(WebMapper::toResponse)
                .toList();
    }
}
