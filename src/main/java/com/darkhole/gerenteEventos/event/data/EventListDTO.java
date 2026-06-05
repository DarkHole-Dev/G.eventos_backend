package com.darkhole.gerenteEventos.apps.event.data;

import org.springframework.http.ResponseEntity;

import com.darkhole.gerenteEventos.data.ResultConstDTO;
import com.darkhole.gerenteEventos.data.ResultDTO;

public class EventListDTO extends ResultConstDTO {
    public static ResponseEntity<ResultDTO> EMPTY() {
        return ResponseEntity.ok(new EventListDTO());
    }
}
