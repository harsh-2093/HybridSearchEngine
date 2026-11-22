package org.example.retrieval;

import org.example.model.Document;

import java.util.List;
import java.util.Map;

public class BM25 {

    private final double k1;
    private final double b;
    private final double avgdl;
    private final Map<String,Double> idf;

    public BM25(double k1, double b, List<Document> documents,Map<String, Double> idf) {
        this.k1 = k1;
        this.b = b;
        int totallen=0;
        for(Document document:documents)
        {
            totallen+=document.getDocumentLength();
        }
        this.avgdl=(double)totallen/documents.size();
        this.idf=idf;
    }

    public double calculateScore(Document document,List<String> query){
        double score=0.0;

        for(String term :query)
        {
            int termCount=0;
            List<String>tokens=document.getTokens();

            for(String token:tokens){
                if(token.equals(term))termCount++;
            }
            int docLength=document.getDocumentLength();
            double lengthNorm=1-b+b*(docLength/avgdl);

            double tfScore=(termCount*(k1+1))/(termCount+k1*lengthNorm);
            double idfScore=idf.getOrDefault(term,0.0);

            score+=idfScore*tfScore;
        }
        return score;
    }
}
