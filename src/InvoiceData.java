import java.time.LocalDate;

public class InvoiceData {
    private final String invoiceId;
    private final String supplierName;
    private final double amount;
    private final LocalDate invoiceDate;

    public InvoiceData(String id, String supplierName, double amount, LocalDate invoiceDate) {
        this.invoiceId = id;
        this.supplierName = supplierName;
        this.amount = amount;
        this.invoiceDate = invoiceDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    // Tests
    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + invoiceId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", amount=" + amount +
                ", invoiceDate=" + invoiceDate +
                '}';
    }
}