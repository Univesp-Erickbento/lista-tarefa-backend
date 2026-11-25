package com.familia.listadetarefas.application.service;

import com.familia.listadetarefas.domain.model.Task;
import com.familia.listadetarefas.domain.model.User;
import com.familia.listadetarefas.domain.port.in.RankingUseCase;
import com.familia.listadetarefas.domain.port.out.TaskRepository;
import com.familia.listadetarefas.domain.port.out.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
public class RankingService implements RankingUseCase {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public RankingService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entrada> ranking(User quemEstaAgindo, String periodo) {
        OffsetDateTime desde = "mes".equalsIgnoreCase(periodo)
                ? OffsetDateTime.now().minus(30, ChronoUnit.DAYS)
                : OffsetDateTime.now().minus(7, ChronoUnit.DAYS);

        List<User> membros = userRepository.findByFamilyId(quemEstaAgindo.getFamily().getId());
        Map<User, Double> pontosPorUsuario = new LinkedHashMap<>();

        for (User membro : membros) {
            double total = taskRepository.historicoDoUsuario(membro.getId(), desde).stream()
                    .map(Task::getPontosGanhos)
                    .filter(Objects::nonNull)
                    .mapToDouble(BigDecimal::doubleValue)
                    .sum();
            pontosPorUsuario.put(membro, total);
        }

        List<Map.Entry<User, Double>> ordenado = pontosPorUsuario.entrySet().stream()
                .sorted(Comparator.comparingDouble((Map.Entry<User, Double> e) -> e.getValue()).reversed())
                .toList();

        return IntStream.range(0, ordenado.size())
                .mapToObj(i -> new Entrada(ordenado.get(i).getKey(), ordenado.get(i).getValue(), i + 1))
                .toList();
    }
}
