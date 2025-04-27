package test.java.com.invoicemanager.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import main.java.com.invoicemanager.model.Invoice;
import main.java.com.invoicemanager.repository.impl.InvoiceRepositoryImpl;

class InvoiceRepositoryImplTest {

    private InvoiceRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new InvoiceRepositoryImpl();
    }

    @Test
    void testSaveInvoice() {
        Invoice invoice = new Invoice("1", "Supplier A", 500.0, LocalDate.parse("2025-04-01"));
        boolean saved = repository.save(invoice);
        assertTrue(saved, "Invoice should be saved successfully.");
        assertEquals(1, repository.getAll().size(), "Repository should contain one invoice.");
    }

    @Test
    void testExistsById() {
        Invoice invoice = new Invoice("1", "Supplier A", 500.0, LocalDate.parse("2025-04-01"));
        repository.save(invoice);
        assertTrue(repository.existsById("1"), "Invoice with ID '1' should exist.");
        assertFalse(repository.existsById("2"), "Invoice with ID '2' should not exist.");
    }

    @Test
    void testGetAllInvoices() {
        Invoice invoice1 = new Invoice("1", "Supplier A", 500.0, LocalDate.parse("2025-04-01"));
        Invoice invoice2 = new Invoice("2", "Supplier B", 700.0, LocalDate.parse("2025-04-02"));
        repository.save(invoice1);
        repository.save(invoice2);

        ArrayList<Invoice> invoices = repository.getAll();
        assertEquals(2, invoices.size(), "Repository should contain two invoices.");
        assertEquals("1", invoices.get(0).getInvoiceId(), "First invoice ID should be '1'.");
        assertEquals("2", invoices.get(1).getInvoiceId(), "Second invoice ID should be '2'.");
    }

    @Test
    void testClearAllInvoices() {
        Invoice invoice = new Invoice("1", "Supplier A", 500.0, LocalDate.parse("2025-04-01"));
        repository.save(invoice);

        repository.clearAll();
        assertTrue(repository.getAll().isEmpty(), "Repository should be empty after clearAll.");
    }
}