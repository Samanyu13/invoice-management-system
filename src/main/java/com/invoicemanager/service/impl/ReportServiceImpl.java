package main.java.com.invoicemanager.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.com.invoicemanager.model.Invoice;
import main.java.com.invoicemanager.repository.InvoiceRepository;
import main.java.com.invoicemanager.repository.impl.InvoiceRepositoryImpl;
import main.java.com.invoicemanager.service.ReportService;

public class ReportServiceImpl implements ReportService {

    private final InvoiceRepository repository;

    public ReportServiceImpl() {
        this.repository = InvoiceRepositoryImpl.getInstance();
    }

    private static final Logger LOGGER = Logger.getLogger(ReportServiceImpl.class.getName());
    @Override
    public String generateMarkdownReport() {
        StringBuilder report = new StringBuilder();

        try {
            int invoiceSize = repository.getInvoiceCount();
            report.append("# Invoice Report\n\n")
                    .append("## Summary\n")
                    .append("- Total invoices: ").append(invoiceSize).append("\n");

            double total = repository.getInvoiceAmountsSum();
            report.append("- Total amount: ₹").append(String.format("%.2f", total)).append("\n");

            double avg = (invoiceSize == 0) ? 0 : total / invoiceSize;
            report.append("- Average amount: ₹").append(String.format("%.2f", avg)).append("\n\n");

            report.append("## Total Amount Per Supplier\n");
            Map<String, Double> totalPerSupplier = repository.groupBySupplierTotalAmount();

            for (var entry : totalPerSupplier.entrySet()) {
                report.append("- ").append(entry.getKey())
                        .append(": ₹").append(String.format("%.2f", entry.getValue())).append("\n");
            }

            report.append("\n## Top 3 Highest Invoices\n");
            repository.findTopInvoices(3).stream()
                    .sorted(Comparator.comparingDouble(Invoice::getAmount).reversed())
                    .limit(3)
                    .forEach(inv -> report.append("- **").append(inv.getInvoiceId()).append("** | ")
                            .append(inv.getSupplierName()).append(" | ₹")
                            .append(String.format("%.2f", inv.getAmount())).append(" | ")
                            .append(inv.getInvoiceDate()).append("\n"));

            report.append("\n\n*Generated on ").append(LocalDateTime.now()).append("*\n\n");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while generating the report", e);
            return "Failed to generate report due to internal error.";
        }

        return report.toString();
    }

    @Override
    public void writeReportToFile(String content, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
            System.out.println("Report generated to : " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to write report the report: " + e.getMessage());
        }
    }
}
