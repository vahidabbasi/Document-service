package com.assignment.service;

import com.assignment.exceptions.DocumentException;
import com.assignment.model.response.CreateDocumentResponse;
import com.assignment.model.response.DocumentInfo;
import com.assignment.model.response.GetDocumentResponse;
import com.assignment.repository.DocumentDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;


@Slf4j
@Service
public class DocumentService {

    private final DocumentDAO documentDAO;

    @Autowired
    public DocumentService(final DocumentDAO documentDAO) {
        Objects.requireNonNull(documentDAO, "documentDAO  was null when injected");
        this.documentDAO = documentDAO;
    }

    public CreateDocumentResponse saveDocument(final MultipartFile file) {
        final String documentId;
        final String fileName = file.getOriginalFilename();
        try {
            log.info("Save document with name: {} in database", fileName);
            documentId = documentDAO.saveDocumentInfo(fileName, file.getBytes());
        } catch (final IOException e) {
            throw new DocumentException("Exception occurred when reading file : " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }


        final DocumentInfo documentInfo = DocumentInfo.builder()
                .documentName(fileName)
                .documentId(documentId).build();

        return CreateDocumentResponse.builder()
                .documentInfo(documentInfo)
                .build();
    }

    public GetDocumentResponse getAllDocuments() {
        log.info("Get all Documents");
        return GetDocumentResponse.builder()
                .documentInfo(documentDAO.getDocumentsInfo())
                .build();
    }

    public void removeDocument(final String documentId) {
        log.info("Remove document with id: {} from database", documentId);
        documentDAO.removeDocument(documentId);
    }

    public CreateDocumentResponse updateDocumentInfo(final MultipartFile file, final String documentId) {
        final String documentName = documentDAO.getDocumentId(documentId);
        if (documentName == null) {
            throw new DocumentException("document does not exist in data base", HttpStatus.NOT_FOUND);
        }
        try {
            log.info("Save document with Id: {} in database", documentId);
            documentDAO.updateDocumentInfo(file.getBytes(), documentId);
        } catch (final IOException e) {
            throw new DocumentException("Exception occurred when reading file : " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        final DocumentInfo documentInfo = DocumentInfo.builder()
                .documentName(documentName)
                .documentId(documentId).build();

        return CreateDocumentResponse.builder()
                .documentInfo(documentInfo)
                .build();
    }
}
