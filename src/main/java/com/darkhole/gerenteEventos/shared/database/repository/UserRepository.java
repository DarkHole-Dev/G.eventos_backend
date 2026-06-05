package com.darkhole.gerenteEventos.shared.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    
}
