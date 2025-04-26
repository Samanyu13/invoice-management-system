package main.java.com.invoicemanager.cli;

import java.util.Map;

public class MenuConstants {
    public static final int ADD_INVOICE = 1;
    public static final int ADD_INVOICE_CSV = 2;
    public static final int LIST_INVOICES = 3;
    public static final int FILTER_BY_SUPPLIER = 4;
    public static final int FILTER_BY_AMOUNT = 5;
    public static final int TOTAL_PER_SUPPLIER = 6;
    public static final int TOP_3_INVOICES = 7;
    public static final int REPORT_GENERATOR = 8;
    public static final int EXIT = 9;

    public static final Map<Integer, String> MENU_OPTIONS = Map.of(
            ADD_INVOICE, "Add Invoice",
            ADD_INVOICE_CSV, "Import from CSV",
            LIST_INVOICES, "List Invoices",
            FILTER_BY_SUPPLIER, "Filter by Supplier",
            FILTER_BY_AMOUNT, "Filter by Amount",
            TOTAL_PER_SUPPLIER, "Total per supplier",
            TOP_3_INVOICES, "Top 3 Invoices",
            REPORT_GENERATOR, "Report Generator",
            EXIT, "Exit"
    );
}
