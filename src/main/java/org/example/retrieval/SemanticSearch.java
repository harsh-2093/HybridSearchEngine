package org.example.retrieval;

import jakarta.annotation.PostConstruct;
import org.example.ingestion.DocumentLoader;
import org.example.model.Document;
import org.example.model.SearchResult;
import org.example.service.EmbeddingService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SemanticSearch {

    private final DocumentLoader documentLoader;
    private final EmbeddingService embeddingService;

    private final Map<Integer,Document> documents=new LinkedHashMap<>();
    private final Map<Integer,float[]> documentsVectors=new LinkedHashMap<>();

    public SemanticSearch(DocumentLoader documentLoader, EmbeddingService embeddingService) {
        this.documentLoader = documentLoader;
        this.embeddingService = embeddingService;
    }
    @PostConstruct
    public void initializing() throws IOException {
        try{
            List<Document> allDocuments=documentLoader.loadDocuments();

            for(Document document:allDocuments){
                String text=document.getTitle()+" "+document.getContent();

                float[]  vector=embeddingService.generateEmbedding(text);

                documents.put(document.getId(),document);
                documentsVectors.put(document.getId(),vector);
            }
            System.out.println("Documents loaded: " + documents.size());
            System.out.println("Embeddings generated: " + documentsVectors.size());
        }catch(IOException e){
            throw new RuntimeException("Failed to initialize document embeddings", e);
        }
    }

    public List<SearchResult>search(String queryText){
        //1.Generate query embedding
        float[] queryVector=embeddingService.generateEmbedding(queryText);

        //2.store similarity score
        List<SearchResult>result =new ArrayList<>();

        //3. compare query vec with doc vector
        for(Map.Entry<Integer,float[]>entry:documentsVectors.entrySet()){
            int documentID=entry.getKey();
            float[] documentVector=entry.getValue();

            double score=cosineSimilarity(queryVector,documentVector);

            Document document=documents.get(documentID);

            result.add(new SearchResult(score,document));
        }
        //4. sort by similarity
        result.sort(
                Comparator.comparingDouble(SearchResult::getScore)
                        .reversed()
        );

        // 5. Return top 10
        return result.stream().limit(10).toList();
    }

    public double cosineSimilarity(float[] vectorA, float[] vectorB) {

        double dotProduct = 0.0;
        double magnitudeA = 0.0;
        double magnitudeB = 0.0;

        for (int i = 0; i < vectorA.length; i++) {

            dotProduct += vectorA[i] * vectorB[i];

            magnitudeA += vectorA[i] * vectorA[i];

            magnitudeB += vectorB[i] * vectorB[i];
        }

        magnitudeA = Math.sqrt(magnitudeA);
        magnitudeB = Math.sqrt(magnitudeB);

        if (magnitudeA == 0 || magnitudeB == 0) {
            return 0.0;
        }

        return dotProduct / (magnitudeA * magnitudeB);
    }
}