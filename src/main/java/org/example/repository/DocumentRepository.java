
package org.example.repository;

import org.example.model.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DocumentRepository {

    private final List<Document> documents = new ArrayList<>();

    public void addDocument(Document document) {
        documents.add(document);
    }

    public void addDocuments(List<Document> newDocuments) {
        documents.addAll(newDocuments);
    }

    public List<Document> getAllDocuments() {
        return List.copyOf(documents);
    }

    public int getDocumentCount() {
        return documents.size();
    }
}
