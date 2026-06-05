package com.darkhole.gerenteEventos.data;

public class ResultErrorDTO extends ResultDTO {
    public String error;

    public ResultErrorDTO(String error) {
        this.error = error;
    }
}
