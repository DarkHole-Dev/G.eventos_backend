package com.darkhole.gerenteEventos.event.dto;

import java.util.List;

import lombok.Builder;

@Builder
public class EventCCIdDTO {
    public List<String> categoryIds;

    public List<String> commentIds;
}
