package com.assignment.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when validation failed or other cases
 */
public class DocumentException extends RuntimeException {
    private final String displayMessage;
    private final HttpStatus httpStatus;

    public DocumentException(final String message) {
        super(message);
        displayMessage = message;
        httpStatus = HttpStatus.FORBIDDEN;
    }

    public DocumentException(final String message,
                             final HttpStatus httpStatus) {
        super(message);
        displayMessage = message;
        this.httpStatus = httpStatus;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
