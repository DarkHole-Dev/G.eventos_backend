package com.darkhole.gerenteEventos.registration;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.darkhole.gerenteEventos.registration.dto.RegistrationDTO;
import com.darkhole.gerenteEventos.registration.dto.RegistrationsDTO;
import com.darkhole.gerenteEventos.shared.database.entity.EventEntity;
import com.darkhole.gerenteEventos.shared.database.entity.RegistrationEntity;
import com.darkhole.gerenteEventos.shared.database.repository.EventRepository;
import com.darkhole.gerenteEventos.shared.database.repository.RegistrationRepository;
import com.darkhole.gerenteEventos.shared.database.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_CANCELED = "CANCELED";

    private static final List<String> ACTIVE_STATUSES = List.of(STATUS_PENDING, STATUS_CONFIRMED);

    private final RegistrationRepository registrationRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    public boolean userExists(String userId) {
        return userRepository.existsById(userId);
    }

    public boolean eventExists(String eventId) {
        return eventRepository.existsById(eventId);
    }

    public boolean isValidStatus(String status) {
        return STATUS_PENDING.equals(status)
            || STATUS_CONFIRMED.equals(status)
            || STATUS_CANCELED.equals(status);
    }

    public boolean isUserRegistered(String userId, String eventId) {
        return registrationRepository.existsByUserIdAndEventId(userId, eventId);
    }

    public boolean hasAvailableSlot(String eventId) {
        Optional<EventEntity> event = eventRepository.findById(eventId);

        if (event.isEmpty()) return false;

        long activeRegistrations = registrationRepository.countByEventIdAndStatusIn(eventId, ACTIVE_STATUSES);

        return activeRegistrations < event.get().getSlots();
    }

    public Optional<RegistrationDTO> createRegistration(String userId, String eventId) {
        if (!userExists(userId) || !eventExists(eventId)) return Optional.empty();
        if (isUserRegistered(userId, eventId) || !hasAvailableSlot(eventId)) return Optional.empty();

        RegistrationEntity registration = registrationRepository.save(RegistrationEntity.builder()
            .userId(userId)
            .eventId(eventId)
            .status(STATUS_CONFIRMED)
            .createdAt(Instant.now())
            .build()
        );

        return Optional.of(toDTO(registration));
    }

    public Optional<RegistrationDTO> getRegistration(String userId, String registrationId) {
        if (!userExists(userId)) return Optional.empty();

        return registrationRepository.findById(registrationId)
            .filter(registration -> registration.getUserId().equals(userId))
            .map(this::toDTO);
    }

    public RegistrationsDTO getUserRegistrations(String userId) {
        List<RegistrationDTO> registrations = registrationRepository.findByUserId(userId)
            .stream()
            .map(this::toDTO)
            .toList();

        return RegistrationsDTO.builder()
            .registrations(registrations)
            .build();
    }

    public Optional<RegistrationsDTO> getEventRegistrations(String eventId) {
        if (!eventExists(eventId)) return Optional.empty();

        List<RegistrationDTO> registrations = registrationRepository.findByEventId(eventId)
            .stream()
            .map(this::toDTO)
            .toList();

        return Optional.of(RegistrationsDTO.builder()
            .registrations(registrations)
            .build()
        );
    }

    public boolean updateRegistration(String userId, String registrationId, String status) {
        if (!userExists(userId) || !isValidStatus(status)) return false;

        RegistrationEntity registration = registrationRepository.findById(registrationId).orElse(null);

        if (registration == null || !registration.getUserId().equals(userId)) return false;

        boolean currentStatusIsActive = ACTIVE_STATUSES.contains(registration.getStatus());
        boolean newStatusIsActive = ACTIVE_STATUSES.contains(status);

        if (!currentStatusIsActive && newStatusIsActive && !hasAvailableSlot(registration.getEventId())) {
            return false;
        }

        registration.setStatus(status);
        registrationRepository.save(registration);

        return true;
    }

    public boolean deleteRegistration(String userId, String registrationId) {
        if (!userExists(userId)) return false;

        RegistrationEntity registration = registrationRepository.findById(registrationId).orElse(null);

        if (registration == null || !registration.getUserId().equals(userId)) return false;

        registrationRepository.deleteById(registrationId);

        return true;
    }

    private RegistrationDTO toDTO(RegistrationEntity registration) {
        return RegistrationDTO.builder()
            .id(registration.getId())
            .userId(registration.getUserId())
            .eventId(registration.getEventId())
            .status(registration.getStatus())
            .createdAt(registration.getCreatedAt())
            .build();
    }
}
