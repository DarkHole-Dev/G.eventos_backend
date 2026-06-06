package com.darkhole.gerenteEventos.user.dto;

import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.Builder;

@Builder
public class UserDTO extends ResultDTO {
    public String name;

    public String email;

    public String imageUrl;
}
