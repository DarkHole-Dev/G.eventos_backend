package com.darkhole.gerenteEventos.shared.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestUpdateDTO {
    
    private String name;
    private String imageUrl; 
}
