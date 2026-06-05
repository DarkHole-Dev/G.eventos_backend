package com.darkhole.gerenteEventos.shared.model;

public class Location {
    private String country;

    private String state;

    private String city;

    private String neighborhood;

    private String street;

    private String number;

    private String cep;

    public Location(String country, String state, String city, String neighborhood, String street, String number, String cep) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.cep = cep;
        this.number = number;
        this.street = street;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getNeighborhood() {
        return neighborhood;
    }
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

}
