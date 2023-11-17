package com.example.ruClient.DTO;

import jakarta.validation.constraints.Size;

public class SensorDTO {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
