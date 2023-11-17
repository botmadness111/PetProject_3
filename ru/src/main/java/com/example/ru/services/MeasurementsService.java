package com.example.ru.services;

import com.example.ru.models.Measurements;
import com.example.ru.models.Sensor;
import com.example.ru.repositories.MeasurementsRepositories;
import com.example.ru.repositories.SensorRepository;
import com.example.ru.util.MeasurementsNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private MeasurementsRepositories measurementsRepository;
    private SensorService sensorService;

    @Autowired
    public MeasurementsService(MeasurementsRepositories measurementsRepository, SensorService sensorService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorService = sensorService;
    }

    public List<Measurements> getMeasurements() {
        List<Measurements> measurements = measurementsRepository.findAll();
        return measurements;
    }

    public Measurements getMeasurement(int id) {
        Optional<Measurements> measurement = measurementsRepository.findById(id);
        return measurement.orElseThrow(MeasurementsNotFoundException::new);
    }

    @Transactional(readOnly = false)
    public void saveMeasurements(Measurements measurements) {
        enrichMeasurements(measurements);
        measurementsRepository.save(measurements);

    }

    private void enrichMeasurements(Measurements measurements) {
        measurements.setCreated_at(LocalDateTime.now());
        measurements.setSensor(sensorService.getSensorByName(measurements.getSensor().getName()));
    }
}
