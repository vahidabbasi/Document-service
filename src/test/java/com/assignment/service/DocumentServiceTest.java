package com.assignment.service;

import com.assignment.exceptions.DocumentException;
import com.assignment.model.response.CreateDocumentResponse;
import com.assignment.model.response.GetDocumentResponse;
import com.assignment.repository.DocumentDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Collections;

import static com.assignment.utils.TestUtils.CREATE_DOCUMENT_RESPONSE;
import static com.assignment.utils.TestUtils.DOCUMENT_ID;
import static com.assignment.utils.TestUtils.DOCUMENT_INFO;
import static com.assignment.utils.TestUtils.DOCUMENT_NAME;
import static com.assignment.utils.TestUtils.GET_DOCUMENT_RESPONSE;
import static com.assignment.utils.TestUtils.MULTIPART_FILE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocumentServiceTest {

    @Mock
    private DocumentDAO documentDAO;

    @InjectMocks
    private DocumentService documentService;

    @Test
    public void shouldSaveDocument() throws IOException {
        when(documentDAO.saveDocumentInfo(anyString(), any())).thenReturn(DOCUMENT_ID);

        final CreateDocumentResponse actualResponse = documentService.saveDocument(MULTIPART_FILE);

        verify(documentDAO).saveDocumentInfo(DOCUMENT_NAME, MULTIPART_FILE.getBytes());
        assertEquals(CREATE_DOCUMENT_RESPONSE, actualResponse);
    }

    @Test
    public void shouldGetAllDocuments() {
        when(documentDAO.getDocumentsInfo()).thenReturn(Collections.singletonList(DOCUMENT_INFO));

        final GetDocumentResponse actualResponse = documentService.getAllDocuments();

        verify(documentDAO).getDocumentsInfo();
        assertEquals(GET_DOCUMENT_RESPONSE, actualResponse);
    }

    @Test
    public void shouldRemoveDocument() {
        documentService.removeDocument(DOCUMENT_ID);

        verify(documentDAO).removeDocument(DOCUMENT_ID);
    }

    @Test
    public void shouldUpdateDocumentInfo() throws IOException {
        when(documentDAO.getDocumentId(anyString())).thenReturn(DOCUMENT_NAME);

        final CreateDocumentResponse actualResponse = documentService.updateDocumentInfo(MULTIPART_FILE, DOCUMENT_ID);

        verify(documentDAO).getDocumentId(DOCUMENT_ID);
        verify(documentDAO).updateDocumentInfo(MULTIPART_FILE.getBytes(), DOCUMENT_ID);
        assertEquals(CREATE_DOCUMENT_RESPONSE, actualResponse);
    }

    @Test(expected = DocumentException.class)
    public void shouldUpdateDocumentInfoThrowException() {
        when(documentDAO.getDocumentId(anyString())).thenThrow(DocumentException.class);

        documentService.updateDocumentInfo(MULTIPART_FILE, DOCUMENT_ID);
    }
}
