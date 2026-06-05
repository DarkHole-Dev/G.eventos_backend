package com.darkhole.gerenteEventos.shared.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.lang.Nullable;

@Service
public class CDN {
    @Autowired
    private GridFsTemplate fs;

    public String upload(CDNFile file) {
        try {
            return fs.store(file.getFile(), file.getFileName(), file.getContentType()).toString();
        } catch (Exception e) {
            return "";
        }
    }

    public @Nullable CDNFile download(String id) {
        try {
            var blob = fs.findOne(Query.query(Criteria.where("_id").is(id)));
            if (!(blob instanceof GridFSFile)) {
                return null;
            }
            var resource = fs.getResource((GridFSFile) blob);

            return CDNFile.builder()
                    .fileName(resource.getFilename())
                    .contentType(resource.getContentType())
                    .file(resource.getInputStream())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

}
