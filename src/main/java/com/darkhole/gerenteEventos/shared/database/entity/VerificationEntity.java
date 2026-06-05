package com.darkhole.gerenteEventos.shared.database.entity;

import java.time.Instant;

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
@Document(collection = "verifications")
public class VerificationEntity {
    @Id
    private String id;

    private String document;

    private Instant approvedAt;

    private String approverId;
}
