package com.darkhole.gerenteEventos.shared.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darkhole.gerenteEventos.shared.database.service.LookupService;
import com.darkhole.gerenteEventos.shared.dto.ResultDTO;
import com.darkhole.gerenteEventos.shared.dto.request.FindAllIdsRequestDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/shared")
@RequiredArgsConstructor
public class LookupController {
    private final LookupService lookupService;

    @PostMapping("/findAllIds")
    public ResponseEntity<ResultDTO> findAllIds(@RequestBody FindAllIdsRequestDTO dto) {
        if (dto == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(lookupService.findAllIds(dto));
    }
}
