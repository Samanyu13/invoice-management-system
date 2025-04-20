import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {

    public static void generateReport(List<InvoiceData> invoices, String filePath) {
        StringBuilder report = new StringBuilder();

        report.append("# Invoice Report\n\n");
        report.append("## Summary\n");
        report.append("- Total invoices: ").append(invoices.size()).append("\n");
        double total = invoices.stream().mapToDouble(InvoiceData::getAmount).sum();
        report.append("- Total amount: ₹").append(String.format("%.2f", total)).append("\n");
        double avg = invoices.isEmpty() ? 0 : total / invoices.size();
        report.append("- Average amount: ₹").append(String.format("%.2f", avg)).append("\n\n");

        report.append("## Total Amount Per Supplier\n");
        Map<String, Double> totalPerSupplier = invoices.stream()
                .collect(Collectors.groupingBy(InvoiceData::getSupplierName,
                        Collectors.summingDouble(InvoiceData::getAmount)));

        for (var entry : totalPerSupplier.entrySet()) {
            report.append("- ").append(entry.getKey())
                    .append(": ₹").append(String.format("%.2f", entry.getValue())).append("\n");
        }

        report.append("\n## Top 3 Highest Invoices\n");
        invoices.stream()
                .sorted(Comparator.comparingDouble(InvoiceData::getAmount).reversed())
                .limit(3)
                .forEach(inv -> {
                    report.append("- **").append(inv.getInvoiceId()).append("** | ")
                            .append(inv.getSupplierName()).append(" | ₹")
                            .append(String.format("%.2f", inv.getAmount())).append(" | ")
                            .append(inv.getInvoiceDate()).append("\n");
                });

        report.append("\n\n*Generated on ").append(LocalDateTime.now()).append("*\n\n");


        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(report.toString());
            System.out.println("Report generated to : " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to write report the report: " + e.getMessage());
        }
    }
}
