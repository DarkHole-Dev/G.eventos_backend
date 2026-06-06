package com.darkhole.gerenteEventos.shared.database;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.gridfs.model.GridFSFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CDN {
    private final GridFsTemplate fs;

    public String upload(String fileName, String contentType, InputStream fileContent) {
        try {
            return fs.store(fileContent, fileName, contentType).toString();
        } catch (Exception e) {
            return "";
        }
    }

    public Optional<Resource> download(String id) {
        try {
            var blob = fs.findOne(Query.query(Criteria.where("_id").is(id)));
            
            if (!(blob instanceof GridFSFile)) return Optional.empty();
            
            return Optional.ofNullable(fs.getResource(blob.getFilename()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public void delete(String id) {
        if (fs.findOne(Query.query(Criteria.where("_id").is(id))) != null) {
            fs.delete(Query.query(Criteria.where("_id").is(id)));
        }
    }
}
