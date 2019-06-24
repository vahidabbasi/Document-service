package com.assignment.controller;

import com.assignment.model.response.CreateDocumentResponse;
import com.assignment.model.response.GetDocumentResponse;
import com.assignment.service.DocumentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.assignment.utils.TestUtils.CREATE_DOCUMENT_RESPONSE;
import static com.assignment.utils.TestUtils.DOCUMENT_ID;
import static com.assignment.utils.TestUtils.GET_DOCUMENT_RESPONSE;
import static com.assignment.utils.TestUtils.MULTIPART_FILE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    @Test
    public void shouldSaveDocument() {
        when(documentService.saveDocument(any())).thenReturn(CREATE_DOCUMENT_RESPONSE);

        final CreateDocumentResponse actualResponse = documentController.saveDocument(MULTIPART_FILE);

        verify(documentService).saveDocument(MULTIPART_FILE);
        assertEquals(CREATE_DOCUMENT_RESPONSE, actualResponse);
    }

    @Test
    public void shouldGetAllDocuments() {
        when(documentService.getAllDocuments()).thenReturn(GET_DOCUMENT_RESPONSE);

        final GetDocumentResponse actualResponse = documentController.getAllDocuments();

        verify(documentService).getAllDocuments();
        assertEquals(GET_DOCUMENT_RESPONSE, actualResponse);
    }

    @Test
    public void shouldRemoveDocument() {
        documentController.removeDocument(DOCUMENT_ID);

        verify(documentService).removeDocument(DOCUMENT_ID);
    }

    @Test
    public void shouldUpdateDocumentInfo() {
        when(documentService.updateDocumentInfo(any(), anyString())).thenReturn(CREATE_DOCUMENT_RESPONSE);

        final CreateDocumentResponse actualResponse = documentController.updateDocumentInfo(MULTIPART_FILE,
                DOCUMENT_ID);

        verify(documentService).updateDocumentInfo(MULTIPART_FILE, DOCUMENT_ID);
        assertEquals(CREATE_DOCUMENT_RESPONSE, actualResponse);
    }
}
