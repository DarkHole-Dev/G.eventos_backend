package com.darkhole.gerenteEventos.registration.dto;

import java.util.List;

import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.Builder;

@Builder
public class RegistrationsDTO extends ResultDTO {
    public List<RegistrationDTO> registrations;
}
