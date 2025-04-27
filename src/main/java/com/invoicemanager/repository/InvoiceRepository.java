package main.java.com.invoicemanager.repository;

import java.util.List;
import java.util.Map;

import main.java.com.invoicemanager.model.Invoice;

/**
 * Our interface for data repository.
 * Provides methods for saving invoices, querying invoices by different criteria,
 * and retrieving statistics about the stored invoices.
 */
public interface InvoiceRepository {

    /**
     * Saves an invoice to the repository.
     *
     * @param invoice the invoice to be saved
     * @return true if the invoice was successfully saved, false otherwise
     */
    boolean save(Invoice invoice);

    /**
     * Checks if an invoice with the given ID already exists in the repository.
     *
     * @param id the ID of the invoice
     * @return true if an invoice with the specified ID exists, false otherwise
     */
    boolean existsById(String id);

    /* ArrayList<Invoice> getAll(); */

    /**
     * Clears all invoices from the repository.
     */
    void clearAll();

    /**
     * Finds all invoices that belong to the specified supplier.
     *
     * @param supplier the name of the supplier
     * @return a list of invoices associated with the given supplier
     */
    List<Invoice> findBySupplier(String supplier);

    /**
     * Finds all invoices where the amount is greater than the specified threshold.
     *
     * @param threshold the minimum amount
     * @return a list of invoices with amounts greater than the specified threshold
     */
    List<Invoice> findByAmountGreaterThan(double threshold);

    /**
     * Groups invoices by supplier and calculates the total amount for each supplier.
     *
     * @return a map where the keys are supplier names and the values are the total amounts for each supplier
     */
    Map<String, Double> groupBySupplierTotalAmount();

    /**
     * Retrieves the top 'count' number of invoices based on the highest amounts.
     *
     * @param count the number of top invoices to retrieve
     * @return a list of the top 'count' invoices ordered by amount
     */
    List<Invoice> findTopInvoices(int count);

    /**
     * Gets the total number of invoices in the repository.
     *
     * @return the total number of invoices
     */
    int getInvoiceCount();

    /**
     * Calculates the sum of the amounts of all invoices in the repository.
     *
     * @return the sum of the invoice amounts
     */
    double getInvoiceAmountsSum();
}