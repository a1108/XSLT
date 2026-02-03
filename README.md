# ACH Transaction Reporting System

This project is a Spring Boot-based ACH Transaction Reporting System that utilizes XSLT to transform data from an XML file to dynamically render a styled HTML report.

## Project Overview

The application demonstrates a decoupled architecture where data storage (XML) and presentation logic (XSLT/HTML) are separated. By leveraging the power of XSLT, the system can perform complex data transformations and apply business rules without the need for intermediate Java model objects.

## Key Features

*   **XSLT Transformation**: Uses `ach-transform.xsl` to convert raw transaction data into a clean, responsive HTML table.
*   **Dynamic Business Logic**: 
    *   Transactions over **$25,000** are automatically flagged as `HIGH VALUE / REQUIRES APPROVAL`.
    *   Standard transactions are labeled as `STANDARD / NORMAL`.
*   **Automated Formatting**: Handles currency symbols, decimal places, and date display directly within the stylesheet.
*   **Minimalist Controller**: A streamlined Spring Boot controller that focuses solely on executing the transformation.

## Tech Stack

*   **Java 17**
*   **Spring Boot 3.2.0**
*   **XSLT 1.0**
*   **Maven**

## Project Structure
text 
. 
├── pom.xml 
└── src 
└── main 
├── java 
│ └── org 
│ └── example 
│ ├── AchApplication.java 
│ └── controller 
│ └── AchController.java 
└── resources 
├── ach-transactions.xml 
└── ach-transform.xsl

## Getting Started

### Prerequisites

*   Java 17 or higher
*   Maven 3.6 or higher

### Running the Application

1.  Navigate to the project directory.
2.  Run the application using Maven:
    bash
    mvn spring-boot:run

3.  Access the report in your browser:
    [http://localhost:8080/ach](http://localhost:8080/ach)

## Endpoint

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/ach` | Renders the ACH Transaction report via XSLT transformation. |
