package com.familia.listadetarefas.domain.port.in;

import com.familia.listadetarefas.domain.model.TaskTemplate;
import com.familia.listadetarefas.domain.model.TaskTemplateUserStats;
import com.familia.listadetarefas.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface TaskTemplateUseCase {

    List<TaskTemplate> listarCatalogo(User quemEstaAgindo);

    TaskTemplate criar(User quemEstaAgindo, String tituloPadrao, String descricaoPadrao,
                        String categoria, Integer pontosPadrao);

    List<TaskTemplateUserStats> estatisticasPorPessoa(UUID taskTemplateId);
}
