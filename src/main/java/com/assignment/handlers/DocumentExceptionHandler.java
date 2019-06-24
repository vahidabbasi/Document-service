package com.assignment.handlers;

import com.assignment.exceptions.DocumentException;
import com.assignment.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.ResponseEntity.status;

@Slf4j
@ControllerAdvice
public class DocumentExceptionHandler {
    /**
     * To make sure that we do not let any exception through to the customer.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(final Exception exception) {
        log.error("RuntimeException: ", exception);
        return internalServerError(exception.getMessage());
    }

    @ExceptionHandler(DocumentException.class)
    public ResponseEntity handleDocumentException(final DocumentException exception) {
        log.error("DocumentException: {}", exception.getMessage());
        return createError(exception.getHttpStatus(), exception.getDisplayMessage());
    }

    /**
     * Returns an INTERNAL_SERVER_ERROR to the client with the given error message.
     */
    private static ResponseEntity<ErrorResponse> internalServerError(final String displayMessage) {
        log.error("Internal server error: {}", displayMessage);
        return createError(HttpStatus.SERVICE_UNAVAILABLE, displayMessage);
    }

    /**
     * Returns an HTTP error with the given statuses.
     */
    private static ResponseEntity<ErrorResponse> createError(final HttpStatus httpStatus, final String message) {
        return status(httpStatus).body(ErrorResponse.builder()
                .message(message)
                .build());
    }
}
