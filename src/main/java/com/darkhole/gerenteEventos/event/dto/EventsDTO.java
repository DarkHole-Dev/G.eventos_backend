package com.darkhole.gerenteEventos.event.dto;

import java.util.List;

import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Builder
public class EventsDTO extends ResultDTO {
    public List<EventDTO> events;
}
