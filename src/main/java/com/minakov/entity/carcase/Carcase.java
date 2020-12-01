package com.minakov.entity.carcase;

import com.minakov.entity.Entity;

public class Carcase extends Entity {

    private String name;

    public Carcase() {
    }

    public Carcase(String name) {
        this.name = name;
    }

    public Carcase(int id, String name) {
        setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Carcase{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
