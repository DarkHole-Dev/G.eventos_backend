package com.darkhole.gerenteEventos.shared.database.repository;

import com.darkhole.gerenteEventos.shared.database.entity.EventEntity;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<EventEntity, String> {
    @Aggregation(pipeline = {
        "{ $match: { categories: ?0 } }"
    })
    List<EventEntity> findByCategoryName(String categoryName);
}
