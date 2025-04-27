package main.java.com.invoicemanager.exception;

public class InvoiceServiceException extends RuntimeException {
    public InvoiceServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}