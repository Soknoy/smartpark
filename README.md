====================================================
                 SmartPark API
====================================================

SmartPark is a simple in-memory parking management API built with:
- Spring Boot
- Spring Web
- Spring Validation
- Spring Security
- Springdoc OpenAPI (Swagger UI)

It supports vehicle registration, check-in/check-out, and parking lot management.

----------------------------------------------------
Prerequisites
----------------------------------------------------
- Java 21 installed and configured in PATH
- Maven 3.x installed
- Optional: IDE (IntelliJ, VSCode, Eclipse)

----------------------------------------------------
Project Structure
----------------------------------------------------
- com.smartpark.api.controller – REST controllers
- com.smartpark.api.dto – Request and Response DTOs
- com.smartpark.api.model – Domain models (ParkingLot, Vehicle, VehicleType)
- com.smartpark.api.service – Service layer (ParkingService, DataPreloader)
- application.properties – Configurations including Swagger paths and server port

----------------------------------------------------
Installation
----------------------------------------------------
1. Clone the repository:
   git clone <repository-url>
   cd smartpark

2. Build the project using Maven:
   mvn clean install

----------------------------------------------------
Running the Application
----------------------------------------------------
Run using Maven:
   mvn spring-boot:run

Or directly using Java (after building):
   java -jar target/smartpark-0.0.1-SNAPSHOT.jar

Default URL: http://localhost:9090/

----------------------------------------------------
Swagger UI
----------------------------------------------------
Access Swagger UI at:

   http://localhost:9090/swagger-ui.html

Endpoints available under "SmartPark (ArrayList) API":
- POST /api/parking/lot – Register parking lot
- POST /api/parking/vehicle – Register vehicle
- POST /api/parking/checkin/{lotId}/{licensePlate} – Check-in vehicle
- POST /api/parking/checkout/{licensePlate} – Check-out vehicle
- GET /api/parking/occupancy/{lotId} – View lot occupancy
- GET /api/parking/vehicles/{lotId} – List vehicles in a lot
- GET /api/parking/lots – List all lots
- GET /api/parking/vehicles – List all vehicles

----------------------------------------------------
Configuration (application.properties)
----------------------------------------------------
spring.application.name=smartpark
server.port=9090
spring.main.allow-bean-definition-overriding=true

# Springdoc OpenAPI / Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

----------------------------------------------------
Preloaded Data
----------------------------------------------------
Parking Lots:
Lot ID   Location  Capacity
------   -------   -------
LOT-A    Makati    5
LOT-B    Taguig    2

Vehicles:
License Plate  Type        Owner
------------   --------   ---------------
ABC-123        CAR        Peter Parker
MOTO-1         MOTORCYCLE Bruce Wayne

----------------------------------------------------
Notes
----------------------------------------------------
- Spring Security is enabled but all endpoints are currently permitted.
- Data is stored in-memory; it resets on each restart.
- Use Swagger UI to explore and test endpoints.

Swagger Link: http://localhost:9090/swagger-ui.html
