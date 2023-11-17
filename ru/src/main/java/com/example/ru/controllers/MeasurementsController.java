package com.example.ru.controllers;

import com.example.ru.DTO.MeasurementsDTO;
import com.example.ru.DTO.MeasurementsResponse;
import com.example.ru.DTO.SensorDTO;
import com.example.ru.models.Measurements;
import com.example.ru.models.Sensor;
import com.example.ru.services.MeasurementsService;
import com.example.ru.services.SensorService;
import com.example.ru.util.MeasurementsErrorResponse;
import com.example.ru.util.MeasurementsNotCreatedException;
import com.example.ru.util.MeasurementsNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {


    private MeasurementsService measurementsService;
    private SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, SensorService sensorService) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
    }


    @GetMapping()
    // Обычно список из элементов оборачивается в один объект для пересылки
    public MeasurementsResponse getMeasurements() {
        return new MeasurementsResponse(measurementsService.getMeasurements().stream().map(this::convertMeasurementsToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public MeasurementsDTO getMeasurement(@PathVariable int id) {
        return convertMeasurementsToDto(measurementsService.getMeasurement(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> getMeasurements(@RequestBody @Valid MeasurementsDTO measurementsDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField()).
                        append(" - ").
                        append(error.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementsNotCreatedException(errorMessage.toString());
        }
        System.out.println(measurementsDTO.getSensor().getName());
        if (sensorService.getSensorByName(measurementsDTO.getSensor().getName()) == null) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Не удалось найти такой сенсор");
            throw new MeasurementsNotCreatedException(errorMessage.toString());
        }


        measurementsService.saveMeasurements(convertDtoToMeasurements(measurementsDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Measurements convertDtoToMeasurements(MeasurementsDTO measurementsDTO) {
        return modelMapper.map(measurementsDTO, Measurements.class);
    }

    private MeasurementsDTO convertMeasurementsToDto(Measurements measurements) {
        return modelMapper.map(measurements, MeasurementsDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementsErrorResponse> handleException(MeasurementsNotCreatedException e) {
        MeasurementsErrorResponse response = new MeasurementsErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementsErrorResponse> handleExceprion(MeasurementsNotFoundException e) {
        MeasurementsErrorResponse response = new MeasurementsErrorResponse("Measurements should be not found", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
