package com.darkhole.gerenteEventos.event.dto;

import java.util.Date;
import java.util.List;

import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Builder
public class EventDTO extends ResultDTO {
    public String id;

    public String name;

    public List<String> images;

    public int slots;

    public Date date;

    public String organizerId;

    public String permissionId;

    public List<String> categories;

    public List<String> comments;

    public String locationId;

    public String contactId;
}
