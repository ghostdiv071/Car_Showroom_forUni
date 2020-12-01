package com.minakov.entity.color;

import com.minakov.entity.Entity;

public class Color extends Entity {

    private String name;

    public Color() {
    }

    public Color(String name) {
        this.name = name;
    }

    public Color(int id, String name) {
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
        return "Color{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
