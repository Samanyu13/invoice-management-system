import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

public class InvoiceApp {
    public static void main(String[] args) {
        InvoiceServiceManager service = new InvoiceServiceManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            cliPrompt();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.print("ID: ");
                    String id = InvoiceInputHelper.promptString("Invoice ID");
                    String supplier = InvoiceInputHelper.promptString("Supplier Name");
                    double amount = InvoiceInputHelper.promptDouble("Amount");
                    LocalDate date = InvoiceInputHelper.promptDate("Invoice Date");
                    if (service.addInvoice(new InvoiceData(id, supplier, amount, date))) {
                        System.out.println("Invoice added.");
                    } else {
                        System.out.println("Invoice ID already exists. Skipped.");
                    }
                }
                case 2 -> {
                    int skipped = 0, added = 0;
                    System.out.print("CSV file path: ");
                    String path = scanner.nextLine();
                    List<InvoiceData> imported = InvoiceCSVReader.readFromCsv(path);
                    for (InvoiceData invoice : imported) {
                        if (service.addInvoice(invoice)) {
                            added++;
                        } else {
                            skipped++;
                        }
                    }
                    System.out.println("Import complete. Added: " + added + ", Skipped (duplicates): " + skipped);                }
                case 3 -> service.listInvoices().forEach(System.out::println);
                case 4 -> {
                    System.out.print("Supplier: ");
                    String sup = scanner.nextLine();
                    service.filterBySupplier(sup).forEach(System.out::println);
                }
                case 5 -> {
                    System.out.print("Amount Threshold: ");
                    double amt = scanner.nextDouble();
                    service.filterByAmount(amt).forEach(System.out::println);
                }
                case 6 -> service.totalAmountPerSupplier().forEach((k, v) -> System.out.println(k + ": " + v));
                case 7 -> service.top3Invoices().forEach(System.out::println);
                case 8 -> ReportGenerator.generateReport(service.getAllInvoices(), "reports/invoice_report.md");
                case 9 -> {
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