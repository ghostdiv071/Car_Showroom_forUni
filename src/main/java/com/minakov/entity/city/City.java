package com.minakov.entity.city;

import com.minakov.entity.Entity;

public class City extends Entity {

    private String name;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public City(int id, String name) {
        setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
