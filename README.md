# model.Invoice Management System

A simple command-line based model.Invoice Management System written in Java.  
This idea is to set up a simple dashboard to manage and play with invoice data :)

---

## Features

- Add new invoices
- List all invoices
- Simple Invoice filtering by:
  - Supplier name
  - Amount threshold
- Report Generator (export to Markdown):
  - Overall report
- Import invoice data from a CSV file

---

## Requirements

- Java 17+ (should work fine with any modern Java version)
- No external libraries used (as of now)
- No database — fully in-memory

---

## How to Setup

1. Clone the repo and cd in:
   ```bash
   git clone https://github.com/Samanyu13/invoice-management-system.git
   cd invoice-management-system
   ```
2. Compile the code
   ```bash
   javac -d out $(find src -name "*.java")
   ```
3. Run the code
   ```bash
   java -cp out InvoiceApp
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
   