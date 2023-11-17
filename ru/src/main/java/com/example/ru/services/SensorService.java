package com.example.ru.services;

import com.example.ru.DTO.SensorDTO;
import com.example.ru.models.Sensor;
import com.example.ru.repositories.SensorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ru.util.SensorNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> getSensosrs() {
        List<Sensor> sensors = sensorRepository.findAll();
        return sensors;
    }

    public Sensor getSensor(int id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        return sensor.orElseThrow(SensorNotFoundException::new);
    }

    public Sensor getSensorByName(String name) {
        Optional<Sensor> sensor = Optional.ofNullable(sensorRepository.findByName(name));
        return sensor.orElse(null);
    }

    @Transactional(readOnly = false)
    public void saveSensor(Sensor sensor) {
        enrichSensor(sensor);
        System.out.println(sensor);
        sensorRepository.save(sensor);
    }

    private void enrichSensor(Sensor sensor) {
        sensor.setCreated_who("Admin");
        sensor.setCreated_at(LocalDateTime.now());
    }
}
