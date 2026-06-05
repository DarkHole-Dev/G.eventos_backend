package com.darkhole.gerenteEventos.shared.model;

import java.time.LocalDateTime;


import com.darkhole.gerenteEventos.organizer.model.Organizer;

public class Verification {
    private String document;

    private boolean approved;

    private LocalDateTime approvedAt;

    private Organizer owner;

    public Verification(String document, boolean approved, LocalDateTime approvedAt, Organizer owner) {
        this.approved = approved;
        this.approvedAt = approvedAt;
        this.document = document;
        this.owner = owner;

    }
    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document;
    }
    public boolean getApproved() {
        return approved;
    }
    public void setApproved(boolean approved){
        this.approved = approved;
    }
    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }
    public void setpprovedAt(LocalDateTime approvedAt){
        this.approvedAt = approvedAt;
    }
    public Organizer getOwner(){
        return owner;
    } 
    public void setOwner(Organizer owner){
        this.owner = owner;
    }


}
