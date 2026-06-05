package com.darkhole.gerenteEventos.shared.model;

public class Contact {
    
    private String phone;

    private String email;

    public Contact(String phone, String email) {
        this.email = email;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
