# Invoice Management System

A simple command-line based Invoice Management System written in Java.  
This idea is to set up a simple CLI to manage and work on invoice data.

---

## Features

- Add new invoices
- Peek at invoices
- Simple Invoice filtering by:
  - Supplier name
  - Amount threshold
- Report Generator (export to Markdown):
  - Overall report
- Import invoice data from a CSV file

---

## Requirements

- Java 17+ (should work fine with any modern Java version)
- Maven 3.8+ (for building and running easily)
- No database — fully in-memory

---

## How to Setup

1. Clone the repo and cd in:
   ```bash
   git clone https://github.com/Samanyu13/invoice-management-system.git
   cd invoice-management-system
   ```
2. Build the Project using Maven
   ```bash
   mvn clean compile
   ```
3. Run the Application
   ```bash
   mvn exec:java
   ```
4. Run tests
   ```bash
   mvn test
   ```
   
## CSV Import Format
To import invoice repository from a CSV file, use the following format:
```bash
  invoiceId,supplierName,amount,invoiceDate
  INV001,Supplier A,5000.00,2024-01-15
  INV002,Supplier B,7500.00,2024-02-10
  ....
```



## Invoice Management System – Basic Flow and Interaction

```text
     +----------------------+
     |   InvoiceApp.java    | <- Our entry point
     +----------------------+
                |
                |
                v
     +----------------------+
     |      CLI Layer       | <-- Handles and facilitates user input/output
     |----------------------|
     | -       CLI          | <-- Handles interactor and maps user inputs to commands
     | -     Command        | <-- Represents individual user option
     | - CliHandlerFactory  | <-- Sets up and maps Commands to their handlers
     | -    MenuOptions     | <-- Menu options and prompts as constants, to enhance readability
     +----------------------+
                |
                |
                v
     +----------------------+
     |     Service Layer    | <-- Business Logic
     |----------------------|
     | -  InvoiceService    | <-- Add, import, filter, list invoices
     | -   ReportService    | <-- Report creation and storage handling
     +----------------------+
                |
                | 
                v
     +----------------------+
     |   Repository Layer   | <-- Handles data storage and retrieval
     |----------------------|
     | - InvoiceRepository  | <-- Store, retrieve, and query on data layer
     +----------------------+
                |
                |
                v
     +----------------------+
     |      Data Layer      | <-- defines the structure of our data (Invoice)
     |----------------------|
     | -     Invoice        | <-- Entity
     +----------------------+
   