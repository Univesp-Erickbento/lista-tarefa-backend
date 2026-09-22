package com.familia.listadetarefas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
class ListaTarefasApplicationTests {

    @Test
    void contextLoads() {
        // Verifica que o contexto Spring sobe sem erros de configuração.
        // Requer um Postgres acessível via as mesmas env vars do application.yml
        // (ou configure um application-test.yml com um banco de testes).
    }
}
