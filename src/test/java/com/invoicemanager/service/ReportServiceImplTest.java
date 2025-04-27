package test.java.com.invoicemanager.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import main.java.com.invoicemanager.model.Invoice;
import main.java.com.invoicemanager.service.impl.ReportServiceImpl;

class ReportServiceImplTest {

    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void testGenerateMarkdownReport() {
        // Create sample invoices
        List<Invoice> invoices = List.of(
                new Invoice("1", "Supplier A", 500.0, LocalDate.parse("2025-04-01")),
                new Invoice("2", "Supplier B", 200.0, LocalDate.parse("2025-04-02"))
        );

        // Generate markdown report
        String report = reportService.generateMarkdownReport(invoices);

        // Verify that the report contains expected values
        assertNotNull(report);
        assertTrue(report.contains("# Invoice Report"));
        assertTrue(report.contains("1"));
        assertTrue(report.contains("Supplier A"));
        assertTrue(report.contains("500.0"));
    }

    @Test
    void testWriteReportToFile() {
        // Create sample invoices with LocalDate instead of String for date
        List<Invoice> invoices = List.of(
                new Invoice("1", "Supplier A", 500.0, LocalDate.parse("2025-04-01")),
                new Invoice("2", "Supplier B", 200.0, LocalDate.parse("2025-04-02"))
        );

        // Generate markdown report content
        String reportContent = reportService.generateMarkdownReport(invoices);

        // Define the file path for the report
        String filePath = "test-report.md";

        // Write report to the file
        reportService.writeReportToFile(reportContent, filePath);

        // Verify that the file exists
        File reportFile = new File(filePath);
        assertTrue(reportFile.exists(), "The report file should exist.");

        // Clean up: Delete the report file after the test
        boolean isDeleted = reportFile.delete();
        assertTrue(isDeleted, "The report file should be deleted after the test.");
    }
}