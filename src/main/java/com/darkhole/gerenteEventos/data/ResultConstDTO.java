package com.darkhole.gerenteEventos.data.core;

import org.springframework.http.ResponseEntity;

public class ResultConstDTO extends ResultDTO {
    public static ResponseEntity<ResultDTO> SUCCESS = ResponseEntity
        .ok(new ResultSucessDTO("Success"));
    public static ResponseEntity<ResultDTO> NOT_IMPLEMENTED = ResponseEntity
        .status(501)
        .body(new ResultErrorDTO("Not implemented")); 
}
