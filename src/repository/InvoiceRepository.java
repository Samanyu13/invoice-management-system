package repository;

import java.util.ArrayList;

import model.Invoice;

public interface InvoiceRepository {
    boolean save(Invoice invoice);
    ArrayList<Invoice> getAll();
    boolean existsById(String id);
    void clearAll();
}