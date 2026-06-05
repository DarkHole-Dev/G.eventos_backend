package com.darkhole.gerenteEventos.shared.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.RegistrationEntity;

public interface RegistrationRepository extends MongoRepository<RegistrationEntity, String> {

}
