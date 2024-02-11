package ru.goryacheva.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.goryacheva.project.model.Sensor;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}
