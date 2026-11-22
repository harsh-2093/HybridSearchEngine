package org.example;

import org.example.model.Document;
import org.example.preprocessing.Tokenizer;
import org.example.retrieval.BM25;
import org.example.retrieval.DocumentFrequency;
import org.example.retrieval.IDFCalculator;

import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // 1. Create documents
        List<Document> documents = List.of(
                new Document(1, "Java",
                        "Java is a programming language. Java supports object oriented programming."),
                new Document(2, "Java Memory",
                        "Java supports garbage collection and memory management."),
                new Document(3, "Python",
                        "Python is a programming language used for machine learning.")
        );

        // 2. Tokenize documents
        Tokenizer tokenizer = new Tokenizer();

        for (Document document : documents) {
            document.setTokens(tokenizer.tokenize(document.getContent()));
        }

        // 3. Calculate document frequency
        DocumentFrequency dfCalculator = new DocumentFrequency();
        Map<String, Integer> df = dfCalculator.calculate(documents);

        // 4. Calculate IDF
        IDFCalculator idfCalculator = new IDFCalculator();
        Map<String, Double> idf =
                idfCalculator.calculate(df, documents.size());


        // 5. Initialize BM25
        BM25 bm25 = new BM25(1.5, 0.75, documents, idf);


        // 6. Search query
        List<String> query = tokenizer.tokenize("java programming");

        // 7. Calculate and display scores
        for (Document document : documents) {
            double score = bm25.calculateScore(document, query);

            System.out.println(
                    document.getTitle() + " -> " + score
            );
        }
    }
}