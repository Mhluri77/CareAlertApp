package com.carealert.app.repositories;

import com.carealert.app.entities.Dose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoseRepository extends JpaRepository<Dose, Long> {
    List<Dose> findByMedication_MedicationId(Long medicationId);
}
