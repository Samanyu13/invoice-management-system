import java.util.*;
import java.util.stream.Collectors;

public class InvoiceServiceManager {
    private List<InvoiceData> invoices = new ArrayList<>();

    public void addInvoice(InvoiceData invoice) {
        invoices.add(invoice);
    }

    public List<InvoiceData> listInvoices() {
        return invoices;
    }

    public List<InvoiceData> filterBySupplier(String supplier) {
        return invoices.stream()
                .filter(i -> i.getSupplierName().equalsIgnoreCase(supplier))
                .collect(Collectors.toList());
    }

    public List<InvoiceData> filterByAmount(double threshold) {
        return invoices.stream()
                .filter(i -> i.getAmount() >= threshold)
                .collect(Collectors.toList());
    }

    public Map<String, Double> totalAmountPerSupplier() {
        return invoices.stream()
                .collect(Collectors.groupingBy(InvoiceData::getSupplierName,
                        Collectors.summingDouble(InvoiceData::getAmount)));
    }

    public List<InvoiceData> top3Invoices() {
        return invoices.stream()
                .sorted(Comparator.comparingDouble(InvoiceData::getAmount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
}