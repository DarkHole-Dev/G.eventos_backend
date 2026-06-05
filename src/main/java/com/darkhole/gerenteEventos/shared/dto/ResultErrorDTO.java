package com.darkhole.gerenteEventos.shared.dto;

public class ResultErrorDTO extends ResultDTO {
    public String error;

    public ResultErrorDTO(String error) {
        this.error = error;
    }
}
