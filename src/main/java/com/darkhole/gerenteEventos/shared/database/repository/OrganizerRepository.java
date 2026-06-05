package com.darkhole.gerenteEventos.shared.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.OrganizerEntity;

public interface OrganizerRepository extends MongoRepository<OrganizerEntity, String> {

}
