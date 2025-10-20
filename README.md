# Projeto testeVagaJunior

## Descrição
Este projeto é uma API RESTful desenvolvida em **Spring Boot 3.5.6** com Java 21, voltada para gerenciamento de pedidos (Orders). 
Possui integração com **PostgreSQL** e documentação via **Swagger/OpenAPI**. É estruturado em camadas para facilitar manutenção e testes.

## Tecnologias Utilizadas
- Java 21 (Eclipse Temurin)
- Spring Boot 3.5.6
- Spring Web
- Spring Data JPA
- PostgreSQL 16
- Lombok
- Java Faker (para dados fictícios nos testes)
- Swagger/OpenAPI (springdoc)
- Docker / Docker Compose
- Maven


## Endpoints Principais
| Método | Endpoint           | Descrição                    |
|--------|------------------|-------------------------------|
| POST   | /orders           | Cria um novo pedido           |
| GET    | /orders           | Lista pedidos por cliente     |
| PATCH  | /orders/{id}      | Atualiza status do pedido     |

## Configuração do Banco de Dados
No `application.properties`:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/kaiomuniz_db
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Docker Compose
```yaml
services:
  postgres-db:
    image: postgres:16
    container_name: teste01_db
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin
      POSTGRES_DB: kaiomuniz_db
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data

volumes:
  postgres-data:
```

## Testes Automatizados
- `OrdersApiApplicationTests.java` utiliza **JUnit 5**, **MockMvc** e **Java Faker**.
- Testa criação, listagem, atualização de status e tratamento de erros.

## Swagger/OpenAPI
- Acesse `http://localhost:8080/swagger-ui.html` para visualizar a documentação da API.

## Como Executar
1. Clonar o repositório

git clone https://github.com/KaioMuniz/testeVagaJunior.git
cd testeVagaJunior

2. Rodar com Docker Compose + Dockerfile

docker-compose up -d --build

## Contato
- Kaio Muniz - kkaioribeiro@gmail.com
