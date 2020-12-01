package com.minakov.entity.model;

import com.minakov.entity.Entity;

public class Model extends Entity {

    private String name;
    private String brand;

    public Model() {
    }

    public Model(String name, String brand) {
        this.name = name;
        this.brand = brand;
    }

    public Model(int id, String name, String  brand) {
        setId(id);
        this.name = name;
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
