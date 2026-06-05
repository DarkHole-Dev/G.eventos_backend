package com.darkhole.gerenteEventos.shared.dto;

import org.springframework.http.ResponseEntity;
import com.darkhole.gerenteEventos.shared.dto.ResultDTO;
import com.darkhole.gerenteEventos.shared.dto.ResultErrorDTO;

public class DTO {
    public static ResponseEntity<ResultDTO> SUCCESS = ResponseEntity
        .ok(new ResultSucessDTO("Success"));
    public static ResponseEntity<ResultDTO> NOT_IMPLEMENTED = ResponseEntity
        .status(501)
        .body(new ResultErrorDTO("Not implemented")); 
}
