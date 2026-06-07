package com.darkhole.gerenteEventos.event;

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

import com.darkhole.gerenteEventos.event.dto.CreateEventDTO;
import com.darkhole.gerenteEventos.event.dto.EventCCIdDTO;
import com.darkhole.gerenteEventos.event.dto.EventsDTO;
import com.darkhole.gerenteEventos.shared.dto.DTO;
import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/events")
public class EventController {
    private final EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<ResultDTO> createEvent(@RequestBody CreateEventDTO json) {
        if (json == null
            || json.name == null
            || json.slots == null
            || json.date == null
            || json.organizerId == null
            || json.permissionId == null) {
            return DTO.BAD_REQUEST("Name, slots, date, organizerId and permissionId are required");
        }

        return eventService.createEvent(json)
            .<ResponseEntity<ResultDTO>>map(event -> ResponseEntity.ok().body(event))
            .orElseGet(() -> DTO.BAD_REQUEST("Invalid event data"));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResultDTO> getEvent(@PathVariable String id) {
        if (id == null || id.isEmpty()) {
            return DTO.BAD_REQUEST("Event id is required");
        }

        return eventService.getEvent(id)
            .<ResponseEntity<ResultDTO>>map(event -> ResponseEntity.ok().body(event))
            .orElseGet(() -> DTO.BAD_REQUEST("Event not found"));
    }

    @GetMapping("/list")
    public ResponseEntity<ResultDTO> getEvents() {
        return ResponseEntity.ok().body(eventService.getEvents());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResultDTO> updateEvent(
        @RequestHeader("Authorization") String token,
        @PathVariable String id,
        @RequestBody CreateEventDTO json
    ) {
        if (token == null || token.isEmpty() || id == null || id.isEmpty()) {
            return DTO.BAD_REQUEST("Token and event id are required");
        }

        if (json == null) {
            return DTO.BAD_REQUEST("Update body is required");
        }

        if (json.name == null
            && json.images == null
            && json.slots == null
            && json.date == null
            && json.organizerId == null
            && json.permissionId == null
            && json.categories == null
            && json.comments == null
            && json.locationId == null
            && json.contactId == null) {
            return DTO.BAD_REQUEST("At least one field is required to update");
        }

        return eventService.updateEvent(token, id, json)
            ? DTO.SUCCESS()
            : DTO.UNAUTHORIZED("Invalid token or event not owned by organizer");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResultDTO> deleteEvent(
        @RequestHeader("Authorization") String token,
        @PathVariable String id
    ) {
        if (token == null || token.isEmpty() || id == null || id.isEmpty()) {
            return DTO.BAD_REQUEST("Token and event id are required");
        }

        return eventService.deleteEvent(token, id)
            ? DTO.SUCCESS()
            : DTO.UNAUTHORIZED("Invalid token or event not owned by organizer");
    }


    @GetMapping("/cc")
    public ResponseEntity<ResultDTO> getMethodName(@RequestParam EventCCIdDTO ids) {
        if (ids == null || ids.categoryIds == null || ids.commentIds == null) {
            return DTO.BAD_REQUEST("At least one of categoryIds or commentIds is required");
        }

        return ResponseEntity.ok().body(
            eventService.getEventByCC(ids.categoryIds, ids.commentIds)
        );
    }


    @GetMapping("/searchByCategory")
    public ResponseEntity<ResultDTO> getMethodName(@RequestParam String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) {
            return DTO.BAD_REQUEST("Category name is required");
        }

        return ResponseEntity.ok().body(EventsDTO.builder().events(eventService.getEventsByCategory(categoryName)).build());
    }
    
    
}
