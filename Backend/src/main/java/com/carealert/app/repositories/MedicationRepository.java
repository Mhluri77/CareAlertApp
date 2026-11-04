
package com.carealert.app.repositories;

import com.carealert.app.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    List<Medication> findByUser_UserId(Long userId);

    // Retrieve all medications for automatic reminders
    List<Medication> findAll();
}
