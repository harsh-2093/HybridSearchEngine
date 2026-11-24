package org.example.service;

import org.example.ingestion.DocumentLoader;
import org.example.model.Document;
import org.example.model.SearchResult;
import org.example.preprocessing.Tokenizer;
import org.example.retrieval.BM25;
import org.example.retrieval.DocumentFrequency;
import org.example.retrieval.IDFCalculator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    private final DocumentLoader documentLoader;

    public SearchService(DocumentLoader documentLoader) {
        this.documentLoader = documentLoader;
    }

    public List<SearchResult> search(String queryText) throws IOException {

        // 1. Load sample documents
        List<Document> documents = documentLoader.loadDocuments();

        // 2. Tokenize documents
        Tokenizer tokenizer = new Tokenizer();

        for (Document document : documents) {
            document.setTokens(tokenizer.tokenize(document.getContent()));
        }

        // 3. Calculate DF
        DocumentFrequency dfCalculator = new DocumentFrequency();
        Map<String, Integer> df = dfCalculator.calculate(documents);

        // 4. Calculate IDF
        IDFCalculator idfCalculator = new IDFCalculator();
        Map<String, Double> idf =
                idfCalculator.calculate(df, documents.size());

        // 5. Initialize BM25
        BM25 bm25 = new BM25(1.5, 0.75, documents, idf);

        // 6. Tokenize query
        List<String> query = tokenizer.tokenize(queryText);

        // 7. Calculate scores
        List<SearchResult> results=new ArrayList<>();
        for (Document document : documents) {
            double score = bm25.calculateScore(document, query);
            if(score>0)
            {
                results.add(new SearchResult(score,document));
            }
        }
        results.sort(
                Comparator.comparingDouble(SearchResult::getScore).reversed()
        );
        //9:return top 10 result
        return results.stream()
                .limit(10)
                .toList();

    }
}
