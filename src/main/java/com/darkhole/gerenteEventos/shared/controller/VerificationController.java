package com.darkhole.gerenteEventos.shared.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.darkhole.gerenteEventos.shared.database.service.VerificationService;
import com.darkhole.gerenteEventos.shared.dto.request.VerificationUpdateRequestDTO;
import com.darkhole.gerenteEventos.shared.dto.response.VerificationResponseDTO;

@RestController
@RequestMapping("/v1/verification")
public class VerificationController {
    
    @Autowired
    private VerificationService verificationService;

    @GetMapping("/{id}")
    public ResponseEntity<VerificationResponseDTO> getVerification(@PathVariable("id") String id) {
        VerificationResponseDTO response = verificationService.getVerification(id);

         return ResponseEntity.status(HttpStatus.OK).body(response);


    }
    @PutMapping("/{id}")
    public ResponseEntity<VerificationResponseDTO> update (@PathVariable("id") String id, @RequestBody VerificationUpdateRequestDTO dto) {
        VerificationResponseDTO response = verificationService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
