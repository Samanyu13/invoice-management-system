import static cli.CLIHandlerFactory.addInvoiceFromCSVHandler;
import static cli.CLIHandlerFactory.addInvoiceHandler;
import static cli.CLIHandlerFactory.exitHandler;
import static cli.CLIHandlerFactory.filterByAmountHandler;
import static cli.CLIHandlerFactory.filterBySupplierHandler;
import static cli.CLIHandlerFactory.listInvoicesHandler;
import static cli.CLIHandlerFactory.reportGeneratorHandler;
import static cli.CLIHandlerFactory.topThreeInvoicesHandler;
import static cli.CLIHandlerFactory.totalPerSupplierHandler;
import static cli.MenuConstants.ADD_INVOICE;
import static cli.MenuConstants.ADD_INVOICE_CSV;
import static cli.MenuConstants.EXIT;
import static cli.MenuConstants.FILTER_BY_AMOUNT;
import static cli.MenuConstants.FILTER_BY_SUPPLIER;
import static cli.MenuConstants.LIST_INVOICES;
import static cli.MenuConstants.MENU_OPTIONS;
import static cli.MenuConstants.REPORT_GENERATOR;
import static cli.MenuConstants.TOP_3_INVOICES;
import static cli.MenuConstants.TOTAL_PER_SUPPLIER;

import cli.CLI;
import repository.InvoiceRepository;
import repository.InvoiceRepositoryImpl;
import service.InvoiceService;
import service.impl.InvoiceServiceImpl;
import service.ReportService;
import service.impl.ReportServiceImpl;

public class InvoiceApp {
    public static void main(String[] args) {

        InvoiceRepository repository = new InvoiceRepositoryImpl();
        InvoiceService service = new InvoiceServiceImpl(repository);
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
        cli.addCLIOption(REPORT_GENERATOR, MENU_OPTIONS.get(REPORT_GENERATOR), reportGeneratorHandler(service, reportService));
        cli.addCLIOption(EXIT, MENU_OPTIONS.get(EXIT), exitHandler());
    }
}