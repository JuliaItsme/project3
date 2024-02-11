package ru.goryacheva.project.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.goryacheva.project.model.Measurement;
import ru.goryacheva.project.service.SensorService;

@Component
public class MeasurementValidation implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidation(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        if (measurement.getSensor() == null)
            return;

        if (sensorService.get(measurement.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor", "There is no registered sensor with this name!");
    }
}