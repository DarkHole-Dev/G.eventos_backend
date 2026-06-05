package com.darkhole.gerenteEventos.shared.dto;

public class ResultSucessDTO extends ResultDTO {
    public String message;

    public ResultSucessDTO(String message) {
        this.message = message;
    }
}