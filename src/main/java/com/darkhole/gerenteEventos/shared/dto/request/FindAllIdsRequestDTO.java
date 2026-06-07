package com.darkhole.gerenteEventos.shared.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAllIdsRequestDTO {
    private List<String> categoryIds;

    private List<String> commentIds;
}
