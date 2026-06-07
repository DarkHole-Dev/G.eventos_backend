package com.darkhole.gerenteEventos.permission.dto;

import java.util.List;

import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.Builder;

@Builder
public class PermissionsDTO extends ResultDTO {
    public List<PermissionDTO> permissions;
}
