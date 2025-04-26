package main.java.com.invoicemanager.service;

import java.util.List;

import main.java.com.invoicemanager.model.Invoice;

public interface ReportService {
    String generateMarkdownReport(List<Invoice> invoices);

    void writeReportToFile(String content, String filePath);
}
