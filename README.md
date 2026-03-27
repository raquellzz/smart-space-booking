# Smart Space Booking

Sistema Inteligente de Governança e Reservas para Coworking Corporativo. Uma plataforma centralizada que revoluciona a gestão de espaços através de Economia de Reputação e Auditoria Visual por IA.

O sistema vai além do agendamento comum ao implementar um modelo de Economia de Reputação, onde o zelo pelo patrimônio é a moeda de troca. Através de auditoria visual por Inteligência Artificial e uma pontuação de confiança (Trust Score), o sistema automatiza a fiscalização e garante que os espaços estejam sempre prontos para o próximo usuário.

## Tecnologias Utilizadas

* **Front-end:** React.js + Vite
* **Back-end:** Java (Spring Boot)
* **Banco de Dados:** PostgreSQL
* **Inteligência Artificial:** Integração com Gemini 1.5 Flash API

## Como rodar o projeto localmente

### Pré-requisitos
Certifique-se de ter instalado em sua máquina:
* **Java 21** (ou superior)
* **PostgreSQL**
* **Node.js** (v22+ recomendado via NVM)

### 1. Configurando o Banco de Dados
1. Abra o seu PostgreSQL e crie um banco de dados chamado `smart_space_db`.
2. No repositório, navegue até `backend/src/main/resources/application.properties`.
3. Verifique se as credenciais de `spring.datasource.username` e `spring.datasource.password` correspondem ao seu banco local.

### 2. Rodando o Back-end (Spring Boot)
1. Abra um terminal na raiz do projeto e acesse a pasta do back-end:
   ```bash
   cd backend
2. Execute a aplicação utilizando o Maven Wrapper:
    ```bash
    ./mvnw spring-boot:run
    ou
    ```bash
    mvn spring-boot:run
3. A API estará rodando em http://localhost:8080.

### 3. Rodando o Front-end (React + Vite)
1. Abra um novo terminal na raiz do projeto e acesse a pasta do front-end:
   ```bash
   cd frontend
2. Instale as dependências do Node:
    ```bash
   npm instal
3. Inicie o servidor de desenvolvimento:
    ```bash
    npm run dev
4. A interface estará disponível no navegador, geralmente em http://localhost:5173.

## Equipe

- Bianca Maciel
- Luigi Soares
- Raquel Freire