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

<!-- **Data Persistence:**
Both services utilize a repository layer with Hibernate ORM for interacting with their respective databases, abstracting database operations and mapping objects to relational tables. -->


### Learning Objectives

- Understand microservice design with Spring Boot
- Integrate synchronous and asynchronous service communication
- Apply reactive programming using WebClient and WebFlux
- Use RabbitMQ for decoupled messaging
<!-- - Implement RESTful APIs with layered separation of concerns -->
<!-- - Simplify database access using JPA and Hibernate -->

1. **What is a microservice?** 
<br>
A microservice is a small, independently deployable service that focuses on a specific business capability and communicates with other services over a network.

2. **Microservice Architecture vs Monolithic Architecture**
<br>

| Microservices Architecture                                                                 | Monolithic Architecture                                                        |
|--------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| Structures the application as a collection of small, independent, loosely coupled services. Each service focuses on a specific business capability. | Builds the entire application as a single, indivisible unit.                   |
| Independently deployable services                                                          | Entire application is deployed as one unit                                     |
| Can scale individual services as needed                                                    | Scales the entire application together                                         |
| Supports different languages and databases (polyglot architecture)                         | Typically uses a single language and tech stack                                |
| Failure in one service is isolated and doesn't affect others                               | Failure in one area can affect the entire system                               |
| Smaller, focused teams can work on individual services independently                       | Larger teams collaborate on a single codebase                                  |

3. **main benefits of adopting a Microservices Architecture**
- Independent Deployment: Services can be deployed and updated without affecting others.

- Scalability: Individual services can be scaled up or down based on demand, optimizing resource usage.

- Technology Diversity (Polyglot): Teams can choose the best technology stack (language, database) for each service.

- Resilience/Fault Isolation: A failure in one service doesn't necessarily bring down the entire system.

- Faster Development Cycles: Smaller, focused teams can develop and iterate on services more quickly.

- Easier Maintenance: Smaller codebases are easier to understand, maintain, and refactor.

4. **challenges or drawbacks of Microservices**
- Increased Complexity: Managing a distributed system with many services is inherently more complex than a monolith (e.g., deployment, testing, monitoring).

- Distributed Transactions: Ensuring data consistency across multiple services is challenging.

- Inter-service Communication: Requires robust mechanisms for communication (synchronous/asynchronous).

- Operational Overhead: Requires more sophisticated infrastructure for deployment, monitoring, logging, and tracing.

- Data Management: Deciding on data ownership and consistency across services can be complex.

- Debugging: Tracing issues across multiple services can be difficult.

5. **Synchronous and Asynchronous communication in Microservices**
- **Synchronous**: The caller waits for an immediate response (e.g., REST API call).
    - When to use: When an immediate response is required for the client to proceed (e.g., user login, retrieving product details for an order).
    - Pros: Simple to implement, immediate feedback.
    - Cons: Tightly coupled, blocking, can lead to cascading failures if a service is down.
- **Asynchronous**: The caller doesn't wait; the request is handled in the background (e.g., message queue with RabbitMQ).
    - When to use: For long-running operations, event-driven architectures, decoupling services, or when high throughput is needed without blocking the client (e.g., order processing notifications, background tasks).
    - Pros: Decoupled services, improved resilience, better scalability, non-blocking.
    - Cons: More complex to implement, harder to debug, eventual consistency.


