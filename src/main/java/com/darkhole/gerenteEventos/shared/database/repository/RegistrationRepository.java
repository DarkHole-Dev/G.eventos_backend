package com.darkhole.gerenteEventos.shared.database.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.darkhole.gerenteEventos.shared.database.entity.RegistrationEntity;

public interface RegistrationRepository extends MongoRepository<RegistrationEntity, String> {
    boolean existsByUserIdAndEventId(String userId, String eventId);

    List<RegistrationEntity> findByUserId(String userId);

    List<RegistrationEntity> findByEventId(String eventId);

    long countByEventIdAndStatusIn(String eventId, List<String> statuses);
}
