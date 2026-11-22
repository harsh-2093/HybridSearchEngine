package org.example.retrieval;

import java.util.HashMap;
import java.util.Map;

public class IDFCalculator {
    public Map<String,Double> calculate(Map<String,Integer> documentFrequency, int totalDocuments){
        Map<String,Double>idf=new HashMap<>();

        for(Map.Entry<String,Integer> entry:documentFrequency.entrySet()){
            String term =entry.getKey();
            int df=entry.getValue();

            double score =Math.log(1.0+(totalDocuments-df+0.5)/(df+0.5));
            idf.put(term,score);
        }
        return idf;
    }
}
