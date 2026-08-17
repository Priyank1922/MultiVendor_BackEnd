# Scalable Multi-Vendor E-Commerce Data Access Layer

A modular **Spring Boot backend** for a multi-vendor e-commerce platform designed to manage vendors, products, categories, shopping carts, orders, and customer accounts.

The project focuses on building a scalable persistence layer using **Spring Data JPA and Hibernate**, with optimized entity relationships, transaction management, soft deletes, batch processing, and efficient database queries.

## Features

* Multi-vendor product and category management
* Customer account and profile management
* Shopping cart operations
* Order and order-item management
* Vendor management
* Complex entity relationships using JPA and Hibernate
* Soft deletion of products
* Hibernate batch processing for bulk operations
* Cascade operations and orphan removal
* N+1 query optimization using JPQL `JOIN FETCH`
* Transaction management with Spring
* PostgreSQL database integration
* Unit testing with JUnit 5

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate ORM
* PostgreSQL
* HikariCP
* Maven
* JUnit 5

## Architecture

```text
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
Spring Data JPA / Hibernate
    |
    v
PostgreSQL
```

The application follows a layered architecture that separates API handling, business logic, data access, and persistence responsibilities.

## Entity Relationships

### User and CustomerProfile

```text
User
 |
 +-- @OneToOne
       |
       v
CustomerProfile
```

A user can have an associated customer profile.

### Category and Product

```text
Category
 |
 +-- @OneToMany
       |
       v
Product
```

A category can contain multiple products.

### Product and Cart

```text
Product
 |
 +-- @ManyToMany
       |
       v
Cart
```

Products and carts are connected using a join table.

### Order and OrderItem

```text
Order
 |
 +-- @OneToMany
     CascadeType.ALL
     orphanRemoval = true
       |
       v
OrderItem
```

Order items are managed as child entities of an order.

## Hibernate Features

### Bidirectional Entity Mapping

The project demonstrates bidirectional relationships, particularly between `Order` and `OrderItem`, using:

* `@OneToMany`
* `@ManyToOne`
* `mappedBy`
* `CascadeType.ALL`

### Soft Delete

Products are logically deleted instead of being physically removed from the database.

Hibernate's soft-delete functionality is used to preserve historical product references, particularly for existing orders.

Example:

```java
@SQLDelete(sql = "UPDATE products SET active = false WHERE id = ?")
@Where(clause = "active = true")
```

### Batch Processing

Hibernate JDBC batching is configured to improve performance during bulk operations.

```properties
hibernate.jdbc.batch_size=50
```

This is useful for large inventory operations such as bulk product uploads or inventory updates performed by vendors.

### Orphan Removal

`orphanRemoval = true` automatically removes an `OrderItem` from the database when it is removed from its parent `Order` collection.

## N+1 Query Problem and Optimization

### Problem

While retrieving customer orders, accessing associated order items and products individually can generate multiple SQL queries.

```text
1 query  -> Fetch orders
N queries -> Fetch associated data
```

This creates the N+1 query problem and can significantly affect application performance as the number of orders increases.

### Solution

The project uses JPQL `JOIN FETCH` to retrieve the required associations efficiently.

```java
SELECT o
FROM Order o
JOIN FETCH o.orderItems oi
JOIN FETCH oi.product
WHERE o.customer.id = :customerId
```

This reduces unnecessary database calls by retrieving the required order, order-item, and product data through a single optimized query.

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com.example.ecommerce/
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── entity/
│   │       ├── dto/
│   │       └── EcommerceApplication.java
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    └── java/
```

## Database Configuration

Configure PostgreSQL in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.jdbc.batch_size=50
```

## Running the Project

### Clone the Repository

```bash
 git clone https://github.com/Priyank1922/MultiVendor_BackEnd.git
cd MultiVendor_BackEnd
```

### Configure PostgreSQL

Create a PostgreSQL database and update the database credentials in `application.properties`.

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The backend will start on the configured Spring Boot server port.

## Testing

JUnit 5 is used for testing application functionality.

Run the test suite using:

```bash
mvn test
```

## Frontend Repository

The frontend application is maintained in a separate repository.

**Multi-Vendor E-Commerce Frontend**

[Frontend Repository](https://github.com/Priyank1922/MultiVendor_FrontEnd)

The frontend communicates with this Spring Boot backend through REST APIs and provides the user interface for customers, vendors, products, carts, and orders.



## Key Learning Outcomes

This project demonstrates practical experience with:

* Spring Boot backend development
* Spring Data JPA
* Hibernate ORM
* Complex entity relationship mapping
* PostgreSQL database integration
* Transaction management
* Soft-delete implementation
* Hibernate batch processing
* Cascade operations
* Orphan removal
* JPQL queries
* N+1 query optimization
* Layered backend architecture
* REST API development
* Unit testing with JUnit 5

## Project Highlights

**Project Type:** Multi-Vendor E-Commerce Backend

**Primary Focus:** Scalable persistence and data-access architecture

**Backend:** Spring Boot, Spring Data JPA, Hibernate

**Database:** PostgreSQL

**Optimization:** JDBC batching and N+1 query resolution

**Frontend:** Maintained in a separate repository
