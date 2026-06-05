package com.darkhole.gerenteEventos.shared.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.LocationEntity;

public interface LocationRepository extends MongoRepository<LocationEntity, String> {

}
