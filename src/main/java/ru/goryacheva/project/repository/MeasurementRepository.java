package ru.goryacheva.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.goryacheva.project.model.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
