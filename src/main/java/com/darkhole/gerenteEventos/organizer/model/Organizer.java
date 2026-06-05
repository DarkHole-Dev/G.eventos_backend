package com.darkhole.gerenteEventos.organizer.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.darkhole.gerenteEventos.shared.model.Contact;
import com.darkhole.gerenteEventos.shared.model.Location;

@Document(collection = "organizer")
public class Organizer {
    
    private String name;

    private String cnpj;

    private Contact contact;

    private Location location;

    public Organizer(String name, String cnpj, Contact contact, Location location) {
        this.name = name;
        this.cnpj = cnpj;
        this.contact = contact;
        this.location = location;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public Contact geContact() {
        return contact;
    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
}
