package com.darkhole.gerenteEventos.shared.database.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "events")
public class EventEntity {
    @Id
    private String id;

    private String name;

    private List<String> images;

    private int slots;

    private Date date;

    private String organizerId;

    private String permissionId;

    private List<String> categories;

    private List<String> comments;

    private String locationId;

    private String contactId;
}
