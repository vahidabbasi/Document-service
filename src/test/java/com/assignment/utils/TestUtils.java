package com.assignment.utils;

import com.assignment.model.response.CreateDocumentResponse;
import com.assignment.model.response.DocumentInfo;
import com.assignment.model.response.GetDocumentResponse;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Collections;

public final class TestUtils {

    public static final MockMultipartFile MULTIPART_FILE = new MockMultipartFile("file", "testdata".getBytes());
    public static final String DOCUMENT_NAME = MULTIPART_FILE.getOriginalFilename();
    public static final String DOCUMENT_ID = "documentId";
    public static final DocumentInfo DOCUMENT_INFO = DocumentInfo.builder()
            .documentId(DOCUMENT_ID)
            .documentName(DOCUMENT_NAME)
            .build();
    public static final CreateDocumentResponse CREATE_DOCUMENT_RESPONSE = CreateDocumentResponse.builder()
            .documentInfo(DOCUMENT_INFO)
            .build();
    public static final GetDocumentResponse GET_DOCUMENT_RESPONSE = GetDocumentResponse.builder()
            .documentInfo(Collections.singletonList(DOCUMENT_INFO))
            .build();
}
