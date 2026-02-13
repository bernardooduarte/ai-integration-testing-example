# AI Integration Testing Showcase 🤖🧪

Este projeto é um **monorepo** que demonstra estratégias avançadas de testes de integração para APIs que consomem serviços de Inteligência Artificial. O objetivo principal é mostrar como garantir a resiliência da aplicação e testar diversos cenários de rede (sucesso e falha) sem realizar chamadas reais para APIs pagas ou instáveis.

O projeto apresenta a mesma solução de "Resumo de Textos" implementada em duas stacks líderes de mercado: **Node.js** e **Java (Spring Boot)**.

---

## 🛠️ Tecnologias e Ferramentas

| Recurso | Ecossistema Node.js 🟢 | Ecossistema Java ☕ |
| :--- | :--- | :--- |
| **Framework Web** | Fastify | Spring Boot 3 |
| **Gerenciador** | pnpm | Maven |
| **Cliente HTTP** | Axios | RestClient (Spring) |
| **Testes** | Vitest | JUnit 5 & AssertJ |
| **Mock de Rede** | MSW (Mock Service Worker) | WireMock |
| **Linguagem** | TypeScript | Java 17 (Records) |

---

## 🏗️ Arquitetura de Testes

A grande vantagem deste projeto é a simulação de comportamento de APIs externas:
- **Caminho Feliz ✅**: Simulamos uma resposta 200 OK da IA para validar o fluxo completo de dados.
- **Tratamento de Erros ⚠️**: Simulamos erros 500 ou instabilidades para garantir que a aplicação trate exceções de forma amigável ao usuário final.

---

## 🚀 Como Executar os Testes

### 🟢 API Node.js
1. Navegue até a pasta: `cd node-api`
2. Instale as dependências: `pnpm install`
3. Execute os testes: `pnpm test`

### ☕ API Java (Spring Boot)
1. Navegue até a pasta: `cd spring-api`
2. Execute os testes via Maven Wrapper: `./mvnw test`

---

## ⛓️ CI/CD (GitHub Actions)

O projeto conta com pipelines de **Integração Contínua** configurados. Toda vez que um novo código é enviado para o repositório, os testes de ambas as stacks são executados automaticamente no GitHub Actions, garantindo que nenhuma alteração quebre as funcionalidades existentes.