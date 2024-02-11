package ru.goryacheva.project.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.goryacheva.project.model.Sensor;
import ru.goryacheva.project.repository.SensorRepository;
import ru.goryacheva.project.service.SensorService;

@Component
public class SensorValidation implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorValidation(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        if (sensorService.get(sensor.getName()).isPresent()){
            errors.rejectValue("name", "There is already a sensor with the same name!");
        }
    }
}