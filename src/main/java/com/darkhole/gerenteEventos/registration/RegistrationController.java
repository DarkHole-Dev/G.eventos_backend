package com.darkhole.gerenteEventos.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.darkhole.gerenteEventos.registration.dto.CreateRegistrationDTO;
import com.darkhole.gerenteEventos.shared.dto.DTO;
import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/create")
    public ResponseEntity<ResultDTO> createRegistration(
        @RequestHeader("Authorization") String token,
        @RequestBody CreateRegistrationDTO json
    ) {
        if (token == null || json == null || json.eventId == null) {
            return DTO.BAD_REQUEST("Token and event are required");
        }

        if (!registrationService.userExists(token)) {
            return DTO.UNAUTHORIZED("Invalid token");
        }

        if (!registrationService.eventExists(json.eventId)) {
            return DTO.BAD_REQUEST("Invalid event");
        }

        if (registrationService.isUserRegistered(token, json.eventId)) {
            return DTO.BAD_REQUEST("User is already registered for this event");
        }

        if (!registrationService.hasAvailableSlot(json.eventId)) {
            return DTO.BAD_REQUEST("Event has no available slots");
        }

        return registrationService.createRegistration(token, json.eventId)
            .<ResponseEntity<ResultDTO>>map(registration -> ResponseEntity.ok().body(registration))
            .orElseGet(() -> DTO.BAD_REQUEST("Invalid registration data"));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResultDTO> getRegistration(
        @RequestHeader("Authorization") String token,
        @PathVariable String id
    ) {
        if (token == null || id == null) {
            return DTO.BAD_REQUEST("Token and registration id are required");
        }

        if (!registrationService.userExists(token)) {
            return DTO.UNAUTHORIZED("Invalid token");
        }

        return registrationService.getRegistration(token, id)
            .<ResponseEntity<ResultDTO>>map(registration -> ResponseEntity.ok().body(registration))
            .orElseGet(() -> DTO.BAD_REQUEST("Registration not found"));
    }

    @GetMapping("/list")
    public ResponseEntity<ResultDTO> getUserRegistrations(
        @RequestHeader("Authorization") String token
    ) {
        if (token == null) {
            return DTO.BAD_REQUEST("Token is required");
        }

        if (!registrationService.userExists(token)) {
            return DTO.UNAUTHORIZED("Invalid token");
        }

        return ResponseEntity.ok().body(registrationService.getUserRegistrations(token));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<ResultDTO> getEventRegistrations(
        @RequestHeader("Authorization") String token,
        @PathVariable String eventId
    ) {
        if (token == null || eventId == null) {
            return DTO.BAD_REQUEST("Token and event are required");
        }

        if (!registrationService.userExists(token)) {
            return DTO.UNAUTHORIZED("Invalid token");
        }

        return registrationService.getEventRegistrations(eventId)
            .<ResponseEntity<ResultDTO>>map(registrations -> ResponseEntity.ok().body(registrations))
            .orElseGet(() -> DTO.BAD_REQUEST("Invalid event"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResultDTO> updateRegistration(
        @RequestHeader("Authorization") String token,
        @PathVariable String id,
        @RequestParam String status
    ) {
        if (token == null || id == null || status == null) {
            return DTO.BAD_REQUEST("Token, registration id, and status are required");
        }

        if (!registrationService.userExists(token)) {
            return DTO.UNAUTHORIZED("Invalid token");
        }

        if (!registrationService.isValidStatus(status)) {
            return DTO.BAD_REQUEST("Invalid status");
        }

        return registrationService.updateRegistration(token, id, status)
            ? DTO.SUCCESS()
            : DTO.BAD_REQUEST("Registration not found or event has no available slots");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResultDTO> deleteRegistration(
        @RequestHeader("Authorization") String token,
        @PathVariable String id
    ) {
        if (token == null || id == null) {
            return DTO.BAD_REQUEST("Token and registration id are required");
        }

        if (!registrationService.userExists(token)) {
            return DTO.UNAUTHORIZED("Invalid token");
        }

        return registrationService.deleteRegistration(token, id)
            ? DTO.SUCCESS()
            : DTO.BAD_REQUEST("Registration not found");
    }
}
