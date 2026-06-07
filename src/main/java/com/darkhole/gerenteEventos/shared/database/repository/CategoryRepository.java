package com.darkhole.gerenteEventos.shared.database.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.CategoryEntity;

public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {
    @Aggregation(pipeline = {
    "{ $match: { _id: { $in: ?0 } } }"
    })
    List<CategoryEntity> findByIds(List<String> ids);
}
