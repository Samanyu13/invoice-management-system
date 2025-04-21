package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Invoice;

public class InvoiceCSVReader {
    public static List<Invoice> readFromCsv(String filePath) {
        List<Invoice> invoices = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                // skip csv header
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }
                String[] tokens = line.split(",");
                if (tokens.length != 4) continue;

                String id = tokens[0].trim();
                String supplier = tokens[1].trim();
                double amount = Double.parseDouble(tokens[2].trim());
                LocalDate date = LocalDate.parse(tokens[3].trim());

                if (id.isEmpty() || supplier.isEmpty()) {
                    System.out.println("Missing required fields, skipping invoice :/");
                    continue;
                }

                invoices.add(new Invoice(id, supplier, amount, date));
            }
        } catch (Exception e) {
            System.out.println("Error reading CSV: " + e.getMessage() + ", skipping invoice :/");
        }
        return invoices;
    }
}