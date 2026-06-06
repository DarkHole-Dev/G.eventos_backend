package com.darkhole.gerenteEventos.location;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.darkhole.gerenteEventos.location.dto.LocationDTO;
import com.darkhole.gerenteEventos.shared.database.entity.LocationEntity;
import com.darkhole.gerenteEventos.shared.database.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public String createLocation(String country, String state, String city, String neighborhood, String street, String number, String cep) {
        return locationRepository.save(LocationEntity.builder()
            .country(country)
            .state(state)
            .city(city)
            .neighborhood(neighborhood)
            .street(street)
            .number(number)
            .cep(cep)
            .build()
        ).getId();    
    }

    public boolean deleteLocation(String id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<LocationDTO> getLocation(String id) {
        return locationRepository.findById(id).map(locationEntity -> LocationDTO.builder()
            .country(locationEntity.getCountry())
            .state(locationEntity.getState())
            .city(locationEntity.getCity())
            .neighborhood(locationEntity.getNeighborhood())
            .street(locationEntity.getStreet())
            .number(locationEntity.getNumber())
            .cep(locationEntity.getCep())
            .build()
        );
    }

    public boolean updateLocation(String id, String field, String value) {
        LocationEntity locationEntity = locationRepository.findById(id).orElse(null);
        
        if (locationEntity == null) return false;

        switch (field) {
            case "country":
                locationEntity.setCountry(value);
                break;
            case "state":
                locationEntity.setState(value);
                break;
            case "city":
                locationEntity.setCity(value);
                break;
            case "neighborhood":
                locationEntity.setNeighborhood(value);
                break;
            case "street":
                locationEntity.setStreet(value);
                break;
            case "number":
                locationEntity.setNumber(value);
                break;
            case "cep":
                locationEntity.setCep(value);
                break;
            default:
                return false;
        }
        locationRepository.save(locationEntity);
        return true;
    }
}
