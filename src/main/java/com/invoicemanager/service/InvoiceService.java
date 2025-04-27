package main.java.com.invoicemanager.service;

import java.util.*;

import main.java.com.invoicemanager.model.Invoice;


/**
 * Defines the management of invoice-related operations.
 * Provides methods for adding invoices, retrieving invoices by supplier,
 * filtering invoices by amount and similar (mostly for direct CLI related operations)
 */
public interface InvoiceService {

    /**
     * Adds a new invoice to the system.
     *
     * @param invoice the invoice to be added
     * @return true if the invoice is added successfully, false otherwise
     */
    boolean addInvoice(Invoice invoice);

    /**
     * Finds and retrieves invoices based on the supplier name.
     *
     * @param supplier the name of the supplier to filter invoices by
     * @return a list of invoices associated with the specified supplier
     */
    List<Invoice> findInvoicesBySupplier(String supplier);

    /**
     * Filters invoices by a given amount threshold.
     *
     * @param threshold the amount threshold to filter invoices by
     * @return a list of invoices where the amount is greater than or equal to the threshold
     */
    List<Invoice> filterByAmount(double threshold);

    /**
     * Calculates the total amount of invoices grouped by supplier.
     *
     * @return a map where the key is the supplier's name and the value is the total amount for that supplier
     */
    Map<String, Double> totalAmountPerSupplier();

    /**
     * Retrieves the top three invoices, ordered by amount in descending order.
     *
     * @return a list of the top three invoices with the highest amounts
     */
    List<Invoice> topThreeInvoices();

    /**
     * Retrieves a limited set of invoices (5, as set now).
     *
     * @return a list of invoices, ordered by amount in descending order
     */
    List<Invoice> peekInvoiceData();
}