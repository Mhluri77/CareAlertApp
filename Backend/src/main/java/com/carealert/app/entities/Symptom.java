package com.carealert.app.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "symptoms")
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "symptom_id")
    private Long symptomId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "symptom_name")
    private String symptomName;

    private String description;

    @Column(name = "self_care_advice")
    private String selfCareAdvice;

    @Column(name = "warning_signs")
    private String warningSigns;

    @Column(name = "search_date")
    private LocalDateTime searchDate;

    // Getters and setters
    public Long getSymptomId() { return symptomId; }
    public void setSymptomId(Long symptomId) { this.symptomId = symptomId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getSymptomName() { return symptomName; }
    public void setSymptomName(String symptomName) { this.symptomName = symptomName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSelfCareAdvice() { return selfCareAdvice; }
    public void setSelfCareAdvice(String selfCareAdvice) { this.selfCareAdvice = selfCareAdvice; }

    public String getWarningSigns() { return warningSigns; }
    public void setWarningSigns(String warningSigns) { this.warningSigns = warningSigns; }

    public LocalDateTime getSearchDate() { return searchDate; }
    public void setSearchDate(LocalDateTime searchDate) { this.searchDate = searchDate; }
}
