package com.darkhole.gerenteEventos.shared.database.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.OrganizerEntity;

public interface OrganizerRepository extends MongoRepository<OrganizerEntity, String> {

	Optional<OrganizerEntity> findByCnpj(String cnpj);

}
