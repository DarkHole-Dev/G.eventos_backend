package com.darkhole.gerenteEventos.shared.database.repository;

import com.darkhole.gerenteEventos.shared.database.entity.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<EventEntity, String> {

}
