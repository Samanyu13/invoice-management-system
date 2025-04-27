package main.java.com.invoicemanager.service.impl;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.com.invoicemanager.exception.InvoiceServiceException;
import main.java.com.invoicemanager.model.Invoice;
import main.java.com.invoicemanager.repository.InvoiceRepository;
import main.java.com.invoicemanager.repository.impl.InvoiceRepositoryImpl;
import main.java.com.invoicemanager.service.InvoiceService;

public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger logger = Logger.getLogger(InvoiceServiceImpl.class.getName());
    private final InvoiceRepository repository;

    public InvoiceServiceImpl() {
        this.repository = InvoiceRepositoryImpl.getInstance();
    }

    @Override
    public boolean addInvoice(Invoice invoice) {
        try {
            if (repository.existsById(invoice.getInvoiceId())) {
                throw new InvoiceServiceException("Invoice ID already exists: " + invoice.getInvoiceId(), new Throwable("Duplicate ID"));
            }
            return repository.save(invoice);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while adding invoice: " + invoice.getInvoiceId(), e);
            throw new InvoiceServiceException("Failed to add invoice: " + invoice.getInvoiceId(), e);
        }
    }

    @Override
    public List<Invoice> findInvoicesBySupplier(String supplier) {
        try {
            return repository.findBySupplier(supplier);
        } catch (Exception e) {
            throw new InvoiceServiceException("Failed to filter by supplier: " + supplier, e);
        }
    }

    @Override
    public List<Invoice> filterByAmount(double threshold) {
        try {
            return repository.findByAmountGreaterThan(threshold);
        } catch (Exception e) {
            throw new InvoiceServiceException("Failed to filter by amount: " + threshold, e);
        }
    }

    @Override
    public Map<String, Double> totalAmountPerSupplier() {
        try {
            return repository.groupBySupplierTotalAmount();
        } catch (Exception e) {
            throw new InvoiceServiceException("Failed to calculate total amount per supplier", e);
        }
    }

    @Override
    public List<Invoice> topThreeInvoices() {
        try {
            return repository.findTopInvoices(3);
        } catch (Exception e) {
            throw new InvoiceServiceException("Failed to get top 3 invoices", e);
        }
    }

    @Override
    public List<Invoice> peekInvoiceData() {
        try {
            return repository.findTopInvoices(5);
        } catch (Exception e) {
            throw new InvoiceServiceException("Failed to peek invoices", e);
        }
    }
}
