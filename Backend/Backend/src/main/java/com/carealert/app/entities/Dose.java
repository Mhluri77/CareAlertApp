package com.carealert.app.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "medication_doses")
public class Dose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dose_id")
    private Long doseId;

    @ManyToOne
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @Column(name = "scheduled_date")
    private LocalDate scheduledDate;

    @Column(name = "scheduled_time")
    private LocalTime scheduledTime;

    @Column(name = "actual_taken_time")
    private LocalDateTime actualTakenTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "dose_status")
    private DoseStatus doseStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status")
    private SyncStatus syncStatus;

    // Getters and setters
    public Long getDoseId() { return doseId; }
    public void setDoseId(Long doseId) { this.doseId = doseId; }

    public Medication getMedication() { return medication; }
    public void setMedication(Medication medication) { this.medication = medication; }

    public LocalDate getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDate scheduledDate) { this.scheduledDate = scheduledDate; }

    public LocalTime getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(LocalTime scheduledTime) { this.scheduledTime = scheduledTime; }

    public LocalDateTime getActualTakenTime() { return actualTakenTime; }
    public void setActualTakenTime(LocalDateTime actualTakenTime) { this.actualTakenTime = actualTakenTime; }

    public DoseStatus getDoseStatus() { return doseStatus; }
    public void setDoseStatus(DoseStatus doseStatus) { this.doseStatus = doseStatus; }

    public SyncStatus getSyncStatus() { return syncStatus; }
    public void setSyncStatus(SyncStatus syncStatus) { this.syncStatus = syncStatus; }
}
