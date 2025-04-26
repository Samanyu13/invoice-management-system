package main.java.com.invoicemanager.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.java.com.invoicemanager.model.Invoice;

public class InvoiceRepositoryImpl implements InvoiceRepository {

    // An upgrade of the data-store from two data items, to facilitate duplicate checking as well
    private final Map<String, Invoice> invoiceMap = new HashMap<>();
    @Override
    public boolean save(Invoice invoice) {
        invoiceMap.put(invoice.getInvoiceId(), invoice);
        return true;
    }

    @Override
    public ArrayList<Invoice> getAll() {
        return new ArrayList<>(invoiceMap.values());
    }
    
    @Override
    public boolean existsById(String id) {
        return invoiceMap.containsKey(id);
    }
    
    @Override
    public void clearAll() {
        invoiceMap.clear();
    }
}
