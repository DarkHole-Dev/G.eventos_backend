package com.darkhole.gerenteEventos.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.darkhole.gerenteEventos.permission.dto.PermissionDTO;
import com.darkhole.gerenteEventos.permission.dto.PermissionRegisterDTO;
import com.darkhole.gerenteEventos.permission.dto.PermissionsDTO;
import com.darkhole.gerenteEventos.shared.database.entity.PermissionEntity;
import com.darkhole.gerenteEventos.shared.database.repository.PermissionRepository;
import com.darkhole.gerenteEventos.shared.database.service.VerificationService;
import com.darkhole.gerenteEventos.shared.dto.response.VerificationDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
	private final PermissionRepository permissionRepository;

	private final VerificationService verificationService;

	public PermissionDTO createPermission(PermissionRegisterDTO permission) {
		List<String> verificationIds = permission.verificationId == null
			? new ArrayList<>()
			: permission.verificationId;

		PermissionEntity created = permissionRepository.save(PermissionEntity.builder()
			.name(permission.name)
			.verificationId(verificationIds)
			.build()
		);

		return toDTO(created);
	}

	public Optional<PermissionDTO> getPermission(String id) {
		return permissionRepository.findById(id).map(this::toDTO);
	}

	public PermissionsDTO getPermissions() {
		List<PermissionDTO> permissions = permissionRepository.findAll()
			.stream()
			.map(this::toDTO)
			.toList();

		return PermissionsDTO.builder()
			.permissions(permissions)
			.build();
	}

	public boolean updatePermission(String id, PermissionRegisterDTO permission) {
		PermissionEntity existing = permissionRepository.findById(id).orElse(null);

		if (existing == null) return false;

		if (permission.name != null) {
			existing.setName(permission.name);
		}

		if (permission.verificationId != null) {
			existing.setVerificationId(permission.verificationId);
		}

		permissionRepository.save(existing);

		return true;
	}

	public boolean deletePermission(String id) {
		if (!permissionRepository.existsById(id)) return false;

		permissionRepository.deleteById(id);

		return true;
	}

	private PermissionDTO toDTO(PermissionEntity permission) {
		List<VerificationDTO> verifications = new ArrayList<>();

		if (permission.getVerificationId() != null) {
			for (String verificationId : permission.getVerificationId()) {
				try {
					verifications.add(verificationService.getVerification(verificationId));
				} catch (Exception ignored) {
				}
			}
		}

		return PermissionDTO.builder()
			.id(permission.getId())
			.name(permission.getName())
			.verifications(verifications)
			.build();
	}
}
