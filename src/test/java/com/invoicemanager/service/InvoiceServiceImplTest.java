package test.java.com.invoicemanager.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import main.java.com.invoicemanager.model.Invoice;
import main.java.com.invoicemanager.service.impl.InvoiceServiceImpl;

import java.util.List;
import java.util.Map;

class InvoiceServiceImplTest {

    private InvoiceServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new InvoiceServiceImpl();
    }

    @Test
    void testAddInvoice() {
        Invoice invoice = new Invoice("1", "Supplier A", 500.0, LocalDate.parse("2025-04-01"));
        assertTrue(service.addInvoice(invoice), "Invoice should be added successfully.");

        // Adding same ID again should return false
        assertFalse(service.addInvoice(invoice), "Duplicate invoice should not be added.");
    }

    @Test
    void testFilterBySupplier() {
        Invoice invoice1 = new Invoice("1", "Supplier A", 500.0, LocalDate.parse("2025-04-01"));
        Invoice invoice2 = new Invoice("2", "Supplier B", 700.0, LocalDate.parse("2025-04-02"));
        service.addInvoice(invoice1);
        service.addInvoice(invoice2);

        List<Invoice> supplierAInvoices = service.filterBySupplier("Supplier A");
        assertEquals(1, supplierAInvoices.size(), "Should return 1 invoice for Supplier A.");
        assertEquals("Supplier A", supplierAInvoices.get(0).getSupplierName(), "Supplier name should match.");
    }

    @Test
    void testFilterByAmount() {
        Invoice invoice1 = new Invoice("1", "Supplier A", 500.0, LocalDate.parse("2025-04-01"));
        Invoice invoice2 = new Invoice("2", "Supplier B", 1500.0, LocalDate.parse("2025-04-02"));
        service.addInvoice(invoice1);
        service.addInvoice(invoice2);

        List<Invoice> filteredInvoices = service.filterByAmount(1000.0);
        assertEquals(1, filteredInvoices.size(), "Should return 1 invoice above 1000.0 amount.");
        assertTrue(filteredInvoices.get(0).getAmount() > 1000.0, "Invoice amount should be above threshold.");
    }

    @Test
    void testTotalAmountPerSupplier() {
        service.addInvoice(new Invoice("1", "Supplier A", 500.0, LocalDate.parse("2025-04-01")));
        service.addInvoice(new Invoice("2", "Supplier A", 700.0, LocalDate.parse("2025-04-02")));
        service.addInvoice(new Invoice("3", "Supplier B", 800.0, LocalDate.parse("2025-04-03")));

        Map<String, Double> totals = service.totalAmountPerSupplier();
        assertEquals(2, totals.size(), "Should have two suppliers.");
        assertEquals(1200.0, totals.get("Supplier A"), 0.001, "Supplier A total amount mismatch.");
        assertEquals(800.0, totals.get("Supplier B"), 0.001, "Supplier B total amount mismatch.");
    }

    @Test
    void testTopThreeInvoices() {
        service.addInvoice(new Invoice("1", "Supplier A", 500.0, LocalDate.parse("2025-04-01")));
        service.addInvoice(new Invoice("2", "Supplier A", 1200.0, LocalDate.parse("2025-04-02")));
        service.addInvoice(new Invoice("3", "Supplier B", 1500.0, LocalDate.parse("2025-04-03")));
        service.addInvoice(new Invoice("4", "Supplier C", 100.0, LocalDate.parse("2025-04-04")));
        service.addInvoice(new Invoice("5", "Supplier D", 900.0, LocalDate.parse("2025-04-05")));

        List<Invoice> top3 = service.topThreeInvoices();
        assertEquals(3, top3.size(), "Should return top 3 invoices.");
        assertEquals(1500.0, top3.get(0).getAmount(), 0.001, "Top invoice amount mismatch.");
    }

    @Test
    void testGetAllInvoices() {
        service.addInvoice(new Invoice("1", "Supplier A", 500.0, LocalDate.parse("2025-04-01")));
        service.addInvoice(new Invoice("2", "Supplier B", 700.0, LocalDate.parse("2025-04-02")));

        List<Invoice> allInvoices = service.getAllInvoices();
        assertEquals(2, allInvoices.size(), "Should return all added invoices.");
    }
}