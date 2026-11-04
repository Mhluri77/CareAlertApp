# CareAlert Backend

## Overview
The CareAlert Backend is a Spring Boot REST API that supports the CareAlert Android application.

It manages application data, medication information, device registration and push notifications.

## Technologies
- Java
- Spring Boot
- Maven
- MySQL
- Hibernate / JPA
- Pushy Push Notifications

## Project Structure
- `src/main/java` - Backend Java source code
- `src/main/resources` - Application resources
- `pom.xml` - Maven dependencies and configuration

## Requirements
- Java JDK
- MySQL Server

## Configuration
Create your own `src/main/resources/application.properties` file.

Configure your database connection and Pushy API key locally.

Do not upload passwords or API secrets to GitHub.

## Running the Backend
From the Backend directory, run:

```powershell
.\mvnw.cmd spring-boot:run
```

The application uses port 8080 by default.

## Push Notifications
The backend integrates with Pushy to send notifications to registered Android devices.

## Medication Reminders
An automated scheduler checks medication schedules and sends reminders when they are due.
