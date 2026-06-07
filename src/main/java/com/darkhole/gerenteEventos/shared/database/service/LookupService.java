package com.darkhole.gerenteEventos.shared.database.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.darkhole.gerenteEventos.shared.database.entity.CategoryEntity;
import com.darkhole.gerenteEventos.shared.database.entity.CommentEntity;
import com.darkhole.gerenteEventos.shared.database.repository.CategoryRepository;
import com.darkhole.gerenteEventos.shared.database.repository.CommentRepository;
import com.darkhole.gerenteEventos.shared.dto.request.FindAllIdsRequestDTO;
import com.darkhole.gerenteEventos.shared.dto.response.FindAllIdsDTO;

@Service
public class LookupService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommentRepository commentRepository;

    public FindAllIdsDTO findAllIds(FindAllIdsRequestDTO dto) {
        List<CategoryEntity> categories = dto.getCategoryIds() == null
            ? new ArrayList<>()
            : categoryRepository.findAllById(dto.getCategoryIds());

        List<CommentEntity> comments = dto.getCommentIds() == null
            ? new ArrayList<>()
            : commentRepository.findAllById(dto.getCommentIds());

        return FindAllIdsDTO.builder()
            .categories(categories)
            .comments(comments)
            .build();
    }
}