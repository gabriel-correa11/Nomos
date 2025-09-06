# API do Nomos: Guia para o Time de Front-End & UX

Olá! Este é o guia central da API do Nomos. Se você está aqui para construir a interface e a experiência do usuário,
este documento vai te mostrar como "conversar" com o nosso backend para dar vida à plataforma.

A ideia é que este documento seja o nosso ponto de referência para que a comunicação entre o design e a lógica do sistema seja perfeita.

## O Que a API Faz?

A API é o cérebro do Nomos. Ela é responsável por:
Cadastrar novos usuários (candidatos e recrutadores).
Gerenciar vagas de emprego (criar, listar, etc.).
Cuidar da segurança e da autenticação.

Basicamente, qualquer dado que você precise mostrar na tela ou enviar para o sistema, você vai pedir ou entregar para a API.

## Como Testar a API (Sua Ferramenta Essencial)

Para ver a API funcionando e testar o que ela retorna, você vai precisar de um cliente de API. Recomendamos o **Postman** ou o **Insomnia**.
Eles permitem que você envie requisições para os nossos endpoints e veja exatamente a resposta em formato JSON, que é o que você vai usar para popular o design.

O endereço base da nossa API de desenvolvimento é: `http://localhost:8080`

## Guia de Endpoints

Aqui estão as "portas de comunicação" (endpoints) que a API oferece até agora.

---

### 🏛️ **Módulo de Autenticação (`/auth`)**

Tudo relacionado a contas de usuário.

#### `POST /auth/register`
**Função:** Cadastrar um novo candidato.

Este é o endpoint que o formulário de "Criar Conta" do candidato vai chamar.

-   **Método:** `POST`
-   **URL:** `http://localhost:8080/auth/register`
-   **Dados a Enviar (formato JSON):**

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
-   **Resposta de Sucesso:** A API retornará uma mensagem simples de confirmação: `Candidato registrado com sucesso!` e o status `201 Created`.

---

### ⚖️ **Módulo de Vagas (`/jobs`)**

Tudo relacionado às oportunidades de emprego.

#### `GET /jobs`
**Função:** Obter a lista de todas as vagas cadastradas.

Perfeito para a página principal ou para a tela de "Buscar Vagas".

-   **Método:** `GET`
-   **URL:** `http://localhost:8080/jobs`
-   **Dados de Resposta (formato JSON):** A API devolverá uma lista (array) de vagas. Cada vaga terá a seguinte estrutura:

    ```json
    [
        {
            "id": 1,
            "titulo": "Advogado(a) Pleno - Contencioso",
            "descricao": "Descrição completa da vaga, responsabilidades, requisitos...",
            "localizacao": "Cuiabá, MT"
        },
        {
            "id": 2,
            "titulo": "Estágio em Direito Digital",
            "descricao": "Oportunidade para estudantes a partir do 7º semestre...",
            "localizacao": "São Paulo, SP"
        }
    ]
    ```

#### `POST /jobs`
**Função:** Criar uma nova vaga.

Este endpoint será usado pela interface do recrutador para publicar uma nova oportunidade.

-   **Método:** `POST`
-   **URL:** `http://localhost:8080/jobs`
-   **Dados a Enviar (formato JSON):**

    ```json
    {
        "titulo": "Título da Vaga",
        "descricao": "Descrição detalhada da vaga.",
        "localizacao": "Cidade, UF"
    }
    ```
-   **Resposta de Sucesso:** A API retornará os dados da vaga que acabou de ser criada, incluindo o `id` que foi gerado.

    ```json
    {
        "id": 3,
        "titulo": "Título da Vaga",
        "descricao": "Descrição detalhada da vaga.",
        "localizacao": "Cidade, UF"
    }
    ```

---

E é isso por enquanto! Conforme formos adicionando mais funcionalidades (login, perfil do usuário, etc.), vamos atualizando este guia.

Qualquer dúvida ou se precisar de algum dado diferente, é só me chamar. Vamos construir isso juntos!