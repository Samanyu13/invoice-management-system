import java.time.LocalDate;

public class InvoiceData {
    private String id;
    private String supplierName;
    private double amount;
    private LocalDate invoiceDate;

    public InvoiceData(String id, String supplierName, double amount, LocalDate invoiceDate) {
        this.id = id;
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

    // Tests
    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", amount=" + amount +
                ", invoiceDate=" + invoiceDate +
                '}';
    }
}