import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceCSVReader {
    public static List<InvoiceData> readFromCsv(String filePath) {
        List<InvoiceData> invoices = new ArrayList<>();
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

                invoices.add(new InvoiceData(id, supplier, amount, date));
            }
        } catch (Exception e) {
            System.out.println("Error reading CSV: " + e.getMessage() + ", skipping the row :/");
        }
        return invoices;
    }
}