package com.carealert.app.controllers;

import com.carealert.app.entities.Dose;
import com.carealert.app.services.DoseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carealert/doses")
public class DoseController {
    private final DoseService doseService;

    public DoseController(DoseService doseService) {
        this.doseService = doseService;
    }

    @GetMapping
    public List<Dose> getAllDoses() {
        return doseService.getAllDoses();
    }

    @GetMapping("/medication/{medicationId}")
    public List<Dose> getDosesByMedication(@PathVariable Long medicationId) {
        return doseService.getDosesByMedication(medicationId);
    }

    @PostMapping
    public Dose createDose(@RequestBody Dose dose) {
        return doseService.saveDose(dose);
    }

    @DeleteMapping("/{id}")
    public void deleteDose(@PathVariable Long id) {
        doseService.deleteDose(id);
    }
}
