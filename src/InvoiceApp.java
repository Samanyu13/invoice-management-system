import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

public class InvoiceApp {
    public static void main(String[] args) {
        InvoiceServiceManager service = new InvoiceServiceManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            prompt();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Supplier: ");
                    String supplier = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Date (yyyy-mm-dd): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());
                    service.addInvoice(new InvoiceData(id, supplier, amount, date));
                }
                case 2 -> {
                    System.out.print("CSV file path: ");
                    String path = scanner.nextLine();
                    List<InvoiceData> imported = InvoiceCSVReader.readFromCsv(path);
                    imported.forEach(service::addInvoice);
                    System.out.println(imported.size() + " invoices imported.");
                }
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
                case 8 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    private static void prompt() {
        System.out.println("\n1. Add Invoice");
        System.out.println("2. Import from CSV");
        System.out.println("3. List Invoices");
        System.out.println("4. Filter by Supplier");
        System.out.println("5. Filter by Amount");
        System.out.println("6. Report - Total per Supplier");
        System.out.println("7. Report - Top 3 Invoices");
        System.out.println("8. Exit");
        System.out.print("Enter your choice :: ");
    }
}