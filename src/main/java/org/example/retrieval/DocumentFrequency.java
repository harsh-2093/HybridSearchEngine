package org.example.retrieval;

import org.example.model.Document;

import java.util.*;

public class DocumentFrequency {
    public Map<String,Integer> calculate(List<Document> documents){
        Map<String,Integer> df=new HashMap<>();

        for (Document document:documents){
            Set<String> uniqueTerms=new HashSet<>(document.getTokens());

            for(String term:uniqueTerms){
                df.put(term,df.getOrDefault(term,0)+1);
            }
        }
        return df;
    }
}
