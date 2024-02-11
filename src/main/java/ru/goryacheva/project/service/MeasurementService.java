package ru.goryacheva.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.goryacheva.project.model.Measurement;
import ru.goryacheva.project.repository.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository,
                              SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    @Transactional
    public void add(Measurement measurement){
        enrich(measurement);
        measurementRepository.save(measurement);
    }

    public List<Measurement> getAll(){
        return measurementRepository.findAll();
    }

    private void enrich(Measurement measurement) {
        measurement.setSensor(sensorService.get(measurement.getSensor().getName()).get());
        measurement.setCurrentTime(LocalDateTime.now());
    }
}
