import java.util.*;
import java.util.stream.Collectors;

public class InvoiceServiceManager {
    private final List<InvoiceData> invoices = new ArrayList<>();
    private final Set<String> invoiceIds = new HashSet<>();

    public boolean addInvoice(InvoiceData invoice) {
        // prevent duplicate entries
        if (invoiceIds.contains(invoice.getInvoiceId())) {
            return false;
        }
        invoices.add(invoice);
        invoiceIds.add(invoice.getInvoiceId());
        return true;
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

    public List<InvoiceData> getAllInvoices() {
        return invoices;
    }
}