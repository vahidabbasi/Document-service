package com.assignment.controller;

import com.assignment.model.response.CreateDocumentResponse;
import com.assignment.model.response.ErrorResponse;
import com.assignment.model.response.GetDocumentResponse;
import com.assignment.service.DocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.net.HttpURLConnection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/v1")
@Validated
@Api("A REST-controller to handle various file operations")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(final DocumentService documentService) {
        Objects.requireNonNull(documentService, "documentService was null when injected");
        this.documentService = documentService;

    }

    @PostMapping("/documents/new")
    @ApiOperation(value = "Save document on database")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "Document has been saved", response =
                    CreateDocumentResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "The request is missing or have badly" +
                    " formatted"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAVAILABLE, message = "Server error", response =
                    ErrorResponse.class)
    })
    public CreateDocumentResponse saveDocument(@RequestPart("file") @NotNull final MultipartFile file) {
        return documentService.saveDocument(file);
    }

    @GetMapping("/documents")
    @ApiOperation(value = "Get all documents from database")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Documents has been retrieved", response =
                    GetDocumentResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAVAILABLE, message = "Server error", response =
                    ErrorResponse.class)
    })
    public GetDocumentResponse getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @DeleteMapping("/documents/{documentId}")
    @ApiOperation(value = "Delete related document form database")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Document has been deleted"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAVAILABLE, message = "Server error", response =
                    ErrorResponse.class)
    })

    public ResponseEntity<Void> removeDocument(@PathVariable final String documentId) {
        documentService.removeDocument(documentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/documents/{documentId}")
    @ApiOperation(value = "Update related document form database")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Document has been updated"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "The request is missing or have badly" +
                    " formatted"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAVAILABLE, message = "Server error", response =
                    ErrorResponse.class)
    })
    public CreateDocumentResponse updateDocumentInfo(@RequestPart("file") @NotNull final MultipartFile file,
                                                     @PathVariable final String documentId) {
        return documentService.updateDocumentInfo(file, documentId);
    }

}
