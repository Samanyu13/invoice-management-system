package service;

import java.util.List;

import model.Invoice;

public interface ReportService {
    String generateMarkdownReport(List<Invoice> invoices);

    void writeReportToFile(String content, String filePath);
}
