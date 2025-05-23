package main.java.com.invoicemanager.cli;

import java.time.LocalDate;
import java.util.List;

import main.java.com.invoicemanager.model.Invoice;
import main.java.com.invoicemanager.service.InvoiceService;
import main.java.com.invoicemanager.service.ReportService;
import main.java.com.invoicemanager.util.InputHelper;
import main.java.com.invoicemanager.util.InvoiceCSVReader;

public class CLIHandlerFactory {

    public static final String MAIN_REPORT_FILE_PATH = "reports/invoice_report.md";

    public static Runnable addInvoiceHandler(InvoiceService service) {
        return () -> {
            System.out.print("ID: ");
            String id = InputHelper.promptString("Invoice ID");
            String supplier = InputHelper.promptString("Supplier Name");
            double amount = InputHelper.promptDouble("Amount");
            LocalDate date = InputHelper.promptDate("Invoice Date");
            if (service.addInvoice(new Invoice(id, supplier, amount, date))) {
                System.out.println("Invoice added.");
            } else {
                System.out.println("Invoice ID already exists. Skipped.");
            }
        };
    }

    public static Runnable addInvoiceFromCSVHandler(InvoiceService service) {
        return () -> {
            int skipped = 0, added = 0;
            String path = InputHelper.promptString("CSV file path: ");
            List<Invoice> imported = InvoiceCSVReader.readFromCsv(path);
            for (Invoice invoice : imported) {
                if (service.addInvoice(invoice)) {
                    added++;
                } else {
                    skipped++;
                }
            }
            System.out.println("Import complete. Added: " + added + ", Skipped (duplicates): " + skipped);
        };
    }

    public static Runnable listInvoicesHandler(InvoiceService service) {
        return () -> service.peekInvoiceData().forEach(System.out::println);
    }

    public static Runnable filterBySupplierHandler(InvoiceService service) {
        return () -> {
            String supplier = InputHelper.promptString("Supplier: ");
            service.findInvoicesBySupplier(supplier).forEach(System.out::println);
        };
    }

    public static Runnable filterByAmountHandler(InvoiceService service) {
        return () -> {
            double amount = InputHelper.promptDouble("Amount threshold: ");
            service.filterByAmount(amount).forEach(System.out::println);
        };
    }

    public static Runnable totalPerSupplierHandler(InvoiceService service) {
        return () -> service.totalAmountPerSupplier().forEach((k, v) -> System.out.println(k + ": " + v));
    }

    public static Runnable topThreeInvoicesHandler(InvoiceService service) {
        return () -> service.topThreeInvoices().forEach(System.out::println);
    }

    public static Runnable reportGeneratorHandler(ReportService reportService) {
        return () -> {
            String markdownInput = reportService.generateMarkdownReport();
            reportService.writeReportToFile(markdownInput, MAIN_REPORT_FILE_PATH);
        };
    }

    public static Runnable exitHandler() {
        return () -> {
            System.out.println("Exiting... :(");
            System.exit(0);
        };
    };
}
