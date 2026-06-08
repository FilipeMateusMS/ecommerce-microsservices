# 🛒 E-Commerce Microservices

Projeto desenvolvido para estudo de **Microsserviços com Spring Boot**, aplicando conceitos modernos de arquitetura distribuída, mensageria e observabilidade.

## Comuniçação entre os sreviços
<img width="1134" height="527" alt="image" src="https://github.com/user-attachments/assets/9c843ce8-ee1c-422f-9741-10e154b7f0c5" />



## 🚀 Tecnologias Utilizadas

### Backend

* Java 21
* Spring Boot
* Spring Cloud
* Spring Data JPA
* Spring Data MongoDB
* Spring Cloud Gateway
* Spring Cloud Config
* Spring Eureka
* Spring Kafka
* Spring Mail
* PostgreSQL
* MongoDB
* Apache Kafka
* Zookeeper
* Zipkin
* Keycloak

  ## Modelagem
  <img width="1102" height="582" alt="image" src="https://github.com/user-attachments/assets/78b9ea4f-5947-4ce5-b711-496720ad9348" />


### Infraestrutura

* Docker
* Docker Compose

---

# 📂 Estrutura do Projeto

```text
config-server
customer
discovery
gateway
notification
order
payment
product
```

---

# 🏗 Arquitetura

O projeto é composto pelos seguintes microsserviços:

| Serviço              | Responsabilidade                            | Porta |
| -------------------- | ------------------------------------------- | ----- |
| Config Server        | Configurações centralizadas                 | -     |
| Discovery Service    | Registro e descoberta dos serviços (Eureka) | 8761  |
| Gateway Service      | API Gateway                                 | 8222  |
| Customer Service     | Gerenciamento de clientes                   | 8090  |
| Product Service      | Catálogo de produtos                        | 8050  |
| Order Service        | Processamento de pedidos                    | 8070  |
| Payment Service      | Processamento de pagamentos                 | 8060  |
| Notification Service | Envio de notificações                       | 8040  |

---

# ⚙ Config Server

Todos os microsserviços herdam suas configurações básicas do Config Server.

### Configuração compartilhada

```yaml
eureka:
  instance:
    hostname: localhost

  client:
    service-url:
      defaultZone: http://${eureka.instance.hostname}:8761/eureka/

spring:
  cloud:
    config:
      override-system-properties: false

management:
  tracing:
    sampling:
      probability: 1.0
```
---

### Rotas

| Endpoint                 | Serviço          |
| ------------------------ | ---------------- |
| `/api/v1/customers/**`   | Customer Service |
| `/api/v1/orders/**`      | Order Service    |
| `/api/v1/order-lines/**` | Order Service    |
| `/api/v1/products/**`    | Product Service  |
| `/api/v1/payments/**`    | Payment Service  |

# 🗄 Bancos Utilizados

| Serviço      | Banco      |
| ------------ | ---------- |
| Customer     | MongoDB    |
| Notification | MongoDB    |
| Product      | PostgreSQL |
| Order        | PostgreSQL |
| Payment      | PostgreSQL |

---

# 🐳 Docker como Executar

## 1. Subir a infraestrutura

```bash
docker-compose up -d
```

## 2. Executar os microsserviços na ordem:

1. Config Server
2. Discovery Service
3. Gateway Service
4. Customer Service
5. Product Service
6. Payment Service
7. Order Service
8. Notification Service




⭐ Projeto desenvolvido para fins de estudo e aprendizado em arquitetura de microsserviços.
