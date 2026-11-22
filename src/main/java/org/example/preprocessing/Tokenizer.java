package org.example.preprocessing;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Tokenizer {

    public List<String> tokenize(String text)
    {
        if(text==null || text.isBlank())
        {
            return new ArrayList<>();
        }

        //convret to lowercase
        text=text.toLowerCase(Locale.ROOT);

        //Remove puntutation
        text=text.replaceAll("[^a-z0-9\\s]","");

        //split text into indivisula words

        String[] words=text.trim().split("\\s+");

        List<String> tokens=new ArrayList<>();

        for(String word:words){
            if(!word.isBlank()){
                tokens.add(word);
            }
        }
        return tokens;
    }
}
