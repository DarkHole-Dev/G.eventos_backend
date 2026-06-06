package com.darkhole.gerenteEventos.shared.dto;

import org.springframework.http.ResponseEntity;

public class DTO {
    public static ResponseEntity<ResultDTO> SUCCESS() {
        return ResponseEntity.ok(new ResultSucessDTO("Success"));
    }



    public static ResponseEntity<ResultDTO> UNAUTHORIZED(String message) {
        return ResponseEntity
            .status(401)
            .body(new ResultErrorDTO(message));
    }



    public static ResponseEntity<ResultDTO> NOT_IMPLEMENTED(String message) {
        return ResponseEntity
            .status(501)
            .body(new ResultErrorDTO(message));
    }
    


    public static ResponseEntity<ResultDTO> BAD_REQUEST(String message) {
        return ResponseEntity
            .badRequest()
            .body(new ResultErrorDTO(message));
    }
}
