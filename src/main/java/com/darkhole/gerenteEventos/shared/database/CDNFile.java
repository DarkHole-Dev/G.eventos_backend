package com.darkhole.gerenteEventos.shared.database;

import java.io.InputStream;

import lombok.Builder;
import lombok.Getter;


@Builder
public class CDNFile {
    @Getter
    private String fileName;

    @Getter
    private String contentType;

    @Getter
    private InputStream file;
}
