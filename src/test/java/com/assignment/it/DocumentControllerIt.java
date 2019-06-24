package com.assignment.it;

import com.assignment.exceptions.DocumentException;
import com.assignment.it.config.IntegrationTestConfiguration;
import com.assignment.model.response.CreateDocumentResponse;
import com.assignment.model.response.DocumentInfo;
import com.assignment.model.response.GetDocumentResponse;
import com.google.common.collect.ImmutableMap;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@Import(IntegrationTestConfiguration.class)
public class DocumentControllerIt {

    @Autowired
    RestTemplate testRestTemplate;

    private static final String BASE_URL = "http://localhost:8082/document-service/v1";
    private static final String SAVE_DOCUMENT_URL = "/documents/new";
    private static final String GET_DOCUMENTS_URL = "/documents";
    private static final String REMOVE_DOCUMENT_URL = "/documents/{documentId}";

    @After
    public void setup() {
        //TODO teardown data base
    }

    @Test
    public void saveDocument() {
        final MockMultipartFile file = new MockMultipartFile("file", "bar".getBytes());
        final CreateDocumentResponse actualResponse = saveDocument(file);

        assertEquals(file.getOriginalFilename(), actualResponse.getDocumentInfo().getDocumentName());
    }

    @Test
    public void getAllDocuments() {
        final MockMultipartFile file = new MockMultipartFile("file", "bar".getBytes());
        final CreateDocumentResponse saveResponse = saveDocument(file);

        assertEquals(file.getOriginalFilename(), saveResponse.getDocumentInfo().getDocumentName());
        assertTrue(getAllDocument().getDocumentInfo().size() > 0);
    }

    @Test
    public void removeDocument() {
        final MockMultipartFile file = new MockMultipartFile("file", "bar".getBytes());
        final CreateDocumentResponse saveResponse = saveDocument(file);

        assertEquals(file.getOriginalFilename(), saveResponse.getDocumentInfo().getDocumentName());

        removeDocument(saveResponse.getDocumentInfo().getDocumentId());

        final Optional<DocumentInfo> test = getAllDocument().getDocumentInfo().stream().filter
                (documentInfo -> documentInfo.getDocumentId()
                        .equals(saveResponse.getDocumentInfo().getDocumentId())).findFirst();

        assertNotNull(test.get().getDeletedAt());
    }

    private CreateDocumentResponse saveDocument(final MultipartFile file) {
        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        try {
            final ByteArrayResource contentsAsResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
            body.add("file", contentsAsResource);

            return testRestTemplate.postForEntity(BASE_URL + SAVE_DOCUMENT_URL,
                    new HttpEntity<>(body, getHttpHeaders(MediaType.MULTIPART_FORM_DATA)),
                    CreateDocumentResponse.class).getBody();

        } catch (final IOException e) {
            throw new DocumentException("Exception occurred when reading multipart file data : " + e.getMessage());
        }
    }

    private GetDocumentResponse getAllDocument() {
        return testRestTemplate.getForObject(BASE_URL + GET_DOCUMENTS_URL, GetDocumentResponse.class);
    }

    private void removeDocument(final String documentId) {

        final Map<String, Object> params = ImmutableMap.of(
                "documentId", documentId);
        testRestTemplate.delete(BASE_URL + REMOVE_DOCUMENT_URL, params);
    }

    private HttpHeaders getHttpHeaders(final MediaType mediaType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return headers;
    }
}
