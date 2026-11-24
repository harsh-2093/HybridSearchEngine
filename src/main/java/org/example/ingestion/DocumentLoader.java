package org.example.ingestion;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.model.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentLoader {
    public List<Document> loadDocuments() throws IOException {
        List<Document> documents=new ArrayList<>();

        ClassPathResource resource=new ClassPathResource("documents.csv");

        try(
                InputStream inputStream=resource.getInputStream();
                CSVParser parser=CSVParser.parse(
                        inputStream,
                        StandardCharsets.UTF_8,
                        CSVFormat.DEFAULT.builder()
                                .setHeader()
                                .setSkipHeaderRecord(true)
                                .build()
                )
                ){
            for(CSVRecord record:parser){
                int id=Integer.parseInt(record.get("id"));
                String title=record.get("title");
                String content=record.get("content");
                Document document =
                        new Document(id, title, content);

                documents.add(document);
            }
        }
        return documents;
    }
}
