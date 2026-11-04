
package com.carealert.app.services;

import com.carealert.app.entities.Medication;
import com.carealert.app.repositories.MedicationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MedicationReminderService {

    private final MedicationRepository medicationRepository;
    private final PushyService pushyService;

    public MedicationReminderService(
            MedicationRepository medicationRepository,
            PushyService pushyService) {
        this.medicationRepository = medicationRepository;
        this.pushyService = pushyService;
    }

    @Scheduled(fixedRate = 60000)
    public void checkMedicationReminders() {

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES);

        List<Medication> medications = medicationRepository.findAll();

        for (Medication medication : medications) {
            try {
                if (medication.getReminderTime() == null ||
                        medication.getFrequency() == null ||
                        medication.getFrequency().isBlank()) {
                    continue;
                }

                if (medication.getStartDate() != null &&
                        today.isBefore(medication.getStartDate())) {
                    continue;
                }

                if (medication.getEndDate() != null &&
                        today.isAfter(medication.getEndDate())) {
                    continue;
                }

                if (!isMedicationDue(medication, today, now)) {
                    continue;
                }

                // Prevent duplicate notifications for the same minute
                if (medication.getLastReminderSentAt() != null &&
                        medication.getLastReminderSentAt()
                                .truncatedTo(ChronoUnit.MINUTES)
                                .equals(now)) {
                    continue;
                }

                if (medication.getUser() == null ||
                        medication.getUser().getPushyDeviceToken() == null ||
                        medication.getUser().getPushyDeviceToken().isBlank()) {
                    continue;
                }

                String title = "Medication Reminder";
                String message = "Time to take " +
                        medication.getMedicationName() +
                        ". Dosage: " + medication.getDosage();

                // Send notification first
                pushyService.sendNotification(
                        medication.getUser().getPushyDeviceToken(),
                        title,
                        message
                );

                // Record successful notification
                medication.setLastReminderSentAt(now);
                medicationRepository.save(medication);

                System.out.println(
                        "Medication reminder sent: " +
                                medication.getMedicationName()
                );

            } catch (Exception e) {
                System.err.println(
                        "Medication reminder failed for ID " +
                                medication.getMedicationId() +
                                ": " + e.getMessage()
                );
            }
        }
    }

    private boolean isMedicationDue(
            Medication medication,
            LocalDate today,
            LocalDateTime now) {

        String frequency = medication.getFrequency()
                .trim()
                .toLowerCase(Locale.ROOT);

        LocalDate startDate = medication.getStartDate() != null
                ? medication.getStartDate()
                : today;

        LocalTime reminderTime = medication.getReminderTime();

        // Daily schedules and schedules every X hours
        Integer intervalHours = getIntervalHours(frequency);

        if (intervalHours != null) {

            LocalDateTime firstReminder =
                    LocalDateTime.of(startDate, reminderTime);

            long minutesSinceStart =
                    ChronoUnit.MINUTES.between(firstReminder, now);

            long intervalMinutes = intervalHours * 60L;

            return minutesSinceStart >= 0 &&
                    minutesSinceStart % intervalMinutes == 0;
        }

        // Every other day or every X days
        Integer intervalDays = getIntervalDays(frequency);

        if (intervalDays != null) {

            long daysBetween =
                    ChronoUnit.DAYS.between(startDate, today);

            return daysBetween >= 0 &&
                    daysBetween % intervalDays == 0 &&
                    isReminderTimeNow(reminderTime, now);
        }

        // Weekly
        if (frequency.contains("weekly") ||
                frequency.contains("once a week") ||
                frequency.contains("once per week")) {

            long daysBetween =
                    ChronoUnit.DAYS.between(startDate, today);

            return daysBetween >= 0 &&
                    daysBetween % 7 == 0 &&
                    isReminderTimeNow(reminderTime, now);
        }

        // Monthly
        if (frequency.contains("monthly") ||
                frequency.contains("once a month") ||
                frequency.contains("once per month")) {

            int reminderDay = Math.min(
                    startDate.getDayOfMonth(),
                    today.lengthOfMonth()
            );

            return today.getDayOfMonth() == reminderDay &&
                    isReminderTimeNow(reminderTime, now);
        }

        // Unknown or custom frequency: do not guess
        System.err.println(
                "Unsupported medication frequency: " + frequency
        );

        return false;
    }

    private Integer getIntervalHours(String frequency) {

        // Common daily schedules
        if (frequency.contains("four times") ||
                frequency.contains("4 times") ||
                frequency.contains("4x daily")) {
            return 6;
        }

        if (frequency.contains("three times") ||
                frequency.contains("3 times") ||
                frequency.contains("3x daily")) {
            return 8;
        }

        if (frequency.contains("twice") ||
                frequency.contains("2 times") ||
                frequency.contains("2x daily")) {
            return 12;
        }

        if (frequency.contains("daily") ||
                frequency.contains("once a day") ||
                frequency.contains("once daily")) {
            return 24;
        }

        // Every X hours
        Matcher matcher = Pattern.compile(
                "every\\s+(\\d+)\\s+hours?"
        ).matcher(frequency);

        if (matcher.find()) {
            int hours = Integer.parseInt(matcher.group(1));

            if (hours > 0 && hours <= 24) {
                return hours;
            }
        }

        return null;
    }

    private Integer getIntervalDays(String frequency) {

        if (frequency.contains("every other day") ||
                frequency.contains("alternate days") ||
                frequency.contains("every second day")) {
            return 2;
        }

        Matcher matcher = Pattern.compile(
                "every\\s+(\\d+)\\s+days?"
        ).matcher(frequency);

        if (matcher.find()) {
            int days = Integer.parseInt(matcher.group(1));

            if (days > 0) {
                return days;
            }
        }

        return null;
    }

    private boolean isReminderTimeNow(
            LocalTime reminderTime,
            LocalDateTime now) {

        return reminderTime.getHour() == now.getHour() &&
                reminderTime.getMinute() == now.getMinute();
    }
}
