package com.darkhole.gerenteEventos.shared.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.VerificationEntity;

public interface VerificationRepository extends MongoRepository<VerificationEntity, String> {

}
