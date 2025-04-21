import static util.Constants.ADD_INVOICE;
import static util.Constants.ADD_INVOICE_CSV;
import static util.Constants.EXIT;
import static util.Constants.FILTER_BY_AMOUNT;
import static util.Constants.FILTER_BY_SUPPLIER;
import static util.Constants.LIST_INVOICES;
import static util.Constants.REPORT_GENERATOR;
import static util.Constants.TOP_3_INVOICES;
import static util.Constants.TOTAL_PER_SUPPLIER;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

import model.Invoice;
import repository.InvoiceRepository;
import repository.InvoiceRepositoryImpl;
import service.InvoiceService;
import service.InvoiceServiceImpl;
import util.InvoiceCSVReader;
import util.InputHelper;
import util.ReportGenerator;

public class InvoiceApp {
    public static void main(String[] args) {

        InvoiceRepository repository = new InvoiceRepositoryImpl();
        InvoiceService service = new InvoiceServiceImpl(repository);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            cliPrompt();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case ADD_INVOICE -> {
                    System.out.print("ID: ");
                    String id = InputHelper.promptString("model.Invoice ID");
                    String supplier = InputHelper.promptString("Supplier Name");
                    double amount = InputHelper.promptDouble("Amount");
                    LocalDate date = InputHelper.promptDate("model.Invoice Date");
                    if (service.addInvoice(new Invoice(id, supplier, amount, date))) {
                        System.out.println("model.Invoice added.");
                    } else {
                        System.out.println("model.Invoice ID already exists. Skipped.");
                    }
                }
                case ADD_INVOICE_CSV -> {
                    int skipped = 0, added = 0;
                    System.out.print("CSV file path: ");
                    String path = scanner.nextLine();
                    List<Invoice> imported = InvoiceCSVReader.readFromCsv(path);
                    for (Invoice invoice : imported) {
                        if (service.addInvoice(invoice)) {
                            added++;
                        } else {
                            skipped++;
                        }
                    }
                    System.out.println("Import complete. Added: " + added + ", Skipped (duplicates): " + skipped);
                }
                case LIST_INVOICES -> service.getAllInvoices().forEach(System.out::println);
                case FILTER_BY_SUPPLIER -> {
                    System.out.print("Supplier: ");
                    String sup = scanner.nextLine();
                    service.filterBySupplier(sup).forEach(System.out::println);
                }
                case FILTER_BY_AMOUNT -> {
                    System.out.print("Amount Threshold: ");
                    double amt = scanner.nextDouble();
                    service.filterByAmount(amt).forEach(System.out::println);
                }
                case TOTAL_PER_SUPPLIER ->
                        service.totalAmountPerSupplier().forEach((k, v) -> System.out.println(k + ": " + v));
                case TOP_3_INVOICES -> service.top3Invoices().forEach(System.out::println);
                case REPORT_GENERATOR ->
                        ReportGenerator.generateReport(service.getAllInvoices(), "reports/invoice_report.md");
                case EXIT -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    private static void cliPrompt() {
        System.out.println("\n1. Add Invoice");
        System.out.println("2. Import from CSV");
        System.out.println("3. List Invoices");
        System.out.println("4. Filter by Supplier");
        System.out.println("5. Filter by Amount");
        System.out.println("6. Report - Total per Supplier");
        System.out.println("7. Report - Top 3 Invoices");
        System.out.println("8. Report Generator");
        System.out.println("9. Exit");
        System.out.print("Enter your choice :: ");
    }
}