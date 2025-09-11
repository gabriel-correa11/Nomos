# API do Nomos: Guia para o Time de Front-End & UX

Olá! Este é o guia central da API do Nomos. Se você está aqui para construir a interface e a experiência do usuário, este documento vai te mostrar como "conversar" com o nosso backend para dar vida à plataforma.

A ideia é que este documento seja o nosso ponto de referência para que a comunicação entre o design e a lógica do sistema seja perfeita.

## O Que a API Faz?

A API é o cérebro do Nomos. Ela é responsável por:
-   Cadastrar novos usuários (candidatos e recrutadores).
-   Autenticar usuários através de login.
-   Gerenciar vagas de emprego (criar, listar, etc.).
-   Cuidar da segurança e das permissões.

Basicamente, qualquer dado que você precise mostrar na tela ou enviar para o sistema, você vai pedir ou entregar para a API.

## Como Testar a API (Sua Ferramenta Essencial)

Para ver a API funcionando e testar o que ela retorna, você vai precisar de um cliente de API. Recomendamos o **Postman** ou o **Insomnia**. Eles permitem que você envie requisições para os nossos endpoints e veja exatamente a resposta em formato JSON.

O endereço base da nossa API de desenvolvimento é: `http://localhost:8080`

## Guia de Endpoints

Aqui estão as "portas de comunicação" (endpoints) que a API oferece até agora.

---

### 🏛️ **Módulo de Autenticação (`/auth`)**

Tudo relacionado a contas de usuário.

#### `POST /auth/register`
**Função:** Cadastrar um novo candidato.
-   **Método:** `POST`
-   **URL:** `http://localhost:8080/auth/register`
-   **Dados a Enviar (JSON):**
    ```json
    {
        "nome": "Nome Completo do Candidato",
        "email": "email.valido@exemplo.com",
        "senha": "uma_senha_qualquer",
        "formacao": "Instituição de ensino e curso",
        "oab": "UF/12345 (opcional)",
        "areasDeInteresse": "Direito Civil, Tecnologia",
        "apresentacao": "Um breve resumo sobre o candidato."
    }
    ```
-   **Resposta de Sucesso (201 CREATED):** Uma mensagem de confirmação: `Candidato registrado com sucesso!`

#### `POST /auth/login` (NOVO!)
**Função:** Autenticar um candidato e obter um token de acesso.
-   **Método:** `POST`
-   **URL:** `http://localhost:8080/auth/login`
-   **Dados a Enviar (JSON):**
    ```json
    {
        "email": "email.cadastrado@exemplo.com",
        "senha": "a_senha_correta"
    }
    ```
-   **Resposta de Sucesso (200 OK):** Um **Token JWT**. Este token é a "chave" que o usuário precisará para acessar as partes protegidas da API.
    ```
    eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJub21vcy1hcGkiLCJzdWIiOiJjYXJsb3MuYW5kcmFkZUBlbWFpbG5vdm8uY29tIiwiaWF0IjoxNzU3NjE2MTU1LCJleHAiOjE3NTc2MjMzNTV9.DOAIq7MMXg4NkxKWeMeURdZ9WxM4ob-Wnl7VjB4TA54
    ```

---

### **Como Usar o Token (Autenticação)**

Depois que o usuário faz o login, a API entrega um Token JWT. Esse token é como um "crachá" que precisa ser apresentado em todas as futuras requisições a endpoints que não sejam públicos.

Para fazer isso, siga os passos:

1.  No seu cliente de API (Postman, ou no código do front-end), você precisa adicionar um **Cabeçalho (Header)** à sua requisição.
2.  O nome do cabeçalho deve ser exatamente: **`Authorization`**.
3.  O valor do cabeçalho deve ser a palavra **`Bearer `** (com um espaço no final), seguida pelo token que você recebeu no login.

**Exemplo de como o Header deve ficar:**

**`Authorization`**: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJub21vcy1hcGkiLCJzdWIiOiJjYXJsb3MuYW5kcmFkZUBlbWFpbG5vdm8uY29tIiwiaWF0IjoxNzU3NjE2MTU1LCJleHAiOjE3NTc2MjMzNTV9.DOAIq7MMXg4NkxKWeMeURdZ9WxM4ob-Wnl7VjB4TA54`

---

### ⚖️ **Módulo de Vagas (`/jobs`)**

Endpoints para gerenciar as oportunidades de carreira. Atualmente, todos são públicos.

#### `GET /jobs`
**Função:** Obter a lista de todas as vagas cadastradas.
-   **Método:** `GET`
-   **URL:** `http://localhost:8080/jobs`
-   **Dados de Resposta (JSON):** Uma lista de objetos de vagas.

#### `POST /jobs`
**Função:** Criar uma nova vaga.
-   **Método:** `POST`
-   **URL:** `http://localhost:8080/jobs`
-   **Dados a Enviar (JSON):**
    ```json
    {
        "titulo": "Título da Vaga",
        "descricao": "Descrição detalhada da vaga.",
        "localizacao": "Cidade, UF"
    }
    ```
-   **Resposta de Sucesso (201 CREATED):** O objeto da vaga que acabou de ser criada.

---

É isso! Agora o fluxo de autenticação está documentado.

Bom treino! Quando você voltar, nosso próximo passo será criar o Filtro de Segurança que vai ler e validar esse token.