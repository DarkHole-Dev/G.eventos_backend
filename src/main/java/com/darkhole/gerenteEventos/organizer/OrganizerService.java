package com.darkhole.gerenteEventos.organizer;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.darkhole.gerenteEventos.contact.ContactService;
import com.darkhole.gerenteEventos.contact.dto.ContactDTO;
import com.darkhole.gerenteEventos.location.LocationService;
import com.darkhole.gerenteEventos.location.dto.LocationDTO;
import com.darkhole.gerenteEventos.organizer.dto.OrganizerDTO;
import com.darkhole.gerenteEventos.shared.database.CDN;
import com.darkhole.gerenteEventos.shared.database.entity.OrganizerEntity;
import com.darkhole.gerenteEventos.shared.database.repository.OrganizerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizerService {
    private final CDN cdn;

    private final ContactService contactService;

    private final LocationService locationService;

    private final OrganizerRepository organizerRepository;

    private final PasswordEncoder passwordEncoder;

    public String registerOrganizer(String name, String cnpj, String password) {
        return organizerRepository.save(OrganizerEntity.builder()
            .name(name)
            .cnpj(cnpj)
            .password(passwordEncoder.encode(password))
            .build()
        ).getId();
    }

    public String loginOrganizer(String cnpj, String password) {
        return organizerRepository.findByCnpj(cnpj)
            .filter(organizer -> passwordEncoder.matches(password, organizer.getPassword()))
            .map(OrganizerEntity::getId)
            .orElse("");
    }

    public boolean updateOrganizer(String organizerId, String nameField, String value) {
        OrganizerEntity organizer = organizerRepository.findById(organizerId).orElse(null);

        if (organizer == null) return false;

        switch (nameField) {
            case "name":
                organizer.setName(value);
                break;
            case "cnpj":
                organizer.setCnpj(value);
                break;
            case "password":
                organizer.setPassword(passwordEncoder.encode(value));
                break;
            default:
                return false;
        }

        organizerRepository.save(organizer);

        return true;
    }

    public boolean deleteOrganizer(String organizerId) {
        if (!organizerRepository.existsById(organizerId)) return false;

        OrganizerEntity organizer = organizerRepository.findById(organizerId).get();

        if (organizer.getImageUrl() != null && !organizer.getImageUrl().isEmpty()) {
            cdn.delete(organizer.getImageUrl());
        }

        organizerRepository.deleteById(organizerId);

        return true;
    }

    public boolean updateOrganizerImage(String organizerId, String fileName, String contentType, InputStream fileContent) {
        OrganizerEntity organizer = organizerRepository.findById(organizerId).orElse(null);

        if (organizer == null) return false;

        if (organizer.getImageUrl() != null && !organizer.getImageUrl().isEmpty()) {
            cdn.delete(organizer.getImageUrl());
        }
        
        organizer.setImageUrl(cdn.upload(fileName, contentType, fileContent));
        organizerRepository.save(organizer);

        return true;
    }

    public boolean updateOrganizerContact(String organizerId, String field, String value) {
        OrganizerEntity organizer = organizerRepository.findById(organizerId).orElse(null);

        if (organizer == null) return false;

        String contactId = organizer.getContactId();

        if (contactId == null || contactId.isEmpty()) {
            contactId = contactService.createContact("", "");
            organizer.setContactId(contactId);
        }

        boolean updated = contactService.updateContact(contactId, field, value);

        if (!updated) return false;

        organizerRepository.save(organizer);

        return true;
    }

    public boolean updateOrganizerLocation(String organizerId, String field, String value) {
        OrganizerEntity organizer = organizerRepository.findById(organizerId).orElse(null);

        if (organizer == null) return false;

        String locationId = organizer.getLocationId();

        if (locationId == null || locationId.isEmpty()) {
            locationId = locationService.createLocation("", "", "", "", "", "", "");
            organizer.setLocationId(locationId);
        }

        boolean updated = locationService.updateLocation(locationId, field, value);

        if (!updated) return false;

        organizerRepository.save(organizer);

        return true;
    }

    public Optional<OrganizerDTO> getOrganizer(String organizerId) {
        return organizerRepository.findById(organizerId).map(organizer -> OrganizerDTO.builder()
            .imageUrl(organizer.getImageUrl())
            .name(organizer.getName())
            .cnpj(organizer.getCnpj())
            .contact(getContact(organizer.getContactId()))
            .location(getLocation(organizer.getLocationId()))
            .build()
        );
    }

    private ContactDTO getContact(String contactId) {
        if (contactId == null || contactId.isEmpty()) return null;

        var contact = contactService.getContact(contactId);

        if (contact == null) return null;

        return ContactDTO.builder()
            .email(contact.getEmail())
            .phone(contact.getPhone())
            .build();
    }

    private LocationDTO getLocation(String locationId) {
        if (locationId == null || locationId.isEmpty()) return null;

        return locationService.getLocation(locationId)
            .map(location -> LocationDTO.builder()
                .country(location.country)
                .state(location.state)
                .city(location.city)
                .neighborhood(location.neighborhood)
                .street(location.street)
                .number(location.number)
                .cep(location.cep)
                .build()
            )
            .orElse(null);
    }

    public Optional<Resource> getOrganizerImage(String organizerId) {
        if (!organizerRepository.existsById(organizerId)) return Optional.empty();

        String imageUrl = organizerRepository.findById(organizerId).get().getImageUrl();

        if (imageUrl == null || imageUrl.isEmpty()) return Optional.empty();

        return cdn.download(imageUrl);
    }
}