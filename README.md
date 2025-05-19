# Gestão de Revendas - API

Este projeto é uma API REST para gestão de revendas, usuários e oportunidades de negócio, construída com Spring Boot, Spring Security, JPA, MySQL e Docker.

---

## Sumário

- [Funcionalidades](#funcionalidades)
- [Requisitos](#requisitos)
- [Configuração do Ambiente](#configuração-do-ambiente)
- [Execução com Docker](#execução-com-docker)
- [Documentação da API (Swagger)](#documentação-da-api-swagger)
- [Estrutura das Entidades](#estrutura-das-entidades)
- [Regras de Negócio e Permissões](#regras-de-negócio-e-permissões)
- [Exemplos de Uso (Endpoints)](#exemplos-de-uso-endpoints)
- [Observações](#observações)

---

## Funcionalidades

- Cadastro, consulta, edição e exclusão de **revendas** (lojas).
- Cadastro, autenticação e gerenciamento de **usuários** com diferentes cargos (PROPRIETARIO, ADMINISTRADOR, GERENTE, ASSISTENTE).
- Controle de acesso por cargo e vínculo com a revenda.
- Cadastro e gerenciamento de **oportunidades** de atendimento/negociação, com distribuição automática para assistentes.
- Registro de dados do cliente e veículo de interesse em cada oportunidade.
- Controle de status da oportunidade (NOVO, EM_ATENDIMENTO, CONCLUIDO), com motivo de conclusão obrigatório.
- Registro de datas de atribuição e conclusão das oportunidades.

---

## Requisitos

- **Java 17+**
- **Maven 3.8+**
- **Docker e Docker Compose**
- **Postman** (ou outro cliente HTTP para testes)

---

## Configuração do Ambiente

1. **Clone o repositório:**
   ```sh
   git clone <url-do-repositorio>
   cd gestao-revendas
   ```

2. **Variáveis de ambiente e configurações:**
   - O banco de dados é configurado em `src/main/resources/application.yml`:
     - Host: `localhost`
     - Porta: `3308`
     - Usuário: `root`
     - Senha: `root`
     - Banco: `AutoDealDB`
   - O contexto da aplicação é `/mobi`.

3. **Banco de dados:**
   - O banco é criado automaticamente via Docker Compose e inicializado com o script `init.sql`.

---

## Execução com Docker

1. **Suba o banco de dados MySQL:**
   ```sh
   docker-compose up -d
   ```
   Isso irá criar um container MySQL acessível em `localhost:3308`.

2. **Build e execução da aplicação:**
   ```sh
   ./mvnw clean package
   ./mvnw spring-boot:run
   ```
   A aplicação estará disponível em:  
   `http://localhost:8080/mobi`

---

## Documentação da API (Swagger)

Acesse a documentação interativa da API em:  
[http://localhost:8080/mobi/swagger-ui/index.html](http://localhost:8080/mobi/swagger-ui/index.html)

---

## Estrutura das Entidades

### Revenda

- `idRevenda` (UUID, único)
- `nomeSocial` (String, obrigatório)
- `cnpj` (String, obrigatório, único e validado)

### Usuário

- `idUsuario` (UUID, único)
- `nomeCompleto` (String, obrigatório)
- `email` (String, obrigatório, único)
- `senha` (String, obrigatório, criptografada)
- `cargo` (Enum: PROPRIETARIO, ADMINISTRADOR, GERENTE, ASSISTENTE)
- `revenda` (vínculo obrigatório)

### Oportunidade

- `idOportunidade` (UUID, único)
- `status` (Enum: NOVO, EM_ATENDIMENTO, CONCLUIDO)
- `motivoConclusao` (String, obrigatório ao concluir)
- `responsavel` (Usuário)
- `revenda` (Revenda)
- `veiculo` (marca, modelo, versão, anoModelo)
- `cliente` (nome, email, telefone)
- `dataAtribuicao` (registrada ao atribuir responsável)
- `dataConclusao` (registrada ao concluir)

---

## Regras de Negócio e Permissões

- **Autenticação:**  
  Usuários autenticam-se via e-mail e senha (BCrypt).  
  Exemplo de usuário padrão:  
  - E-mail: `admin@revenda.com`
  - Senha: `admin`

- **Cadastro de Usuários:**  
  - Apenas ADMINISTRADOR pode cadastrar usuários em qualquer revenda.
  - PROPRIETARIO e GERENTE podem cadastrar usuários apenas em sua própria revenda.
  - ASSISTENTE não pode cadastrar usuários.

- **Edição de Perfis:**  
  - Apenas ADMINISTRADOR ou PROPRIETARIO da loja podem editar perfis.

- **Acesso a Revendas:**  
  - ADMINISTRADOR pode acessar todas as revendas.
  - Demais usuários só acessam a revenda à qual estão vinculados.

- **Oportunidades:**  
  - Cada oportunidade pertence a uma revenda.
  - O atendimento é feito por um usuário da revenda.
  - O sistema distribui automaticamente oportunidades para assistentes, priorizando quem tem menos oportunidades em andamento e maior tempo sem receber uma nova.
  - PROPRIETARIO e GERENTE podem transferir oportunidades para outros assistentes.
  - Apenas o responsável pode editar a oportunidade, exceto GERENTE e PROPRIETARIO, que podem editar todas da sua loja.
  - Data de atribuição e conclusão são registradas automaticamente.

---

## Exemplos de Uso (Endpoints)

> **Todos os endpoints abaixo devem ser acessados autenticando-se via Basic Auth.**

### Revenda

- **Criar Revenda**
  - `POST /mobi/api/revenda`
  - Exemplo de entrada:
    ```json
    {
      "nomeSocial": "Revenda Exemplo",
      "cnpj": "12.345.678/0001-99"
    }
    ```

- **Listar Revendas**
  - `GET /mobi/api/revenda?page=0&size=10`

- **Buscar Revenda por ID**
  - `GET /mobi/api/revenda/{idRevenda}`

- **Editar Revenda**
  - `PATCH /mobi/api/revenda/{idRevenda}`
    ```json
    {
      "nomeSocial": "Novo Nome",
      "cnpj": "12.345.678/0001-99"
    }
    ```

- **Deletar Revenda**
  - `DELETE /mobi/api/revenda/{idRevenda}`

---

### Usuário

- **Criar Usuário**
  - `POST /mobi/api/revenda/{idRevenda}/usuario`
    ```json
    {
      "nomeCompleto": "João da Silva",
      "email": "joao@revenda.com",
      "cargo": "ASSISTENTE",
      "idRevenda": "{idRevenda}",
      "senha": "senha123"
    }
    ```

- **Listar Usuários da Revenda**
  - `GET /mobi/api/revenda/{idRevenda}/usuario?page=0&size=10`

- **Buscar Usuário por ID**
  - `GET /mobi/api/revenda/{idRevenda}/usuario/{idUsuario}`

- **Editar Usuário**
  - `PATCH /mobi/api/revenda/{idRevenda}/usuario/{idUsuario}`
    ```json
    {
      "nomeCompleto": "João da Silva",
      "email": "joao@revenda.com",
      "cargo": "ASSISTENTE",
      "senha": "novaSenha"
    }
    ```

- **Deletar Usuário**
  - `DELETE /mobi/api/revenda/{idRevenda}/usuario/{idUsuario}`

- **Alterar Usuário Admin**
  - `PATCH /mobi/api/revenda/{idRevenda}/usuario/alterar-admin`
    ```json
    {
      "nomeCompleto": "Administrador",
      "email": "admin@revenda.com",
      "cargo": "ADMINISTRADOR",
      "senha": "novaSenhaAdmin"
    }
    ```

---

### Oportunidade

- **Criar Oportunidade**
  - `POST /mobi/api/revenda/{idRevenda}/oportunidade`
    ```json
    {
      "status": "NOVO",
      "motivoConclusao": "",
      "responsavel": { "idUsuario": "{idUsuarioResponsavel}" },
      "revenda": { "idRevenda": "{idRevenda}" },
      "veiculo": {
        "marca": "Toyota",
        "modelo": "Corolla",
        "versao": "GLi",
        "anoModelo": 2022
      },
      "cliente": {
        "nome": "Maria Cliente",
        "email": "maria@email.com",
        "telefone": "11999999999"
      }
    }
    ```

- **Listar Oportunidades**
  - `GET /mobi/api/revenda/{idRevenda}/oportunidade?page=0&size=10`

- **Editar Oportunidade**
  - `PATCH /mobi/api/revenda/{idRevenda}/oportunidade/{idOportunidade}`
    ```json
    {
      "status": "EM_ATENDIMENTO",
      "motivoConclusao": "",
      "responsavel": { "idUsuario": "{idUsuarioResponsavel}" },
      "revenda": { "idRevenda": "{idRevenda}" },
      "veiculo": {
        "marca": "Toyota",
        "modelo": "Corolla",
        "versao": "GLi",
        "anoModelo": 2022
      },
      "cliente": {
        "nome": "Maria Cliente",
        "email": "maria@email.com",
        "telefone": "11999999999"
      }
    }
    ```

- **Deletar Oportunidade**
  - `DELETE /mobi/api/revenda/{idRevenda}/oportunidade/{idOportunidade}`

- **Transferir Oportunidade**
  - `PATCH /mobi/api/revenda/{idRevenda}/oportunidade`
    - Parâmetro: `idOportunidade` (UUID)
    - Body:
      ```json
      {
        "status": "EM_ATENDIMENTO",
        "motivoConclusao": "",
        "responsavel": { "idUsuario": "{idNovoResponsavel}" },
        "revenda": { "idRevenda": "{idRevenda}" },
        "veiculo": {
          "marca": "Toyota",
          "modelo": "Corolla",
          "versao": "GLi",
          "anoModelo": 2022
        },
        "cliente": {
          "nome": "Maria Cliente",
          "email": "maria@email.com",
          "telefone": "11999999999"
        }
      }
      ```

---

## Observações

- O projeto utiliza **Spring Security** para autenticação e autorização.
- O banco de dados é inicializado automaticamente via Docker e script SQL.
- As senhas são sempre armazenadas de forma criptografada.
- Para testar os endpoints, utilize o Postman ou outro cliente HTTP, sempre autenticando com um usuário válido.
- O sistema está preparado para ser facilmente expandido com novas regras de negócio e endpoints.
- Tudo foi feito em um período de tempo curto, portanto podem haver bugs ou erros na aplicação(Por favor, considere isto!).

---

## Limitação Conhecida

- **Problema não resolvido:** Ainda não foi possível resolver o erro `Caused by: java.sql.SQLException: Field 'veiculo_marca' doesn't have a default value` relacionado à validação feita no método `criaOportunidade`.  
  Esse problema pode ser contornado temporariamente realizando um insert manual no banco de dados, conforme exemplo comentado no arquivo [`init.sql`](src/main/resources/init.sql).  
  A correção definitiva para essa validação ainda está pendente e deve ser tratada futuramente.

---

**Dúvidas ou sugestões?**  
Abra uma issue ou entre em contato com o desenvolvedor.
