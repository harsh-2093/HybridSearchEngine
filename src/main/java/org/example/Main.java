
package org.example;

import org.example.model.SearchResult;
import org.example.retrieval.SemanticSearch;
import org.example.service.SearchService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runSearch(
            SearchService searchService,
            SemanticSearch semanticSearch) {

        return args -> {

            String query = "How do microservices communicate?";

            // BM25 results
            List<SearchResult> bm25Results =
                    searchService.search(query);

            // SBERT results
            List<SearchResult> semanticResults =
                    semanticSearch.search(query);

            System.out.println("\n========== BM25 RESULTS ==========");

            for (int i = 0; i < bm25Results.size(); i++) {

                SearchResult result = bm25Results.get(i);

                System.out.println(
                        "Rank: " + (i + 1)
                );

                System.out.println(
                        "Title: " + result.getDocument().getTitle()
                );

                System.out.println(
                        "BM25 Score: " + result.getScore()
                );

                System.out.println("-------------------------");
            }

            System.out.println("\n========== SBERT RESULTS ==========");

            for (int i = 0; i < semanticResults.size(); i++) {

                SearchResult result = semanticResults.get(i);

                System.out.println(
                        "Rank: " + (i + 1)
                );

                System.out.println(
                        "Title: " + result.getDocument().getTitle()
                );

                System.out.println(
                        "Cosine Similarity: " + result.getScore()
                );

                System.out.println("-------------------------");
            }
        };
    }
}
