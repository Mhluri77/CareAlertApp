package com.carealert.app.services;

import com.carealert.app.entities.Dose;
import com.carealert.app.repositories.DoseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoseService {
    private final DoseRepository doseRepository;

    public DoseService(DoseRepository doseRepository) {
        this.doseRepository = doseRepository;
    }

    public List<Dose> getAllDoses() {
        return doseRepository.findAll();
    }

    public List<Dose> getDosesByMedication(Long medicationId) {
        return doseRepository.findByMedication_MedicationId(medicationId);
    }

    public Dose saveDose(Dose dose) {
        return doseRepository.save(dose);
    }

    public void deleteDose(Long id) {
        doseRepository.deleteById(id);
    }
}
