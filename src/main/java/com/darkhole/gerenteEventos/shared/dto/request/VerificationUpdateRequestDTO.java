package com.darkhole.gerenteEventos.shared.dto.request;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationUpdateRequestDTO {
    
    private String document;

    private Instant approvedAt;

    private String approvedId;
}
