package test.java.com.invoicemanager.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.invoicemanager.exception.InvoiceServiceException;
import main.java.com.invoicemanager.model.Invoice;
import main.java.com.invoicemanager.repository.impl.InvoiceRepositoryImpl;
import main.java.com.invoicemanager.service.impl.InvoiceServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

class InvoiceServiceImplTest {

    private InvoiceServiceImpl service;

    @BeforeEach
    void setUp() {
        InvoiceRepositoryImpl repository = InvoiceRepositoryImpl.getInstance();
        repository.clearAll();

        service = new InvoiceServiceImpl();
    }

    @Test
    void verify_successful_invoice_addition_reflects_in_memory() {
        Invoice invoice = new Invoice("1", "SupplierA", 1000, LocalDate.now());

        boolean result = service.addInvoice(invoice);

        assertTrue(result);

        List<Invoice> invoices = service.peekInvoiceData();
        assertFalse(invoices.isEmpty());
    }

    @Test
    void verify_duplicate_invoice_throws_exception() {
        Invoice invoice = new Invoice("1", "SupplierA", 1000, LocalDate.now());
        service.addInvoice(invoice);

        assertThrows(InvoiceServiceException.class, () -> service.addInvoice(invoice));
    }

    @Test
    void verify_findInvoicesBySupplier_returns_correct_data() {
        Invoice invoice = new Invoice("1", "SupplierA", 1000, LocalDate.now());
        service.addInvoice(invoice);

        List<Invoice> result = service.findInvoicesBySupplier("SupplierA");

        assertEquals(1, result.size());
        assertEquals("SupplierA", result.get(0).getSupplierName());
    }

    @Test
    void verify_filterByAmount_returns_correct_data() {
        Invoice invoice = new Invoice("1", "SupplierA", 1000, LocalDate.now());
        service.addInvoice(invoice);
        int threshold = 750;
        List<Invoice> result = service.filterByAmount(threshold);

        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getAmount() >= threshold);
    }

    @Test
    void verify_totalAmountPerSupplier_returns_correct_data() {
        Invoice invoice = new Invoice("1", "SupplierA", 1000, LocalDate.now());
        service.addInvoice(invoice);

        Map<String, Double> result = service.totalAmountPerSupplier();

        assertEquals(1, result.size());
        assertEquals(1000.0, result.get("SupplierA"));
    }

    @Test
    void verify_topThreeInvoices_returns_three_items_when_invoice_data_greater_than_two() {
        service.addInvoice(new Invoice("1", "SupplierA", 500, LocalDate.now()));
        service.addInvoice(new Invoice("2", "SupplierB", 1000, LocalDate.now()));
        service.addInvoice(new Invoice("3", "SupplierC", 1500, LocalDate.now()));
        service.addInvoice(new Invoice("4", "SupplierD", 2000, LocalDate.now()));

        List<Invoice> result = service.topThreeInvoices();

        assertEquals(3, result.size());
    }

    @Test
    void verify_peekInvoiceData_returns_correct_data() {
        service.addInvoice(new Invoice("1", "SupplierA", 500, LocalDate.now()));
        service.addInvoice(new Invoice("2", "SupplierB", 1000, LocalDate.now()));
        service.addInvoice(new Invoice("3", "SupplierC", 1500, LocalDate.now()));

        List<Invoice> result = service.peekInvoiceData();

        assertFalse(result.isEmpty());
    }

    @Test
    void verify_addInvoice_with_null_id_throws_exception() {
        assertThrows(InvoiceServiceException.class, () -> service.addInvoice(new Invoice(null, "SupplierA", 500, LocalDate.now())));
    }
}