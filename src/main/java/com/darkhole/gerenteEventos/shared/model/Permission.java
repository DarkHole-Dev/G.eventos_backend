package com.darkhole.gerenteEventos.shared.model;

public class Permission {
    
    private String name;

    private Verification verification;

    public Permission(String name, Verification verification){
        this.name = name;
        this.verification = verification;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Verification getVerification() {
        return verification;
    }
    public void setVerification(Verification verification) {
        this.verification = verification;
    }

}
