# ACH Transaction Reporting System

This project is a Spring Boot-based ACH Transaction Reporting System that utilizes XSLT to transform database-driven XML data to dynamically render a styled HTML report.

## Project Overview

The application demonstrates a modern architecture where data is stored in an in-memory H2 database, dynamically converted to XML, and then transformed to HTML using XSLT. The system leverages Spring Data JPA for data persistence and XSLT for presentation logic, providing a clean separation of concerns.

## Key Features

*   **Database-Driven Architecture**: Data stored in H2 in-memory database with JPA entities
*   **Dynamic XML Generation**: Service layer converts database records to XML format on-the-fly
*   **XSLT Transformation**: Uses `ach-transform.xsl` to convert XML into a clean, responsive HTML table
*   **Dynamic Business Logic**: 
    *   Transactions over **$25,000** are automatically flagged as `HIGH VALUE / REQUIRES APPROVAL`
    *   Standard transactions are labeled as `STANDARD / NORMAL`
*   **HTMX Integration**: Partial page updates without full reload on `/home` endpoint
*   **Comprehensive Logging**: Structured logging across all layers (Controller, Service, Config)
*   **H2 Console Access**: Built-in database console for data inspection and management

## Tech Stack

*   **Java 17**
*   **Spring Boot 3.2.0**
*   **Spring Data JPA**
*   **H2 Database** (in-memory)
*   **XSLT 1.0**
*   **HTMX 1.9.12**
*   **Maven**
*   **SLF4J Logging**

## Project Structure

```
.
├── pom.xml
└── src
    └── main
        ├── java/org/example
        │   ├── AchApplication.java
        │   ├── config
        │   │   └── DataInitializer.java
        │   ├── controller
        │   │   └── AchController.java
        │   ├── model
        │   │   └── AchTransaction.java
        │   ├── repository
        │   │   └── AchTransactionRepository.java
        │   └── service
        │       └── AchService.java
        └── resources
            ├── application.properties
            ├── ach-transactions.xml
            ├── ach-transform.xsl
            └── templates
                └── home.html
```

## Getting Started

### Prerequisites

*   Java 17 or higher
*   Maven 3.6 or higher

### Running the Application

1.  Navigate to the project directory
2.  Run the application using Maven:
    ```bash
    mvn spring-boot:run
    ```

3.  Access the application:
    *   **Home Page**: [http://localhost:8080/home](http://localhost:8080/home)
    *   **ACH Report**: [http://localhost:8080/ach](http://localhost:8080/ach)
    *   **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
        *   JDBC URL: `jdbc:h2:mem:achdb`
        *   Username: `sa`
        *   Password: (leave empty)

## Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/home` | Loads home page with HTMX-enabled button to fetch transactions |
| `GET` | `/ach` | Fetches data from database, converts to XML, and renders HTML via XSLT |
| `GET` | `/h2-console` | Access H2 database console for data inspection |

## Architecture Flow

1. **Database Layer**: H2 in-memory database stores ACH transaction records
2. **Repository Layer**: Spring Data JPA repository handles data access
3. **Service Layer**: Retrieves data from database and converts to XML format
4. **Controller Layer**: Applies XSLT transformation to convert XML to HTML
5. **Presentation**: HTML rendered in browser with conditional formatting

**Flow**: `Database → Repository → Service (XML generation) → Controller (XSLT transform) → HTML Response`

## Logging

The application includes comprehensive logging:
*   **INFO**: High-level operations (requests, initialization, completion)
*   **DEBUG**: Detailed step-by-step execution (data fetching, transformations)
*   **ERROR**: Exception handling and error scenarios

Logs track the complete request lifecycle from database query to HTML response.
