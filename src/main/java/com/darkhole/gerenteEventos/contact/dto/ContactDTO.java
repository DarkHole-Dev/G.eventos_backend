package com.darkhole.gerenteEventos.contact.dto;

import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.Builder;

@Builder
public class ContactDTO extends ResultDTO {
    public String email;
    public String phone;
}
