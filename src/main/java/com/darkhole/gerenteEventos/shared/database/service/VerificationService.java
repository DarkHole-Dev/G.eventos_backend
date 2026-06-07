package com.darkhole.gerenteEventos.shared.database.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.darkhole.gerenteEventos.shared.database.entity.VerificationEntity;
import com.darkhole.gerenteEventos.shared.database.repository.VerificationRepository;
import com.darkhole.gerenteEventos.shared.dto.request.VerificationUpdateRequestDTO;
import com.darkhole.gerenteEventos.shared.dto.response.VerificationDTO;

@Service
public class VerificationService {
    
    @Autowired
    private VerificationRepository verificationRepository;

    public VerificationDTO getVerification(String id) {

        VerificationEntity verification = verificationRepository
        .findById(id)
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

       return VerificationDTO.builder()
        .id(verification.getId())
        .document(verification.getDocument())
        .approvedAt(verification.getApprovedAt())
        .approvedId(verification.getApproverId())
        .approved(verification.getApprovedAt() != null)
        .build();
        
    }
    public VerificationDTO update (String id, VerificationUpdateRequestDTO dto) {
        VerificationEntity verification = verificationRepository.findById(id)
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(dto.getDocument() != null) {
            verification.setDocument(dto.getDocument());
        }
        if(dto.getApprovedId() != null) {
            verification.setApproverId(dto.getApprovedId());
        }
        if(dto.getApprovedAt() != null) {
            verification.setApprovedAt(dto.getApprovedAt());
        }

        VerificationEntity update = verificationRepository.save(verification);

        return VerificationDTO.builder()
        .id(update.getId())
        .document(update.getDocument())
        .approvedId(update.getApproverId())
        .approvedAt(update.getApprovedAt())
        .approved(update.getApprovedAt() != null)
        .build();
    }
}
