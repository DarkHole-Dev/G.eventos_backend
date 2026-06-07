package com.darkhole.gerenteEventos.shared.dto.response;

import java.util.List;

import com.darkhole.gerenteEventos.shared.database.entity.CategoryEntity;
import com.darkhole.gerenteEventos.shared.database.entity.CommentEntity;
import com.darkhole.gerenteEventos.shared.dto.ResultDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Builder
public class FindAllIdsDTO extends ResultDTO {
    public List<CategoryEntity> categories;

    public List<CommentEntity> comments;
}
