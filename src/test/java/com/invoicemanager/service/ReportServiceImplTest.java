package test.java.com.invoicemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;

import main.java.com.invoicemanager.model.Invoice;
import main.java.com.invoicemanager.repository.impl.InvoiceRepositoryImpl;
import main.java.com.invoicemanager.service.ReportService;
import main.java.com.invoicemanager.service.impl.ReportServiceImpl;

class ReportServiceImplTest {

    private InvoiceRepositoryImpl repository;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        repository = InvoiceRepositoryImpl.getInstance();
        repository.clearAll();

        reportService = new ReportServiceImpl();
    }

    @Test
    void verify_generateMarkdownReport_works_correctly() {
        repository.save(new Invoice("1", "SupplierA", 1000.0, LocalDate.now()));
        repository.save(new Invoice("2", "SupplierB", 1500.0, LocalDate.now()));

        String markdown = reportService.generateMarkdownReport();

        assertNotNull(markdown);
        assertTrue(markdown.contains("Total invoices: 2"));
        assertTrue(markdown.contains("Total amount: ₹2500.00"));
        assertTrue(markdown.contains("SupplierA"));
        assertTrue(markdown.contains("SupplierB"));
    }

    @Test
    void verify_generateMarkdownReport_handles_exception_case() {
        // Break repository somehow
        repository.save(new Invoice("1", null, 100, null));

        String markdown = reportService.generateMarkdownReport();

        assertTrue(markdown.contains("Failed to generate report due to internal error."));
    }

    @Test
    void verify_writeReportToFile_is_performed_successfully() throws Exception {
        String content = "Test Report";
        String path = "test_report.md";

        reportService.writeReportToFile(content, path);

        File file = new File(path);
        assertTrue(file.exists());

        String writtenContent = Files.readString(file.toPath());
        assertEquals(content, writtenContent);

        // Cleanup
        file.delete();
    }

    /* Need to find a way to cover
    @Test
    void verify_writeReportToFile_Failure() {
        String content = "Test Report";
        String invalidPath = "/invalid_path/test_report.md";

        reportService.writeReportToFile(content, invalidPath);

        // No exception should crash the test, it should print message internally
    }
     */
}