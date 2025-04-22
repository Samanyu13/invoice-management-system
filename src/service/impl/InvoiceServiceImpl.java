package service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Invoice;
import repository.InvoiceRepository;
import service.InvoiceService;

public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repository;

    public InvoiceServiceImpl(InvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addInvoice(Invoice invoice) {
        // duplicate invoice check
        if (repository.existsById(invoice.getInvoiceId())) {
            return false;
        }
        return repository.save(invoice);
    }

    @Override
    public List<Invoice> filterBySupplier(String supplier) {
        return repository.getAll().stream()
                .filter(i -> i.getSupplierName().equalsIgnoreCase(supplier))
                .collect(Collectors.toList());
    }

    @Override
    public List<Invoice> filterByAmount(double threshold) {
        return repository.getAll().stream()
                .filter(i -> i.getAmount() >= threshold)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> totalAmountPerSupplier() {
        return repository.getAll().stream()
                .collect(Collectors.groupingBy(Invoice::getSupplierName,
                        Collectors.summingDouble((Invoice::getAmount))));
    }

    @Override
    public List<Invoice> top3Invoices() {
        return repository.getAll().stream()
                .sorted(Comparator.comparingDouble(Invoice::getAmount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return repository.getAll();
    }
}
