package com.darkhole.gerenteEventos.contact;

import org.springframework.stereotype.Service;

import com.darkhole.gerenteEventos.shared.database.entity.ContactEntity;
import com.darkhole.gerenteEventos.shared.database.repository.ContactRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;


    public String createContact(String email, String phone) {
        return contactRepository.save(ContactEntity.builder()
            .email(email)
            .phone(phone)
            .build()
        ).getId();    
    }

    public boolean deleteContact(String id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean updateContact(String id, String field, String value) {
        ContactEntity contactEntity = contactRepository.findById(id).orElse(null);
        
        if (contactEntity == null) return false;

        switch (field) {
            case "email":
                contactEntity.setEmail(value);
                break;
            case "phone":
                contactEntity.setPhone(value);
                break;
            default:
                return false;
        }
        contactRepository.save(contactEntity);
        return true;
    }

    public ContactEntity getContact(String id) {
        return contactRepository.findById(id).orElse(null);
    }
}
