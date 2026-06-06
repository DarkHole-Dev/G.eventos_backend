package com.darkhole.gerenteEventos.shared.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.darkhole.gerenteEventos.shared.database.entity.CommentEntity;
import com.darkhole.gerenteEventos.shared.database.service.CommentaryService;
import com.darkhole.gerenteEventos.shared.dto.request.CommentaryRequestDTO;
import com.darkhole.gerenteEventos.shared.dto.request.CommentaryUpdateRequestDTO;

@RestController
@RequestMapping("/v1/comment")
public class CommentController {
    
    @Autowired
    private CommentaryService commentaryService;

    @PostMapping()
    public ResponseEntity<CommentEntity> create (@RequestBody CommentaryRequestDTO dto) {

        CommentEntity comment = commentaryService.create(dto);

        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CommentEntity> update(@PathVariable String id, @RequestBody CommentaryUpdateRequestDTO dto) {
        CommentEntity comment = commentaryService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, @RequestParam String userId) {
        commentaryService.delete(id, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } 
}
