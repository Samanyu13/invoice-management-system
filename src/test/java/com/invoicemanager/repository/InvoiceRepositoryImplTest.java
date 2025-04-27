package test.java.com.invoicemanager.repository;

import main.java.com.invoicemanager.model.Invoice;
import main.java.com.invoicemanager.repository.impl.InvoiceRepositoryImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceRepositoryImplTest {

    private InvoiceRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = InvoiceRepositoryImpl.getInstance();
        repository.clearAll();
    }

    @Test
    void verify_saving_unique_invoice_returns_true() {
        Invoice invoice = new Invoice("1", "Supplier A", 100.0, LocalDate.parse("2025-04-01"));
        boolean result = repository.save(invoice);
        assertTrue(result, "Invoice should be saved successfully.");
    }

    @Test
    void verify_save_with_null_invoice_throws_exception() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> repository.save(null));
        assertEquals("Invoice or Invoice ID cannot be null.", exception.getMessage());
    }

    @Test
    void verify_save_with_existing_invoice_id_throws_exception() {
        Invoice invoice = new Invoice("1", "Supplier A", 100.0, LocalDate.parse("2025-04-01"));
        repository.save(invoice);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> repository.save(invoice));
        assertEquals("Invoice with ID already exists.", exception.getMessage());
    }

    @Test
    void verify_existsById_returns_true_for_existing_invoice_id() {
        Invoice invoice = new Invoice("1", "Supplier A", 100.0, LocalDate.parse("2025-04-01"));
        repository.save(invoice);
        assertTrue(repository.existsById("1"), "Invoice with ID '1' should exist.");
    }

    @Test
    void verify_existsById_throws_exception_for_null_invoice_id() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> repository.existsById(null));
        assertEquals("ID cannot be null.", exception.getMessage());
    }

    @Test
    void verify_findBySupplier_returns_correct_result_count() {
        Invoice invoice1 = new Invoice("1", "Supplier A", 100.0, LocalDate.parse("2025-04-01"));
        Invoice invoice2 = new Invoice("2", "Supplier A", 150.0, LocalDate.parse("2025-04-02"));
        Invoice invoice3 = new Invoice("3", "Supplier B", 200.0, LocalDate.parse("2025-04-03"));
        repository.save(invoice1);
        repository.save(invoice2);
        repository.save(invoice3);

        List<Invoice> result = repository.findBySupplier("Supplier A");
        assertEquals(2, result.size(), "Should return 2 invoices for 'Supplier A'.");
    }

    @Test
    void verify_findBySupplier_throws_exception_for_null_supplier() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> repository.findBySupplier(null));
        assertEquals("Supplier cannot be null.", exception.getMessage());
    }

    @Test
    void verify_FindByAmountGreaterThan_returns_correct_result_count() {
        Invoice invoice1 = new Invoice("1", "Supplier A", 100.0, LocalDate.parse("2025-04-01"));
        Invoice invoice2 = new Invoice("2", "Supplier A", 150.0, LocalDate.parse("2025-04-02"));
        Invoice invoice3 = new Invoice("3", "Supplier B", 200.0, LocalDate.parse("2025-04-03"));
        repository.save(invoice1);
        repository.save(invoice2);
        repository.save(invoice3);

        List<Invoice> result = repository.findByAmountGreaterThan(120.0);
        assertEquals(2, result.size(), "Should return 2 invoices with amount greater than 120.");
    }

    @Test
    void verify_FindByAmountGreaterThan_throws_exception_for_negative_threshold() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> repository.findByAmountGreaterThan(-10.0));
        assertEquals("Threshold amount cannot be negative.", exception.getMessage());
    }

    @Test
    void verify_groupBySupplierTotalAmount_returns_correct_sum() {
        Invoice invoice1 = new Invoice("1", "Supplier A", 100.0, LocalDate.parse("2025-04-01"));
        Invoice invoice2 = new Invoice("2", "Supplier A", 150.0, LocalDate.parse("2025-04-02"));
        Invoice invoice3 = new Invoice("3", "Supplier B", 200.0, LocalDate.parse("2025-04-03"));
        repository.save(invoice1);
        repository.save(invoice2);
        repository.save(invoice3);

        Map<String, Double> result = repository.groupBySupplierTotalAmount();
        assertEquals(2, result.size(), "Should return total amount for 2 suppliers.");
        assertEquals(250.0, result.get("Supplier A"), "Total amount for Supplier A should be 250.");
    }

    @Test
    void verify_groupBySupplierTotalAmount_returns_returns_empty_map_when_no_invoices_present() {
        Map<String, Double> result = repository.groupBySupplierTotalAmount();
        assertTrue(result.isEmpty(), "The result should be an empty map when no invoices are present.");
    }

    @Test
    void verify_findTopInvoices_return_top_invoices() {
        Invoice invoice1 = new Invoice("1", "Supplier A", 100.0, LocalDate.parse("2025-04-01"));
        Invoice invoice2 = new Invoice("2", "Supplier A", 150.0, LocalDate.parse("2025-04-02"));
        Invoice invoice3 = new Invoice("3", "Supplier B", 200.0, LocalDate.parse("2025-04-03"));
        repository.save(invoice1);
        repository.save(invoice2);
        repository.save(invoice3);

        List<Invoice> result = repository.findTopInvoices(2);
        assertEquals(2, result.size(), "Should return the top 2 invoices.");
        assertEquals("3", result.get(0).getInvoiceId(), "Invoice ID for the highest amount should be 3.");
    }

    @Test
    void verify_findTopInvoices_returns_empty_list_for_invalid_counts() {
        List<Invoice> result = repository.findTopInvoices(0);
        assertTrue(result.isEmpty(), "The result should be empty when count is 0.");

        result = repository.findTopInvoices(-1);
        assertTrue(result.isEmpty(), "The result should be empty when count is negative.");
    }

    @Test
    void verify_getInvoiceCount_when_invoices_exist() {
        repository.save(new Invoice("1", "SupplierA", 1000, LocalDate.now()));
        repository.save(new Invoice("2", "SupplierB", 1500, LocalDate.now()));

        assertEquals(2, repository.getInvoiceCount());
    }

    @Test
    void verify_getInvoiceCount_when_no_invoices_exist() {
        assertEquals(0, repository.getInvoiceCount());
    }

    @Test
    void verify_getInvoiceAmountsSum_works_correctly() {
        repository.save(new Invoice("1", "SupplierA", 1000, LocalDate.now()));
        repository.save(new Invoice("2", "SupplierB", 1500, LocalDate.now()));

        assertEquals(2500.0, repository.getInvoiceAmountsSum());
    }

    /* Trying to remove getAll usages
    @Test
    void verify_clearAll() {
        Invoice invoice1 = new Invoice("1", "Supplier A", 100.0, LocalDate.parse("2025-04-01"));
        repository.save(invoice1);
        repository.clearAll();
        assertEquals(0, repository.getAll().size(), "Repository should be empty after clearing.");
    }
     */
}