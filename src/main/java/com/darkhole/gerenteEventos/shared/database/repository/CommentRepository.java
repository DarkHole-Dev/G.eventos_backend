package com.darkhole.gerenteEventos.shared.database.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.CommentEntity;

public interface CommentRepository extends MongoRepository<CommentEntity, String> {
    @Aggregation(pipeline = {
        "{ $match: { _id: { $in: ?0 } } }"
    })
    List<CommentEntity> findByIds(List<String> ids);
}
