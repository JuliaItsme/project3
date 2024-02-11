package ru.goryacheva.project.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.goryacheva.project.dto.SensorDto;
import ru.goryacheva.project.model.Sensor;
import ru.goryacheva.project.service.SensorService;
import ru.goryacheva.project.utils.MeasurementErrorResponse;
import ru.goryacheva.project.utils.MeasurementException;
import ru.goryacheva.project.utils.SensorValidation;

import javax.validation.Valid;

import static ru.goryacheva.project.utils.ErrorUtils.errorsTo;

@RestController
@RequestMapping("/sensors")
public class SensorRestController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    private final SensorValidation sensorValidation;

    @Autowired
    public SensorRestController(SensorService sensorService,
                                ModelMapper modelMapper,
                                SensorValidation sensorValidation) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidation = sensorValidation;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDto sensorDto,
                                                                        BindingResult error){
        Sensor sensor = mappingDtoToModel(sensorDto);
        sensorValidation.validate(sensor, error);
        if (error.hasErrors())
            errorsTo(error);
        sensorService.register(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handlerException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor mappingDtoToModel(SensorDto sensorDto){
        return modelMapper.map(sensorDto, Sensor.class);
    }
}
