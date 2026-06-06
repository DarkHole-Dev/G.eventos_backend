package com.darkhole.gerenteEventos.organizer;

import java.io.InputStream;

import org.springframework.core.io.Resource;
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
import org.springframework.web.multipart.MultipartFile;

import com.darkhole.gerenteEventos.organizer.dto.OrganizerDTO;
import com.darkhole.gerenteEventos.organizer.dto.RegisterOrganizerDTO;
import com.darkhole.gerenteEventos.shared.dto.DTO;
import com.darkhole.gerenteEventos.shared.dto.ResultDTO;
import com.darkhole.gerenteEventos.shared.dto.TokenDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/v1/organizers")
@RequiredArgsConstructor
public class OrganizerController {
    private final OrganizerService organizerService;

    @PostMapping("/register")
    public ResponseEntity<ResultDTO> registerOrganizer(@RequestBody RegisterOrganizerDTO json) {
        if (json.name == null || json.cnpj == null || json.password == null)
            return DTO.BAD_REQUEST("All fields are required");

        String organizerId = organizerService.registerOrganizer(json.name, json.cnpj, json.password);

        return ResponseEntity.ok().body(TokenDTO.builder().token(organizerId).build());
    }

    @GetMapping("/login")
    public ResponseEntity<ResultDTO> loginOrganizer(@RequestParam String cnpj, @RequestParam String password) {
        if (cnpj == null || password == null)
            return DTO.BAD_REQUEST("CNPJ and password are required");

        String organizerId = organizerService.loginOrganizer(cnpj, password);

        if (organizerId.isEmpty())
            return DTO.UNAUTHORIZED("Invalid CNPJ or password");

        return ResponseEntity.ok().body(TokenDTO.builder().token(organizerId).build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResultDTO> updateOrganizer(
        @RequestHeader("Authorization") String token,
        @RequestParam String nameField,
        @RequestParam String value
    ) {
        if (token == null || nameField == null || value == null)
            return DTO.BAD_REQUEST("Token, name field, and value are required");

        if (!nameField.equals("name") && !nameField.equals("cnpj") && !nameField.equals("password"))
            return DTO.BAD_REQUEST("Invalid field name");

        return organizerService.updateOrganizer(token, nameField, value)
            ? DTO.SUCCESS()
            : DTO.UNAUTHORIZED("Invalid token");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResultDTO> deleteOrganizer(@RequestHeader("Authorization") String token) {
        if (token == null)
            return DTO.BAD_REQUEST("Token is required");

        return organizerService.deleteOrganizer(token)
            ? DTO.SUCCESS()
            : DTO.UNAUTHORIZED("Invalid token");
    }

    @PutMapping("/updatePhoto")
    public ResponseEntity<ResultDTO> updatePhoto(
        @RequestHeader("Authorization") String token,
        @RequestBody MultipartFile file
    ) {
        if (token == null || file == null)
            return DTO.BAD_REQUEST("Token and file are required");

        String name = file.getOriginalFilename();
        String contentType = file.getContentType();
        InputStream fileContent;

        try {
            fileContent = file.getInputStream();
        } catch (Exception e) {
            return DTO.BAD_REQUEST("Invalid file");
        }

        return organizerService.updateOrganizerImage(token, name, contentType, fileContent)
            ? DTO.SUCCESS()
            : DTO.UNAUTHORIZED("Invalid token");
    }

    @PutMapping("/updateContact")
    public ResponseEntity<ResultDTO> updateContact(
        @RequestHeader("Authorization") String token,
        @RequestParam String field,
        @RequestParam String value
    ) {
        if (token == null || field == null || value == null)
            return DTO.BAD_REQUEST("Token, field, and value are required");

        if (!field.equals("email") && !field.equals("phone"))
            return DTO.BAD_REQUEST("Invalid field name");

        return organizerService.updateOrganizerContact(token, field, value)
            ? DTO.SUCCESS()
            : DTO.UNAUTHORIZED("Invalid token");
    }

    @PutMapping("/updateLocation")
    public ResponseEntity<ResultDTO> updateLocation(
        @RequestHeader("Authorization") String token,
        @RequestParam String field,
        @RequestParam String value
    ) {
        if (token == null || field == null || value == null)
            return DTO.BAD_REQUEST("Token, field, and value are required");

        if (!field.equals("country")
            && !field.equals("state")
            && !field.equals("city")
            && !field.equals("neighborhood")
            && !field.equals("street")
            && !field.equals("number")
            && !field.equals("cep"))
            return DTO.BAD_REQUEST("Invalid field name");

        return organizerService.updateOrganizerLocation(token, field, value)
            ? DTO.SUCCESS()
            : DTO.UNAUTHORIZED("Invalid token");
    }

    @GetMapping("/get")
    public ResponseEntity<ResultDTO> getOrganizer(@RequestHeader("Authorization") String token) {
        if (token == null)
            return DTO.BAD_REQUEST("Token is required");

        var organizer = organizerService.getOrganizer(token);

        if (organizer.isEmpty())
            return DTO.UNAUTHORIZED("Invalid token");

        return ResponseEntity.ok().body(OrganizerDTO.builder()
            .imageUrl(organizer.get().getImageUrl())
            .name(organizer.get().getName())
            .cnpj(organizer.get().getCnpj())
            .location(organizer.get().getLocation())
            .contact(organizer.get().getContact())
            .build());
    }

    @GetMapping("/image/{organizerId}")
    public ResponseEntity<Resource> getOrganizerImage(@PathVariable String organizerId) {
        if (organizerId == null)
            return ResponseEntity.badRequest().build();

        return organizerService.getOrganizerImage(organizerId)
            .map(resource -> ResponseEntity.ok().body(resource))
            .orElse(ResponseEntity.notFound().build());
    }
}
