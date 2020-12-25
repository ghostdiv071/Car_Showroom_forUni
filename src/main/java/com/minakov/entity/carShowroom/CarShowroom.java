package com.minakov.entity.carShowroom;

import com.minakov.entity.Entity;

public class CarShowroom extends Entity {

    private String street;
    private short houseNumber;
    private String city;

    public CarShowroom() {
    }

    public CarShowroom(String street, short houseNumber, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
    }

    public CarShowroom(int id, String street, short houseNumber, String city) {
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

    public short getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(short houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
