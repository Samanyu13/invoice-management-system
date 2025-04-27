package main.java.com.invoicemanager;

import static main.java.com.invoicemanager.cli.CLIHandlerFactory.addInvoiceFromCSVHandler;
import static main.java.com.invoicemanager.cli.CLIHandlerFactory.addInvoiceHandler;
import static main.java.com.invoicemanager.cli.CLIHandlerFactory.exitHandler;
import static main.java.com.invoicemanager.cli.CLIHandlerFactory.filterByAmountHandler;
import static main.java.com.invoicemanager.cli.CLIHandlerFactory.filterBySupplierHandler;
import static main.java.com.invoicemanager.cli.CLIHandlerFactory.listInvoicesHandler;
import static main.java.com.invoicemanager.cli.CLIHandlerFactory.reportGeneratorHandler;
import static main.java.com.invoicemanager.cli.CLIHandlerFactory.topThreeInvoicesHandler;
import static main.java.com.invoicemanager.cli.CLIHandlerFactory.totalPerSupplierHandler;
import static main.java.com.invoicemanager.cli.MenuConstants.ADD_INVOICE;
import static main.java.com.invoicemanager.cli.MenuConstants.ADD_INVOICE_CSV;
import static main.java.com.invoicemanager.cli.MenuConstants.EXIT;
import static main.java.com.invoicemanager.cli.MenuConstants.FILTER_BY_AMOUNT;
import static main.java.com.invoicemanager.cli.MenuConstants.FILTER_BY_SUPPLIER;
import static main.java.com.invoicemanager.cli.MenuConstants.LIST_INVOICES;
import static main.java.com.invoicemanager.cli.MenuConstants.MENU_OPTIONS;
import static main.java.com.invoicemanager.cli.MenuConstants.REPORT_GENERATOR;
import static main.java.com.invoicemanager.cli.MenuConstants.TOP_3_INVOICES;
import static main.java.com.invoicemanager.cli.MenuConstants.TOTAL_PER_SUPPLIER;

import main.java.com.invoicemanager.cli.CLI;
import main.java.com.invoicemanager.service.InvoiceService;
import main.java.com.invoicemanager.service.impl.InvoiceServiceImpl;
import main.java.com.invoicemanager.service.ReportService;
import main.java.com.invoicemanager.service.impl.ReportServiceImpl;

public class InvoiceApp {
    public static void main(String[] args) {

        InvoiceService service = new InvoiceServiceImpl();
        ReportService reportService = new ReportServiceImpl();

        CLI cli = new CLI();
        registerCLIOptions(cli, service, reportService);
        cli.run();
    }

    private static void registerCLIOptions(CLI cli, InvoiceService service, ReportService reportService) {
        cli.addCLIOption(ADD_INVOICE, MENU_OPTIONS.get(ADD_INVOICE), addInvoiceHandler(service));
        cli.addCLIOption(ADD_INVOICE_CSV, MENU_OPTIONS.get(ADD_INVOICE_CSV), addInvoiceFromCSVHandler(service));
        cli.addCLIOption(LIST_INVOICES, MENU_OPTIONS.get(LIST_INVOICES), listInvoicesHandler(service));
        cli.addCLIOption(FILTER_BY_SUPPLIER, MENU_OPTIONS.get(FILTER_BY_SUPPLIER), filterBySupplierHandler(service));
        cli.addCLIOption(FILTER_BY_AMOUNT, MENU_OPTIONS.get(FILTER_BY_AMOUNT), filterByAmountHandler(service));
        cli.addCLIOption(TOTAL_PER_SUPPLIER, MENU_OPTIONS.get(TOTAL_PER_SUPPLIER), totalPerSupplierHandler(service));
        cli.addCLIOption(TOP_3_INVOICES, MENU_OPTIONS.get(TOP_3_INVOICES), topThreeInvoicesHandler(service));
        cli.addCLIOption(REPORT_GENERATOR, MENU_OPTIONS.get(REPORT_GENERATOR), reportGeneratorHandler(reportService));
        cli.addCLIOption(EXIT, MENU_OPTIONS.get(EXIT), exitHandler());
    }
}