package com.darkhole.gerenteEventos.shared.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.ContactEntity;

public interface ContactRepository extends MongoRepository<ContactEntity, String> {

}
