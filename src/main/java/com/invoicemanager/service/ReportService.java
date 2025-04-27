package main.java.com.invoicemanager.service;

/**
 * Specifically added for report generation use-cases and its storage.
 * It provides methods for generating markdown reports and saving them to files.
 */
public interface ReportService {

    /**
     * Generates a markdown formatted report containing various statistics and details about invoices.
     *
     * @return a string representing the markdown report
     */
    String generateMarkdownReport();

    /**
     * Writes the provided content to a file at the specified file path.
     *
     * @param content the content to be written to the file
     * @param filePath the path where the file should be saved
     */
    void writeReportToFile(String content, String filePath);
}
