package com.example.ru.repositories;

import com.example.ru.models.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementsRepositories extends JpaRepository<Measurements, Integer> {
}
