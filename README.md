# model.Invoice Management System

A simple command-line based model.Invoice Management System written in Java.  
This idea is to set up a simple dashboard to manage and play with invoice repository :)

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
   