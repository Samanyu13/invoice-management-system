package main.java.com.invoicemanager.repository;

import java.util.ArrayList;

import main.java.com.invoicemanager.model.Invoice;

public interface InvoiceRepository {
    boolean save(Invoice invoice);
    ArrayList<Invoice> getAll();
    boolean existsById(String id);
    void clearAll();
}