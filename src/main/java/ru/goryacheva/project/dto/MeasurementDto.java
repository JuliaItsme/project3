package ru.goryacheva.project.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class MeasurementDto {

    @NotNull
    @Range(min = -100, max = 100, message = "Temperature should be greater than -100 and less than 100")
    private Double temperature;

    @NotNull
    private Boolean raining;

    @NotNull
    private SensorDto sensorDto;

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDto getSensorDto() {
        return sensorDto;
    }

    public void setSensorDto(SensorDto sensorDto) {
        this.sensorDto = sensorDto;
    }
}
