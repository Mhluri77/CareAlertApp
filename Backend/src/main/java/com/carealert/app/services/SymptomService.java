package com.carealert.app.services;

import com.carealert.app.entities.Symptom;
import com.carealert.app.repositories.SymptomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymptomService {
    private final SymptomRepository symptomRepository;

    public SymptomService(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }

    public List<Symptom> getAllSymptoms() {
        return symptomRepository.findAll();
    }

    public List<Symptom> getSymptomsByUser(Long userId) {
        return symptomRepository.findByUser_UserId(userId);
    }

    public Symptom saveSymptom(Symptom symptom) {
        return symptomRepository.save(symptom);
    }

    public void deleteSymptom(Long id) {
        symptomRepository.deleteById(id);
    }
}
