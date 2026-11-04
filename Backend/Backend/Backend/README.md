# CareAlert Backend API

## How to Run
1. Import the database:
   mysql -u root -p carealert_db < carealert_db.sql

2. Run the backend:
   java -jar carealert-0.0.1-SNAPSHOT.jar

## Endpoints
- http://localhost:8080/api/carealert/users
- http://localhost:8080/api/carealert/medications
- http://localhost:8080/api/carealert/doses
- http://localhost:8080/api/carealert/symptoms
- http://localhost:8080/api/carealert/notifications
