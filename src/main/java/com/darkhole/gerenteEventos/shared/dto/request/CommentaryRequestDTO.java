package com.darkhole.gerenteEventos.shared.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentaryRequestDTO {
     
   private String eventId;

   private String userId;

   private String description;
}
