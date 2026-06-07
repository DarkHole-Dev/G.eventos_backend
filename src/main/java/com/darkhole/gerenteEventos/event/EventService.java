package com.darkhole.gerenteEventos.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.darkhole.gerenteEventos.event.dto.CreateEventDTO;
import com.darkhole.gerenteEventos.event.dto.EventCCDTO;
import com.darkhole.gerenteEventos.event.dto.EventDTO;
import com.darkhole.gerenteEventos.event.dto.EventsDTO;
import com.darkhole.gerenteEventos.shared.database.entity.EventEntity;
import com.darkhole.gerenteEventos.shared.database.repository.CategoryRepository;
import com.darkhole.gerenteEventos.shared.database.repository.CommentRepository;
import com.darkhole.gerenteEventos.shared.database.repository.EventRepository;
import com.darkhole.gerenteEventos.shared.database.repository.OrganizerRepository;
import com.darkhole.gerenteEventos.shared.database.repository.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    private final OrganizerRepository organizerRepository;

    private final CommentRepository commentRepository;
    
    private final CategoryRepository categoryRepository;

    private final PermissionRepository permissionRepository;

    public Optional<EventDTO> createEvent(CreateEventDTO event) {
        if (event == null) return Optional.empty();
        if (event.name == null || event.name.isEmpty()) return Optional.empty();
        if (event.slots == null || event.slots <= 0) return Optional.empty();
        if (event.date == null) return Optional.empty();
        if (event.organizerId == null || event.organizerId.isEmpty()) return Optional.empty();
        if (event.permissionId == null || event.permissionId.isEmpty()) return Optional.empty();

        if (!organizerRepository.existsById(event.organizerId)) return Optional.empty();
        if (!permissionRepository.existsById(event.permissionId)) return Optional.empty();

        EventEntity saved = eventRepository.save(EventEntity.builder()
            .name(event.name)
            .images(copyList(event.images))
            .slots(event.slots)
            .date(event.date)
            .organizerId(event.organizerId)
            .permissionId(event.permissionId)
            .categories(copyList(event.categories))
            .comments(copyList(event.comments))
            .locationId(event.locationId)
            .contactId(event.contactId)
            .build()
        );

        return Optional.of(toDTO(saved));
    }

    public Optional<EventDTO> getEvent(String id) {
        if (id == null || id.isEmpty()) return Optional.empty();

        return eventRepository.findById(id).map(this::toDTO);
    }

    public EventsDTO getEvents() {
        List<EventDTO> events = eventRepository.findAll()
            .stream()
            .map(this::toDTO)
            .toList();

        return EventsDTO.builder()
            .events(events)
            .build();
    }

    public boolean updateEvent(String token, String id, CreateEventDTO event) {
        if (token == null || token.isEmpty() || id == null || id.isEmpty() || event == null) return false;

        if (!organizerRepository.existsById(token)) return false;

        EventEntity existing = eventRepository.findById(id).orElse(null);

        if (existing == null || !token.equals(existing.getOrganizerId())) return false;

        if (event.name != null) existing.setName(event.name);
        if (event.images != null) existing.setImages(copyList(event.images));
        if (event.slots != null && event.slots > 0) existing.setSlots(event.slots);
        if (event.date != null) existing.setDate(event.date);
        if (event.organizerId != null) {
            if (!event.organizerId.isEmpty() && !organizerRepository.existsById(event.organizerId)) return false;
            existing.setOrganizerId(event.organizerId);
        }
        if (event.permissionId != null) {
            if (!event.permissionId.isEmpty() && !permissionRepository.existsById(event.permissionId)) return false;
            existing.setPermissionId(event.permissionId);
        }
        if (event.categories != null) existing.setCategories(copyList(event.categories));
        if (event.comments != null) existing.setComments(copyList(event.comments));
        if (event.locationId != null) existing.setLocationId(event.locationId);
        if (event.contactId != null) existing.setContactId(event.contactId);

        eventRepository.save(existing);
        return true;
    }

    public boolean deleteEvent(String token, String id) {
        if (token == null || token.isEmpty() || id == null || id.isEmpty()) return false;

        if (!organizerRepository.existsById(token)) return false;

        EventEntity existing = eventRepository.findById(id).orElse(null);

        if (existing == null || !token.equals(existing.getOrganizerId())) return false;

        eventRepository.deleteById(id);
        return true;
    }

    public EventCCDTO getEventByCC(List<String> categoryIds, List<String> commentIds) {
        return EventCCDTO.builder()
            .categories(categoryRepository.findAllById(categoryIds))
            .comments(commentRepository.findAllById(commentIds))
            .build();
    }

    public List<EventDTO> getEventsByCategory(String categoryName) {
        return eventRepository.findByCategoryName(categoryName)
            .stream()
            .map(this::toDTO)
            .toList();
    }

    private EventDTO toDTO(EventEntity event) {
        return EventDTO.builder()
            .id(event.getId())
            .name(event.getName())
            .images(copyList(event.getImages()))
            .slots(event.getSlots())
            .date(event.getDate())
            .organizerId(event.getOrganizerId())
            .permissionId(event.getPermissionId())
            .categories(copyList(event.getCategories()))
            .comments(copyList(event.getComments()))
            .locationId(event.getLocationId())
            .contactId(event.getContactId())
            .build();
    }

    private List<String> copyList(List<String> values) {
        return values == null ? new ArrayList<>() : new ArrayList<>(values);
    }
}
