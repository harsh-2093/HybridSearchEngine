package org.example.repository;

import org.example.model.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentRepository {
    private final List<Document> documents=new ArrayList<>();

    public DocumentRepository(Document document) {
        documents.add(document);
    }

    public List<Document>getAllDocument(){
        return documents;
    }
    public int getDocumentCount(){
        return documents.size();
    }
}
