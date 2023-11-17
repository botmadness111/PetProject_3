package com.example.ru.repositories;

import com.example.ru.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Sensor findByName(String sensorname);
}
