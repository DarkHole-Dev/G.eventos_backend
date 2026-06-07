package com.darkhole.gerenteEventos.registration.dto;

import java.time.Instant;

import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.Builder;

@Builder
public class RegistrationDTO extends ResultDTO {
    public String id;

    public String userId;

    public String eventId;

    public String status;

    public Instant createdAt;
}
