# CareAlert – Medication Reminder Application

## 1. Introduction

CareAlert is an Android application that helps users manage their medication and remember when to take it.

Users can register, log in, add medication, track their medication activities and receive notifications when it is time to take their medicine.

The application uses an Android frontend connected to a Spring Boot backend and a MySQL database.

## 2. Main Features

- User registration and login.
- Add and manage medication.
- Automatic medication reminders.
- Push notifications using Pushy.
- Medication synchronisation.
- Medication adherence tracking.
- Symptom recording.
- Trusted contacts.
- Help and support.
- Application settings and language support.

## 3. Technologies Used

**Frontend:**
- Kotlin
- XML
- Android Studio
- Volley
- Pushy Android SDK

**Backend:**
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- MySQL
- Pushy REST API

**Other tools:**
- Postman
- Git and GitHub

## 4. How the System Works

CareAlert has two main parts:

1. **Android application:** Allows users to manage their medication and use the application's features.
2. **Spring Boot backend:** Handles requests from the Android app, stores information in MySQL and sends notifications through Pushy.

The Android app communicates with the backend using REST APIs.

## 5. Project Structure

```text
CareAlertApp/
│
├── app/              Android application
├── Backend/          Spring Boot backend
├── gradle/           Gradle files
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 6. Installation and Setup

### Android Application

1. Clone the repository:

```bash
git clone https://github.com/Mhluri77/CareAlertApp.git
```

2. Open the project in Android Studio.
3. Allow Gradle to synchronise.
4. Connect an Android phone or emulator.
5. Build and run the application.

### Backend

1. Open the `Backend` folder in IntelliJ IDEA.
2. Make sure Java and MySQL are installed.
3. Create and configure the MySQL database.
4. Add your local database details and Pushy API key to `application.properties`.
5. Start the backend using:

```powershell
.\mvnw.cmd spring-boot:run
```

The backend runs on port `8080` by default.

## 7. Push Notifications and Medication Reminders

CareAlert uses Pushy to send notifications to Android devices.

When a user registers their device, the application receives a Pushy device token and sends it to the backend.

The backend stores this token and uses it to send notifications.

The medication reminder service checks medication schedules regularly and sends a notification when medication is due. It also records when a reminder was sent to help prevent duplicate notifications.

## 8. Network Configuration

The Android application currently connects to the backend using this local IP address:

```text
http://192.168.18.169:8080/api/carealert/users
```

This is the developer's laptop IP address.

For the application to work:

- The backend must be running on the laptop.
- The phone and laptop must use the same Wi-Fi network.
- The IP address must be correct.

This setup is only for development and testing. To allow other users to access CareAlert, the backend must be hosted online and the Android application's API address must be updated.

## 9. Testing

The application can be tested by:

- Registering and logging in.
- Adding and managing medication.
- Checking medication synchronisation.
- Testing API requests using Postman.
- Sending test push notifications.
- Checking whether automatic medication reminders work.

## 10. Security

Database passwords and Pushy secret API keys must not be uploaded to GitHub.

The `application.properties` file should remain private. Each developer must create their own local configuration file.

## 11. Future Improvements

Possible improvements include:

- Hosting the backend online.
- Improving medication reports.
- Adding more notification options.
- Improving application security.
- Adding more testing.

## 12. GitHub Repository

[CareAlert GitHub Repository](https://github.com/Mhluri77/CareAlertApp)

## 13. Youtube Video Links

 Below are the video demonstrations of the already installed app on an android phone and the features of the CareAlert App:

[https://youtube.com/shorts/6CBFU1U9SYc?feature=share]

[https://youtube.com/shorts/Qq703GLLMpI?feature=share]

[https://youtube.com/shorts/ckio26F-tKk]

## 14. Conclusion

CareAlert helps users manage their medication and receive reminders on time. It combines an Android application, a Spring Boot backend, a MySQL database and Pushy notifications to provide a simple medication management system.

**CareAlert – Helping users stay on track with their medication.**
