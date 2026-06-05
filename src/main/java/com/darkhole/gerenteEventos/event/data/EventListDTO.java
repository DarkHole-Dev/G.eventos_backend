package com.darkhole.gerenteEventos.event.data;

import org.springframework.http.ResponseEntity;

import com.darkhole.gerenteEventos.shared.dto.ResultConstDTO;
import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

public class EventListDTO extends ResultConstDTO {
    public static ResponseEntity<ResultDTO> EMPTY() {
        return ResponseEntity.ok(new EventListDTO());
    }
}
