package com.minakov.entity.carShowroom;

import com.minakov.entity.Entity;

public class CarShowroom extends Entity {

    private String street;
    private int houseNumber;
    private String city;

    public CarShowroom() {
    }

    public CarShowroom(String street, int houseNumber, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
    }

    public CarShowroom(int id, String street, int houseNumber, String city) {
        setId(id);
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
