package com.darkhole.gerenteEventos.shared.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentaryUpdateRequestDTO {
    
    private String userId;

    private String description;
}
