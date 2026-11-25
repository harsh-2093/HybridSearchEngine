package org.example.controller;

import org.example.model.SearchResult;
import org.example.retrieval.ReciprocalRankFusion;
import org.example.retrieval.SemanticSearch;
import org.example.service.SearchService;
import org.springframework.messaging.rsocket.service.RSocketExchange;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {
    private final SearchService searchService;
    private final SemanticSearch semanticSearch;
    private final ReciprocalRankFusion reciprocalRankFusion;

    public SearchController(SearchService searchService, SemanticSearch semanticSearch, ReciprocalRankFusion reciprocalRankFusion) {
        this.searchService = searchService;
        this.semanticSearch = semanticSearch;
        this.reciprocalRankFusion = reciprocalRankFusion;
    }

    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam String q) throws IOException {
        List<SearchResult> bm25Results=searchService.search(q);
        List<SearchResult> semanticResults = semanticSearch.search(q);

        List<SearchResult> hybridResults =
                reciprocalRankFusion.fuse(bm25Results, semanticResults);
        return  hybridResults;
    }
}
