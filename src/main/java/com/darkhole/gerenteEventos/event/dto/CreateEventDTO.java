package com.darkhole.gerenteEventos.event.dto;

import java.util.Date;
import java.util.List;

public class CreateEventDTO {
    public String name;

    public List<String> images;

    public Integer slots;

    public Date date;

    public String organizerId;

    public String permissionId;

    public List<String> categories;

    public List<String> comments;

    public String locationId;

    public String contactId;
}
