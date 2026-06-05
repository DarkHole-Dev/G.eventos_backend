package com.darkhole.gerenteEventos.shared.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.CategoryEntity;

public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {

}
