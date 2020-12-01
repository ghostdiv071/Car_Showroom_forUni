package com.minakov.entity.brand;

import com.minakov.entity.Entity;

public class Brand extends Entity {

    private String name;

    public Brand() {
    }

    public Brand(String name) {
        this.name = name;
    }

    public Brand(int id, String name) {
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
        return "Brand{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
