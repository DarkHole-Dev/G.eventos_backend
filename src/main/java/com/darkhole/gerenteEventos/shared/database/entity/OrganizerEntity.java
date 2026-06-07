package com.darkhole.gerenteEventos.shared.database.entity;

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
@Document(collection = "organizers")
public class OrganizerEntity {
    @Id
    private String id;

    private String imageUrl;

    private String name;

    private String cnpj;

    private String password;

    private String contactId;

    private String locationId;
}
