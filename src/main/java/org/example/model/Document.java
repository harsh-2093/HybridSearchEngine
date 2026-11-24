package org.example.model;

import java.util.List;

public class Document {
    private int id;
    private String title;
    private String content;
    private List<String> tokens;

    public Document(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens)
    {
        this.tokens=tokens;
    }

    public int getDocumentLength(){
        return tokens==null?0:tokens.size();
    }
}

