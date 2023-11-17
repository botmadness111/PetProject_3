package com.example.ru.DTO;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementsDTO {
    @Min(value = -100, message = "value should be grate than -100")
    @Max(value = 100, message = "value should be less than -100")
    private Double value;

    @NotNull(message = "raining should be not Null")
    private Boolean raining;

    @NotNull(message = "sensorName should be not Null")
    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "MeasurementsDTO{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensorName='" + sensor.getName() + '\'' +
                '}';
    }
}
