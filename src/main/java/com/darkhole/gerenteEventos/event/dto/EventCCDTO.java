package com.darkhole.gerenteEventos.event.dto;

import java.util.List;

import com.darkhole.gerenteEventos.shared.database.entity.CategoryEntity;
import com.darkhole.gerenteEventos.shared.database.entity.CommentEntity;
import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.Builder;


@Builder
public class EventCCDTO extends ResultDTO {
    public List<CategoryEntity> categories;
    public List<CommentEntity> comments;
}
