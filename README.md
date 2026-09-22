# Lista de Tarefas — API (Java 21 + Spring Boot 3.3 + Maven)

Arquitetura **hexagonal (ports & adapters)**. Backend que serve tanto o
web admin quanto o app mobile. Autenticação via JWT, banco Postgres
(schema gerenciado pelo repositório `lista-tarefas-changelog`, via
Liquibase — **não** pelo Hibernate).

## Estrutura de pastas

```
src/main/java/com/familia/listadetarefas/
├── domain/                          ← núcleo, zero dependência de framework
│   ├── model/                        Task, User, Family... (POJOs puros)
│   ├── port/in/                      o que a aplicação OFERECE (casos de uso)
│   ├── port/out/                     o que a aplicação PRECISA (repositórios, senha, token)
│   └── exception/                    exceções de negócio
│
├── application/service/             ← implementa as portas "in", usa só portas "out"
│                                       (não conhece Spring Web, JPA, JWT ou HTTP)
│
├── adapter/
│   ├── in/web/                       ← adaptador de ENTRADA: HTTP
│   │   ├── controller/                depende das portas "in" (interfaces), nunca das classes concretas
│   │   ├── dto/                       formato JSON exposto - pode mudar sem afetar o domínio
│   │   ├── mapper/                    domínio -> DTO de resposta
│   │   ├── security/                  JwtAuthenticationFilter, UserPrincipal (só aqui o Spring Security aparece)
│   │   └── exception/                 traduz exceção de domínio -> código HTTP
│   │
│   └── out/                          ← adaptadores de SAÍDA: implementam as portas "out"
│       ├── persistence/               entidades JPA + Spring Data + mappers + *RepositoryAdapter
│       └── security/                  JwtTokenAdapter, BCryptPasswordAdapter
│
└── config/                          ← fiação do Spring (SecurityConfig)
```

## A regra que sustenta tudo isso

**As setas de dependência só apontam para dentro.** `adapter` depende de
`domain`. `application` depende de `domain`. Nada dentro de `domain`
importa `org.springframework.*`, `jakarta.persistence.*` ou
`io.jsonwebtoken.*`. Se isso for violado em algum PR futuro, a arquitetura
hexagonal deixou de existir — vale um teste de arquitetura (ArchUnit) para
travar isso automaticamente; não incluído aqui por escopo, mas é o próximo
passo natural.

## Diferenças-chave em relação a uma arquitetura em camadas comum

1. **`Task` é um domínio rico, não um DTO anêmico.** As regras
   `concluir()` e `avaliar()` são métodos do próprio objeto `Task`
   (`domain/model/Task.java`), não lógica solta em um service. Isso
   garante que a tarefa nunca fica num estado inconsistente, não importa
   quem a chame.
2. **Nenhum service lê `SecurityContextHolder`.** Quem está autenticado é
   extraído no controller (`@AuthenticationPrincipal`) e passado como
   parâmetro explícito (`User quemEstaAgindo`) para cada caso de uso. A
   camada de aplicação não sabe que existe JWT, sessão ou HTTP.
3. **Controllers dependem de interfaces (`TaskUseCase`), não de
   `TaskService`.** Cumpre o "D" do SOLID de verdade — trocar a
   implementação não exige tocar no controller.
4. **Entidade JPA ≠ modelo de domínio.** `TaskEntity` (com `@Entity`,
   `@ManyToOne` etc.) só existe em `adapter/out/persistence`. O que o
   resto do sistema usa é `Task` (POJO puro).

## Rodando localmente

Pré-requisitos: Java 21, Maven, um Postgres com o schema já aplicado
(rode o Liquibase do repositório `lista-tarefas-changelog` primeiro,
incluindo o changeset `002-task-history-fk-ajuste.sql`).

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=tarefas_db
export DB_USER=tarefas_app
export DB_PASSWORD=sua_senha
export JWT_SECRET=um-segredo-de-pelo-menos-32-caracteres

mvn spring-boot:run
```

API sobe em `http://localhost:8080`.

## Criando o primeiro usuário (bootstrap)

Como todo endpoint exige autenticação exceto `/auth/login`, insira o
primeiro admin manualmente via SQL:

```sql
INSERT INTO families (id, nome) VALUES (gen_random_uuid(), 'Família Bento');

INSERT INTO users (id, family_id, nome, email, senha_hash, grau_parentesco_id, pode_gerenciar_tarefas)
VALUES (
    gen_random_uuid(),
    (SELECT id FROM families WHERE nome = 'Família Bento'),
    'Erick Bento',
    'erick@exemplo.com',
    '$2a$10$SEU_HASH_BCRYPT_AQUI', -- gere com BCryptPasswordEncoder ou um gerador online
    (SELECT id FROM graus_parentesco WHERE nome = 'Pai'),
    true
);
```

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| POST | `/auth/login` | Login, retorna JWT |
| GET | `/tasks?data=&status=&assignedTo=` | Lista tarefas com filtros |
| POST | `/tasks` | Cria tarefa (a partir de um item do catálogo) |
| PUT | `/tasks/{id}` | Altera tarefa |
| DELETE | `/tasks/{id}` | Exclui tarefa |
| PATCH | `/tasks/{id}/concluir` | Marca como concluída |
| PATCH | `/tasks/{id}/avaliar` | Avalia — trigger no banco calcula pontos e stats |
| GET | `/tasks/{id}/history` | Histórico de auditoria da tarefa |
| GET | `/task-templates` | Lista o catálogo |
| POST | `/task-templates` | Cria item no catálogo |
| GET | `/task-templates/{id}/stats` | Estatísticas por pessoa daquele tipo de tarefa |
| GET | `/users` | Lista membros da família |
| POST | `/users` | Adiciona membro da família |
| GET | `/graus-parentesco` | Lista completa de graus (para dropdown) |
| GET | `/ranking?periodo=semana\|mes` | Ranking de pontos |

## Métricas para o Prometheus

`GET /actuator/prometheus` já está exposto. Adicione ao `prometheus.yml`
da stack `ec2-monitoring`:

```yaml
- job_name: "spring-backend"
  metrics_path: "/actuator/prometheus"
  static_configs:
    - targets: ["<ip-privado-do-backend>:8080"]
```

## Build e Docker

```bash
mvn clean package
docker build -t lista-tarefas-api .
docker run -p 8080:8080 --env-file .env lista-tarefas-api
```

Este `Dockerfile` é o que a stack Terraform `ec2-backend` espera quando
você preenche `backend_repo_url` no `.tfvars`.

## O que ainda falta (próximos passos sugeridos)

- **Teste de arquitetura (ArchUnit)** — automatizar a verificação de que
  `domain` nunca importa Spring/JPA/JWT, travando isso no CI em vez de
  depender de revisão manual.
- **Refresh token** — hoje o JWT expira em 24h sem renovação automática.
- **Paginação** em `/tasks` e `/task-templates`.
- **Testes de integração** com Testcontainers.
- O adaptador `TaskRepositoryAdapter.save()` reconstrói a entidade a cada
  chamada (via `PersistenceMapper`), o que faz o Hibernate tratar updates
  como `merge` (uma consulta extra). Funciona corretamente, mas para
  otimizar performance no futuro, dá para carregar a entidade gerenciada
  primeiro e só aplicar os campos alterados nela.
