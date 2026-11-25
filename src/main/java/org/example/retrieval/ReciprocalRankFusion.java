package org.example.retrieval;

import org.example.model.Document;
import org.example.model.SearchResult;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReciprocalRankFusion {
    private static final int K=60;

    public List<SearchResult> fuse(List<SearchResult>bm25Results,List<SearchResult>sematicResults){
        Map<Integer,Double> scores=new HashMap<>();
        Map<Integer, Document>documents=new HashMap<>();

        for(int i=0;i<bm25Results.size();i++)
        {
            SearchResult result=bm25Results.get(i);
            int documentId=result.getDocument().getId();
            documents.put(documentId,result.getDocument());
            double contribution=1.0/(K+i+1);
            scores.merge(documentId,contribution,Double::sum);
        }
        for(int i=0;i<sematicResults.size();i++)
        {
            SearchResult result=sematicResults.get(i);
            int documentId=result.getDocument().getId();
            documents.put(documentId,result.getDocument());
            double contribution=1.0/(K+i+1);
            scores.merge(documentId,contribution,Double::sum);
        }

        List<SearchResult> fusedResults =new ArrayList<>();
        for(Map.Entry<Integer,Double> entry: scores.entrySet()){
            int documentId=entry.getKey();
            double rrfScore=entry.getValue();
            Document document=documents.get(documentId);
            fusedResults.add(new SearchResult(rrfScore,document));
        }
        fusedResults.sort(
                Comparator.comparingDouble(SearchResult::getScore).reversed()
        );

        return fusedResults.stream().limit(10).toList();
    }
}
