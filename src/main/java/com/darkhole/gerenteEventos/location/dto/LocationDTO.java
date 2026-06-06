package com.darkhole.gerenteEventos.location.dto;

import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.Builder;

@Builder
public class LocationDTO extends ResultDTO {
    public String country;
    public String state;
    public String city;
    public String neighborhood;
    public String street;
    public String number;
    public String cep;
}
