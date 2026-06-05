package com.darkhole.gerenteEventos.shared.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.EventEntity;

public interface EventRepository extends MongoRepository<EventEntity, String> {

}
