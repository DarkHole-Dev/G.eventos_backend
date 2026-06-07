package com.darkhole.gerenteEventos.permission;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.darkhole.gerenteEventos.permission.dto.PermissionRegisterDTO;
import com.darkhole.gerenteEventos.shared.dto.DTO;
import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping("/create")
    public ResponseEntity<ResultDTO> createPermission(@RequestBody PermissionRegisterDTO json) {
        if (json == null || json.name == null || json.name.isEmpty()) {
            return DTO.BAD_REQUEST("Permission name is required");
        }

        return ResponseEntity.ok().body(permissionService.createPermission(json));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResultDTO> getPermission(@PathVariable String id) {
        if (id == null || id.isEmpty()) {
            return DTO.BAD_REQUEST("Permission id is required");
        }

        return permissionService.getPermission(id)
            .<ResponseEntity<ResultDTO>>map(permission -> ResponseEntity.ok().body(permission))
            .orElseGet(() -> DTO.BAD_REQUEST("Permission not found"));
    }

    @GetMapping("/list")
    public ResponseEntity<ResultDTO> getPermissions() {
        return ResponseEntity.ok().body(permissionService.getPermissions());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResultDTO> updatePermission(
        @PathVariable String id,
        @RequestBody PermissionRegisterDTO json
    ) {
        if (id == null || id.isEmpty()) {
            return DTO.BAD_REQUEST("Permission id is required");
        }

        if (json == null) {
            return DTO.BAD_REQUEST("Update body is required");
        }

        if (json.name == null && json.verificationId == null) {
            return DTO.BAD_REQUEST("At least one field is required to update");
        }

        return permissionService.updatePermission(id, json)
            ? DTO.SUCCESS()
            : DTO.BAD_REQUEST("Permission not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResultDTO> deletePermission(@PathVariable String id) {
        if (id == null || id.isEmpty()) {
            return DTO.BAD_REQUEST("Permission id is required");
        }

        return permissionService.deletePermission(id)
            ? DTO.SUCCESS()
            : DTO.BAD_REQUEST("Permission not found");
    }
}
