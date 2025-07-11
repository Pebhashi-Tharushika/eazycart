## Microservices Architecture
This repository showcases a practical implementation of microservices architecture focusing on various inter-service communication approaches using Spring Boot. It provides a hands-on example of building robust and scalable distributed systems.

### Project Overview
This project implements two core microservices: Order Service and Product Service. Both services are developed using Spring Boot and follow a layered architecture.

**Services**:

Order Service: Manages order creation, retrieval, and interacts with the Product Service to validate product availability.

Product Service: Manages product information, including stock levels.

#### Communication Mechanisms Demonstrated:

- **Synchronous Communication:**

    - HttpClient: Used for traditional blocking HTTP calls between services.

    - RestClient: Spring's new non-blocking HTTP client for making RESTful calls.

- **Asynchronous Communication:**

    - WebClient (Spring WebFlux): A non-blocking, reactive HTTP client for making asynchronous calls, suitable for high-throughput scenarios.

    - RabbitMQ: A robust message broker used for event-driven communication. Services publish events to queues, and other services consume these events without direct coupling, enhancing decoupling and resilience.


### Learning Objectives

- Understand microservice design with Spring Boot
- Integrate synchronous and asynchronous service communication
- Apply reactive programming using WebClient and WebFlux
- Use RabbitMQ for decoupled messaging

