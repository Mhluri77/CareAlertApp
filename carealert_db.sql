
CREATE DATABASE carealert_db;
USE carealert_db;

-- Step 2: Create tables

-- USERS
CREATE TABLE users (
  user_id BIGINT NOT NULL AUTO_INCREMENT,
  full_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  profile_photo VARCHAR(255) DEFAULT NULL,
  registration_date DATE NOT NULL,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- MEDICATIONS
CREATE TABLE medications (
  medication_id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  medication_name VARCHAR(255) NOT NULL,
  dosage VARCHAR(100) NOT NULL,
  frequency VARCHAR(100) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE DEFAULT NULL,
  reminder_time TIME DEFAULT NULL,
  quantity INT DEFAULT NULL,
  notes VARCHAR(255) DEFAULT NULL,
  photo VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (medication_id),
  CONSTRAINT fk_medications_user FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- MEDICATION_DOSES
CREATE TABLE medication_doses (
  dose_id BIGINT NOT NULL AUTO_INCREMENT,
  medication_id BIGINT NOT NULL,
  scheduled_date DATE NOT NULL,
  scheduled_time TIME NOT NULL,
  actual_taken_time DATETIME DEFAULT NULL,
  dose_status ENUM('Taken','Skipped','Missed') NOT NULL,
  sync_status ENUM('Pending','Synced') DEFAULT 'Pending',
  PRIMARY KEY (dose_id),
  CONSTRAINT fk_medication_doses_med FOREIGN KEY (medication_id) REFERENCES medications(medication_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SYMPTOMS
CREATE TABLE symptoms (
  symptom_id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  symptom_name VARCHAR(255) NOT NULL,
  description VARCHAR(255) DEFAULT NULL,
  self_care_advice VARCHAR(255) DEFAULT NULL,
  warning_signs VARCHAR(255) DEFAULT NULL,
  search_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (symptom_id),
  CONSTRAINT fk_symptoms_user FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- NOTIFICATIONS
CREATE TABLE notifications (
  notification_id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  type VARCHAR(100) NOT NULL,
  title VARCHAR(255) NOT NULL,
  message VARCHAR(500) NOT NULL,
  notification_datetime DATETIME DEFAULT CURRENT_TIMESTAMP,
  read_status BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (notification_id),
  CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ADHERENCE_SCORES
CREATE TABLE adherence_scores (
  score_id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  total_scheduled INT DEFAULT 0,
  total_taken INT DEFAULT 0,
  total_missed INT DEFAULT 0,
  total_skipped INT DEFAULT 0,
  adherence_score DOUBLE DEFAULT 0,
  period_start DATE NOT NULL,
  period_end DATE NOT NULL,
  PRIMARY KEY (score_id),
  CONSTRAINT fk_adherence_scores_user FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- USER_SETTINGS
CREATE TABLE user_settings (
  setting_id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  language VARCHAR(50) NOT NULL,
  notification_enabled BOOLEAN DEFAULT TRUE,
  reminder_sound VARCHAR(100) DEFAULT 'default_tone',
  reminder_preference VARCHAR(100) DEFAULT 'standard',
  theme VARCHAR(50) DEFAULT 'system',
  PRIMARY KEY (setting_id),
  CONSTRAINT fk_user_settings_user FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- TRUSTED_CONTACTS
CREATE TABLE trusted_contacts (
  contact_id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  contact_name VARCHAR(100) NOT NULL,
  contact_phone VARCHAR(50) DEFAULT NULL,
  contact_email VARCHAR(100) DEFAULT NULL,
  alert_permission BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (contact_id),
  CONSTRAINT fk_trusted_contacts_user FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SYNC_RECORDS
CREATE TABLE sync_records (
  sync_id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  record_type VARCHAR(50) NOT NULL,
  record_id BIGINT DEFAULT NULL,
  sync_status ENUM('Pending','Synced','Failed') DEFAULT 'Pending',
  last_sync_datetime DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (sync_id),
  CONSTRAINT fk_sync_records_user FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- USERS
INSERT INTO users (user_id, full_name, email, password_hash, profile_photo, registration_date) VALUES
(1, 'Gift Mapimele', 'gift@gmail.com', 'b2f5ff47436671b6e533d8dc3614845d', 'profile1.png', '2026-09-18'),
(2, 'Karabo Mohale', 'karabo@gmail.com', '0f3c0e3e2f7a3c3f9f9e9d9f9a9c9d9f', 'profile2.png', '2026-09-18'),
(3, 'Brain Khosa', 'brainkhosa@gmail.com', '5f4dcc3b5aa765d61d8327deb882cf99', 'profile5.png', '2026-09-18'),
(4, 'Johnny Bravo', 'johnnybravo@gmail.com', '202cb962ac59075b964b07152d234b70', 'profile6.png', '2026-09-18'),
(5, 'Raesibe Galane', 'raesibe@gmail.com', 'd1f491a404d6854880943e5c3cd9ca25', 'profile3.png', '2026-09-18'),
(6, 'Navelani Hlongwani', 'navelani@gmail.com', '8c6976e5b5410415bde908bd4dee15df', 'profile4.png', '2026-09-18');

-- MEDICATIONS
INSERT INTO medications (medication_id, user_id, medication_name, dosage, frequency, start_date, end_date, reminder_time, quantity, notes, photo) VALUES
(1, 1, 'Panado', '500mg', 'Twice daily', '2026-09-18', '2026-09-25', '08:00:00', 14, 'Take after meals', NULL),
(2, 1, 'Vitamin C', '1000mg', 'Once daily', '2026-09-18', '2026-10-18', '09:00:00', 30, 'Boost immunity', NULL),
(3, 2, 'Nurofen', '200mg', 'Three times daily', '2026-09-18', '2026-09-25', '07:00:00', 21, 'Take with food', NULL),
(4, 3, 'Amoxicillin', '250mg', 'Twice daily', '2026-09-18', '2026-09-22', '08:30:00', 10, 'Complete full course', NULL),
(5, 4, 'Aspirin', '100mg', 'Once daily', '2026-09-18', '2026-10-18', '06:30:00', 30, 'Take with water', NULL),
(6, 5, 'Panado', '500mg', 'Twice daily', '2026-09-18', '2026-09-25', '08:00:00', 14, 'Take after meals', NULL),
(7, 6, 'Grandpa Headache Powders', '1 sachet', 'Once daily', '2026-09-18', '2026-09-20', '07:30:00', 3, 'Mix with water', NULL);

-- MEDICATION_DOSES
INSERT INTO medication_doses (dose_id, medication_id, scheduled_date, scheduled_time, actual_taken_time, dose_status, sync_status) VALUES
(1, 1, '2026-09-18', '08:00:00', NULL, 'Taken', 'Synced'),
(2, 2, '2026-09-18', '09:30:00', NULL, 'Missed', 'Pending'),
(3, 3, '2026-09-18', '07:10:00', NULL, 'Taken', 'Synced'),
(4, 4, '2026-09-18', '08:30:00', NULL, 'Missed', 'Pending'),
(5, 5, '2026-09-18', '06:30:00', NULL, 'Taken', 'Synced'),
(6, 6, '2026-09-18', '08:00:00', NULL, 'Taken', 'Synced'),
(7, 7, '2026-09-18', '07:30:00', NULL, 'Missed', 'Pending');

-- SYMPTOMS
INSERT INTO symptoms (symptom_id, user_id, symptom_name, description, self_care_advice, warning_signs, search_date) VALUES
(1, 1, 'Headache', 'Mild headache after studying', 'Drink water, rest eyes, avoid screen time', 'Seek medical help if persistent', '2026-09-18 18:03:41'),
(2, 2, 'Cough', 'Dry cough for 2 days', 'Drink warm fluids, rest', 'Seek medical help if persistent', '2026-09-18 18:03:41'),
(3, 3, 'Nausea', 'Feeling nauseous after meals', 'Eat light meals, avoid oily food', 'Seek medical help if severe', '2026-09-18 18:03:41'),
(4, 4, 'Fatigue', 'Tiredness after long hours', 'Get enough sleep, hydrate', 'Seek medical help if ongoing', '2026-09-18 18:03:41'),
(5, 5, 'Fever', 'Mild fever after flu', 'Rest, hydrate, take Panado', 'Seek medical help if persistent', '2026-09-18 18:03:41'),
(6, 6, 'Back Pain', 'Lower back pain after exercise', 'Stretch, rest, apply heat', 'Seek medical help if severe', '2026-09-18 18:03:41');

-- NOTIFICATIONS
INSERT INTO notifications (notification_id, user_id, type, title, message, notification_datetime, read_status) VALUES
(1, 1, 'Reminder', 'Medication Due', 'Panado 500mg is due now', '2026-09-18 18:03:41', FALSE),
(2, 2, 'Reminder', 'Medication Due', 'Nurofen 200mg is due now', '2026-09-18 18:03:41', FALSE),
(3, 3, 'Reminder', 'Medication Due', 'Amoxicillin 250mg is due now', '2026-09-18 18:03:41', FALSE),
(4, 4, 'Reminder', 'Medication Due', 'Dispirin 100mg is due now', '2026-09-18 18:03:41', FALSE),
(5, 5, 'Reminder', 'Medication Due', 'Panado 500mg is due now', '2026-09-18 18:03:41', FALSE),
(6, 6, 'Reminder', 'Medication Due', 'Grandpa Headache Powder is due now', '2026-09-18 18:03:41', FALSE);

-- ADHERENCE_SCORES
INSERT INTO adherence_scores (score_id, user_id, total_scheduled, total_taken, total_missed, total_skipped, adherence_score, period_start, period_end) VALUES
(1, 1, 25, 23, 1, 1, 92, '2026-09-01', '2026-09-18'),
(2, 2, 15, 14, 1, 0, 93.3, '2026-09-01', '2026-09-18'),
(3, 3, 10, 8, 2, 0, 80, '2026-09-01', '2026-09-18'),
(4, 4, 20, 19, 1, 0, 95, '2026-09-01', '2026-09-18'),
(5, 5, 12, 8, 2, 1, 66.6, '2026-09-01', '2026-09-18'),
(6, 6, 22, 14, 1, 0, 63.6, '2026-09-01', '2026-09-18');

-- USER_SETTINGS
INSERT INTO user_settings (setting_id, user_id, language, notification_enabled, reminder_sound, reminder_preference, theme) VALUES
(1, 1, 'Tsonga', TRUE, 'default_tone', 'standard', 'dark'),
(2, 2, 'English', TRUE, 'chime', 'standard', 'dark'),
(3, 3, 'Sepedi', TRUE, 'beep', 'snooze', 'light'),
(4, 4, 'Zulu', FALSE, 'silent', 'quiet_hours', 'system'),
(5, 5, 'Ndebele', TRUE, 'default_tone', 'standard', 'light'),
(6, 6, 'English', TRUE, 'beep', 'snooze', 'dark');