package com.assignment.handlers;

import com.assignment.exceptions.DocumentException;
import com.assignment.model.response.ErrorResponse;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class DocumentExceptionHandlerTest {

    private static final String MESSAGE = "Exception message";

    private final DocumentExceptionHandler exceptionHandler = new DocumentExceptionHandler();

    @Test
    public void shouldHandleGeneralException() {

        final ResponseEntity responseEntity = exceptionHandler.handleException(new Exception());

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, responseEntity.getStatusCode());
        assertEquals(ErrorResponse.builder().build(), responseEntity.getBody());
    }

    @Test
    public void shouldHandleDocumentException() {
        final DocumentException documentException = new DocumentException(MESSAGE);

        final ResponseEntity responseEntity = exceptionHandler.handleDocumentException(documentException);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals(ErrorResponse.builder().message(MESSAGE).build(), responseEntity.getBody());
    }
}
