package com.darkhole.gerenteEventos.permission.dto;

import java.util.List;

import lombok.Data;

@Data
public class PermissionRegisterDTO {
	public String name;

	public List<String> verificationId;
}
