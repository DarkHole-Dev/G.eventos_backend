package com.darkhole.gerenteEventos.shared.dto.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationResponseDTO {
    
    private String id;

    private String document;

    private Instant approvedAt;

    private String approvedId;

    private boolean approved;
}
