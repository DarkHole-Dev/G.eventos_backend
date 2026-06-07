package com.darkhole.gerenteEventos.shared.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.darkhole.gerenteEventos.shared.database.entity.CategoryEntity;
import com.darkhole.gerenteEventos.shared.database.repository.CategoryRepository;
import com.darkhole.gerenteEventos.shared.dto.request.CategoryRequestDTO;
import com.darkhole.gerenteEventos.shared.dto.request.CategoryRequestUpdateDTO;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity create(CategoryRequestDTO dto) {
        CategoryEntity category = CategoryEntity.builder()
        .name(dto.getName())
        .imageUrl(dto.getImageUrl())
        .build();

        return categoryRepository.save(category);
    }

    public CategoryEntity findById(String id) {

        return categoryRepository
        .findById(id)
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
    }
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }
    public CategoryEntity update(String id, CategoryRequestUpdateDTO dto) {
        CategoryEntity category = categoryRepository
        .findById(id)
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(dto.getName() != null) {
            category.setName(dto.getName());
        }
        if(dto.getImageUrl() != null) {
            category.setImageUrl(dto.getImageUrl());
        }

        return categoryRepository.save(category);
    }
    public void delete (String id) {
        categoryRepository.findById(id)
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        categoryRepository.deleteById(id);
    }
} 
