package com.darkhole.gerenteEventos.organizer.dto;

import com.darkhole.gerenteEventos.contact.dto.ContactDTO;
import com.darkhole.gerenteEventos.location.dto.LocationDTO;
import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrganizerDTO extends ResultDTO {
    public String imageUrl;

    public String name;
    public String cnpj;
    public LocationDTO location;
    public ContactDTO contact;
}
