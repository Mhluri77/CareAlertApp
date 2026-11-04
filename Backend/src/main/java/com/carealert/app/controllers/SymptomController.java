package com.carealert.app.controllers;

import com.carealert.app.entities.Symptom;
import com.carealert.app.services.SymptomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carealert/symptoms")
public class SymptomController {
    private final SymptomService symptomService;

    public SymptomController(SymptomService symptomService) {
        this.symptomService = symptomService;
    }

    @GetMapping
    public List<Symptom> getAllSymptoms() {
        return symptomService.getAllSymptoms();
    }

    @GetMapping("/user/{userId}")
    public List<Symptom> getSymptomsByUser(@PathVariable Long userId) {
        return symptomService.getSymptomsByUser(userId);
    }

    @PostMapping
    public Symptom createSymptom(@RequestBody Symptom symptom) {
        return symptomService.saveSymptom(symptom);
    }

    @DeleteMapping("/{id}")
    public void deleteSymptom(@PathVariable Long id) {
        symptomService.deleteSymptom(id);
    }
}
