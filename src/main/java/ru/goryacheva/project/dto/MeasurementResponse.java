package ru.goryacheva.project.dto;

import java.util.List;

public class MeasurementResponse {
    private List<MeasurementDto> measurementsDto;

    public MeasurementResponse(List<MeasurementDto> measurementsDto) {
        this.measurementsDto = measurementsDto;
    }

    public List<MeasurementDto> getMeasurementsDto() {
        return measurementsDto;
    }

    public void setMeasurementsDto(List<MeasurementDto> measurementsDto) {
        this.measurementsDto = measurementsDto;
    }
}


