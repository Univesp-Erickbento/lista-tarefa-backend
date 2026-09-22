package com.familia.listadetarefas.domain.port.in;

import com.familia.listadetarefas.domain.model.User;

import java.util.List;

public interface RankingUseCase {

    List<Entrada> ranking(User quemEstaAgindo, String periodo);

    record Entrada(User user, double pontosNoPeriodo, int posicao) {
    }
}
