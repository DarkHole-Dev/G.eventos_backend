package com.darkhole.gerenteEventos.shared.database.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.darkhole.gerenteEventos.shared.database.entity.CommentEntity;
import com.darkhole.gerenteEventos.shared.database.entity.EventEntity;
import com.darkhole.gerenteEventos.shared.database.repository.CommentRepository;
import com.darkhole.gerenteEventos.shared.database.repository.EventRepository;
import com.darkhole.gerenteEventos.shared.dto.request.CommentaryRequestDTO;
import com.darkhole.gerenteEventos.shared.dto.request.CommentaryUpdateRequestDTO;

@Service
public class CommentaryService {
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private EventRepository eventRepository;

    public CommentEntity create(CommentaryRequestDTO dto) {

        System.out.println("Total de eventos no banco: " + eventRepository.count());

        EventEntity event = eventRepository
        .findById(dto.getEventId())
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        CommentEntity comment = CommentEntity.builder()
            .userId(dto.getUserId())
            .description(dto.getDescription())
            .build();

        CommentEntity saveEntity = commentRepository.save(comment);


        if(event.getComments() == null) {
            event.setComments(new ArrayList<>());

        }
        event.getComments().add(saveEntity.getId());


        eventRepository.save(event);

        return saveEntity;
    }
    public CommentEntity update(String commentId, CommentaryUpdateRequestDTO dto) {

        CommentEntity comment = commentRepository
        .findById(commentId)
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!comment.getUserId().equals(dto.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        comment.setDescription(dto.getDescription());

        return commentRepository.save(comment);
    }
    public void delete(String commentId, String userId) {

        CommentEntity comment = commentRepository
        .findById(commentId)
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!comment.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        EventEntity event = eventRepository
        .findAll()
        .stream()

        .filter(c -> c.getComments() != null && c.getComments().contains(commentId))

        .findFirst()

        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        event.getComments().remove(commentId);

        eventRepository.save(event);

        commentRepository.deleteById(commentId);

        
    }

}
