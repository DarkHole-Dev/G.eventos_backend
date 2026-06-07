package com.darkhole.gerenteEventos.permission.dto;

import java.util.List;

import com.darkhole.gerenteEventos.shared.dto.response.VerificationDTO;
import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.Builder;


@Builder
public class PermissionDTO extends ResultDTO {
    public String id;

    public String name;

    public List<VerificationDTO> verifications;
}
