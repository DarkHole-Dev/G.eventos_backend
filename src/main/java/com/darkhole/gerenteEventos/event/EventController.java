package com.darkhole.gerenteEventos.event;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darkhole.gerenteEventos.data.ResultConstDTO;
import com.darkhole.gerenteEventos.data.ResultDTO;
import com.darkhole.gerenteEventos.data.TokenDTO;
import com.darkhole.gerenteEventos.event.data.EventListDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    @GetMapping("/all")
    public ResponseEntity<ResultDTO> getAll() {
        return EventListDTO.NOT_IMPLEMENTED;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResultDTO> update(@RequestParam String id, @RequestBody String entity) {
        return TokenDTO.NOT_IMPLEMENTED;
    }

    @PostMapping("/create")
    public ResponseEntity<ResultDTO> create(@RequestBody String entity) {
        return TokenDTO.NOT_IMPLEMENTED;
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResultDTO> delete(@RequestParam String id) {
        return ResultConstDTO.NOT_IMPLEMENTED;
    }
    
}
