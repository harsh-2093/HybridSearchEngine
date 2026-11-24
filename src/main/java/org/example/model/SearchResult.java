package org.example.model;

public class SearchResult {
    private final Document document;
    private final double score;

    public SearchResult(double score, Document document) {
        this.score = score;
        this.document = document;
    }

    public Document getDocument(){
        return document;
    }

    public double getScore(){
        return score;
    }
}
