package com.carealert.app.repositories;

import com.carealert.app.entities.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    List<Symptom> findByUser_UserId(Long userId);
}
