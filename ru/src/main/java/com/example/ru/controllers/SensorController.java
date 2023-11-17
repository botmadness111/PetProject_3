package com.example.ru.controllers;

import com.example.ru.DTO.SensorDTO;
import com.example.ru.models.Sensor;
import com.example.ru.services.SensorService;
import com.example.ru.util.SensorNotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.example.ru.util.SensorErrorResponse;
import com.example.ru.util.SensorNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public List<SensorDTO> getSensors() {
        return sensorService.getSensosrs().stream().map(this::convertSensorToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SensorDTO getSensors(@PathVariable("id") int id) {
        return convertSensorToDto(sensorService.getSensor(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessage.append(error.getField()).
                        append(" - ").
                        append(error.getDefaultMessage()).
                        append(";");
            }

            throw new SensorNotCreatedException(errorMessage.toString());
        }


        sensorService.saveSensor(convertDtoToSensor(sensorDTO));
        //Отправляем HTTP ответ с пустным телом и со статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse("Sensor wasnt found", System.currentTimeMillis());
        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertDtoToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertSensorToDto(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }


}
