package com.darkhole.gerenteEventos.shared.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darkhole.gerenteEventos.shared.database.entity.CategoryEntity;
import com.darkhole.gerenteEventos.shared.database.service.CategoryService;
import com.darkhole.gerenteEventos.shared.dto.request.CategoryRequestDTO;
import com.darkhole.gerenteEventos.shared.dto.request.CategoryRequestUpdateDTO;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryEntity> create(@RequestBody CategoryRequestDTO dto) {
        CategoryEntity category = categoryService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> getId (@PathVariable("id") String id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryEntity>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryEntity> update(@PathVariable String id, @RequestBody CategoryRequestUpdateDTO dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        categoryService.delete(id);
       return ResponseEntity.noContent().build();
    }
    }

