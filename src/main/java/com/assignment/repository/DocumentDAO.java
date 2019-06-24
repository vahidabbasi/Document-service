package com.assignment.repository;

import com.assignment.model.response.DocumentInfo;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface DocumentDAO {

    @SqlUpdate("INSERT INTO DOCUMENT_INFO (DOCUMENT_ID, DOCUMENT_NAME, DOCUMENT_CONTENT)" +
            " VALUES (nextval('seq_id'), ?, ?)")
    @GetGeneratedKeys("DOCUMENT_ID")
    String saveDocumentInfo(String documentName, byte[] documentContent);

    @SqlQuery("SELECT DOCUMENT_NAME FROM DOCUMENT_INFO WHERE DOCUMENT_ID = :documentId")
    String getDocumentId(@Bind("documentId") String documentId);

    @SqlQuery("SELECT DOCUMENT_ID AS documentId, DOCUMENT_NAME AS documentName, CREATED_AT AS createdAt, " +
            "UPDATED_AT AS updatedAt, DELETED_AT AS deletedAt FROM DOCUMENT_INFO")
    @RegisterConstructorMapper(DocumentInfo.class)
    List<DocumentInfo> getDocumentsInfo();

    @SqlUpdate("UPDATE DOCUMENT_INFO SET DELETED_AT = SYSDATE, UPDATED_AT = SYSDATE WHERE DOCUMENT_ID = :documentId")
    void removeDocument(@Bind("documentId") String documentId);

    @SqlUpdate("UPDATE DOCUMENT_INFO SET UPDATED_AT = SYSDATE, DOCUMENT_CONTENT = :documentContent WHERE " +
            "DOCUMENT_ID = :documentId")
    void updateDocumentInfo(@Bind("documentContent") byte[] documentContent, @Bind("documentId") String documentId);
}
