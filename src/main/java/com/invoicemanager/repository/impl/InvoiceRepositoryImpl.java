package main.java.com.invoicemanager.repository.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import main.java.com.invoicemanager.model.Invoice;
import main.java.com.invoicemanager.repository.InvoiceRepository;

public class InvoiceRepositoryImpl implements InvoiceRepository {
    private static final Logger logger = Logger.getLogger(InvoiceRepositoryImpl.class.getName());
    // An upgrade of the data-store from two data items, to facilitate duplicate checking as well
    private final Map<String, Invoice> invoiceMap = new HashMap<>();
    private static InvoiceRepositoryImpl instance;

    public static InvoiceRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new InvoiceRepositoryImpl();
        }
        return instance;
    }

    private InvoiceRepositoryImpl() {
        // making private constructor to prevent constructor usage
    }

    @Override
    public boolean save(Invoice invoice) {
        if (invoice == null || invoice.getInvoiceId() == null) {
            throw new IllegalArgumentException("Invoice or Invoice ID cannot be null.");
        }
        if (invoiceMap.containsKey(invoice.getInvoiceId())) {
            throw new IllegalStateException("Invoice with ID already exists.");
        }
        invoiceMap.put(invoice.getInvoiceId(), invoice);
        logger.info("Saved invoice with ID: " + invoice.getInvoiceId());
        return true;
    }

    /*
    @Override
    public ArrayList<Invoice> getAll() {
        // Return an empty list if no data is present
        if (invoiceMap.isEmpty()) {
            logger.warning("Invoice repository is empty. Returning an empty list.");
            return new ArrayList<>();
        }
        return new ArrayList<>(invoiceMap.values());
    } */

    @Override
    public boolean existsById(String id) {
        if (id == null) {
            logger.warning("Attempted to check existence with a null ID.");
            throw new IllegalArgumentException("ID cannot be null.");
        }

        return invoiceMap.containsKey(id);
    }

    @Override
    public void clearAll() {
        invoiceMap.clear();
        logger.info("Cleared all invoices from repository.");
    }

    @Override
    public List<Invoice> findBySupplier(String supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier cannot be null.");
        }
        return invoiceMap.values().stream()
                .filter(i -> supplier.equalsIgnoreCase(i.getSupplierName()))
                .toList();
    }

    @Override
    public List<Invoice> findByAmountGreaterThan(double threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold amount cannot be negative.");
        }
        return invoiceMap.values().stream()
                .filter(i -> i.getAmount() >= threshold)
                .toList();
    }

    @Override
    public Map<String, Double> groupBySupplierTotalAmount() {
        try {
            if (invoiceMap.isEmpty()) {
                logger.warning("Invoice map is empty or null, returning an empty map.");
                return Collections.emptyMap();
            }

            return invoiceMap.values().stream()
                    .collect(Collectors.groupingBy(Invoice::getSupplierName,
                            Collectors.summingDouble(Invoice::getAmount)));
        } catch (Exception e) {
            logger.severe("An error occurred while grouping invoices by supplier total amount: " + e.getMessage());
            throw new RuntimeException("Error grouping invoices by supplier total amount", e);
        }
    }

    @Override
    public List<Invoice> findTopInvoices(int count) {
        try {
            // Return empty list for invalid count
            if (count <= 0) {
                logger.warning("The requested count is non-positive: " + count + ". Returning empty list.");
                return Collections.emptyList();
            }

            return invoiceMap.values().stream()
                    .sorted(Comparator.comparingDouble(Invoice::getAmount).reversed())
                    .limit(count)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.severe("An error occurred while finding the top invoices: " + e.getMessage());
            throw new RuntimeException("Error finding top invoices", e);
        }
    }

    @Override
    public int getInvoiceCount() {
        return invoiceMap.size();
    }

    @Override
    public double getInvoiceAmountsSum() {
        return invoiceMap.values().stream()
                .mapToDouble(Invoice::getAmount).sum();
    }
}
