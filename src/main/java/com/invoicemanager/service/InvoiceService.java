package main.java.com.invoicemanager.service;

import java.util.*;

import main.java.com.invoicemanager.model.Invoice;

public interface InvoiceService {

    boolean addInvoice(Invoice invoice);
    List<Invoice> filterBySupplier(String supplier);
    List<Invoice> filterByAmount(double threshold);
    Map<String, Double> totalAmountPerSupplier();
    List<Invoice> top3Invoices();
    List<Invoice> getAllInvoices();
}