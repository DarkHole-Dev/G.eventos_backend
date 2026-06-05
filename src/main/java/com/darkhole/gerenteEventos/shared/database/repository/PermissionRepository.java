package com.darkhole.gerenteEventos.shared.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.PermissionEntity;

public interface PermissionRepository extends MongoRepository<PermissionEntity, String> {

}
