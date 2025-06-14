# Product Service API Documentation

## Overview
The Product Service is a Spring Boot application that provides REST APIs for managing product inventions. It enables creating, retrieving, and updating invention data, as well as managing bid times, innovator details, and bid selection processes.

## API Endpoints

### Invention Management

#### 1. Create Invention
- **Endpoint**: `POST /api/inventions`
- **Description**: Creates a new invention record in the system
- **Request Body**: 
  ```json
  {
    "inventionId": Long,
    "inventorId": Long,
    "investorId": Long,
    "productVideo": "URL string",
    "productDescription": "Text description",
    "capital": Integer,
    "salesData": [Integer array],
    "modeOfSale": "DIRECT | SHARED | PARTNERSHIP | LICENSE",
    "costDescription": "Text description",
    "expectedCapital": Integer,
    "breakupRevenue": Integer,
    "paymentPackage": "STANDARD | PREMIUM | ENTERPRISE",
    "bidStartTime": "HH:MM:SS",
    "bidEndTime": "HH:MM:SS",
    "aoi": ["Array of string"],
    "bidStartDate": "YYYY-MM-DD",
    "isLive": Boolean,
    "isPaid": Boolean
  }
  ```
- **Response**: 
  - **Status Code**: 200 OK
  - **Body**: The saved invention entity with all fields

#### 2. Get Invention by ID
- **Endpoint**: `GET /api/inventions/{innovationId}`
- **Description**: Retrieves an invention by its ID
- **Path Variable**: `innovationId` - The ID of the invention to retrieve
- **Response**: 
  - **Status Code**: 200 OK
  - **Body**: The invention entity if found
  - **Error**: 500 Internal Server Error with message if not found

#### 3. Get Inventions by Inventor ID
- **Endpoint**: `GET /api/inventions/inventor/{inventorId}`
- **Description**: Retrieves all inventions created by a specific inventor
- **Path Variable**: `inventorId` - The ID of the inventor
- **Response**: 
  - **Status Code**: 200 OK
  - **Body**: Array of invention entities

### Bidding Management

#### 4. Update Bid Times
- **Endpoint**: `PUT /api/inventions/updateBidTimes`
- **Description**: Updates the bidding time window for an invention and sends notifications to matched investors
- **Request Body**: 
  ```json
  {
    "inventionId": Long,
    "bidStartTime": "HH:MM:SS",
    "bidEndTime": "HH:MM:SS",
    "bidStartDate": "YYYY-MM-DD"
  }
  ```
- **Response**: 
  - **Status Code**: 200 OK with "Bid times updated successfully!" message if found
  - **Status Code**: 404 Not Found with "Invention ID not found!" message if not found

#### 5. Select Bid
- **Endpoint**: `POST /api/inventions/bidSelected`
- **Description**: Selects a winning bid for an invention by updating the investor ID and live status
- **Request Body**: 
  ```json
  {
    "Invention_ID": Long,
    "Investor_ID": Long,
    "Is_Live": Boolean
  }
  ```
- **Response**: 
  - **Status Code**: 200 OK with "Bid selection updated successfully!" message if found
  - **Status Code**: 404 Not Found with "Invention ID not found!" message if not found

### Innovator Details

#### 6. Get Innovator Details
- **Endpoint**: `GET /api/inventions/innovator-details/{inventionId}`
- **Description**: Retrieves details about the innovator of a specific invention
- **Path Variable**: `inventionId` - The ID of the invention
- **Response**: 
  - **Status Code**: 200 OK
  - **Body**: 
    ```json
    {
      "innovatorEmail": "email@example.com",
      "paymentPackage": "STANDARD | PREMIUM | ENTERPRISE"
    }
    ```
  - **Error**: 500 Internal Server Error with message if invention not found

## Data Models

### Invention Entity
```
inventionId (Long): Unique identifier for the invention
inventorId (Long): ID of the inventor who created the invention
investorId (Long): ID of the investor selected for the invention (nullable)
productVideo (String): URL to the product demonstration video
productDescription (String): Detailed description of the product
capital (Integer): Current capital associated with the invention
salesData (List<Integer>): JSON array of sales data points
modeOfSale (Enum): DIRECT, SHARED, PARTNERSHIP, or LICENSE
costDescription (String): Description of costs associated with the invention
expectedCapital (Integer): Expected capital needed for the invention
breakupRevenue (Integer): Revenue breakdown value
paymentPackage (Enum): STANDARD, PREMIUM, or ENTERPRISE package type
bidStartTime (LocalTime): Time when bidding opens
bidEndTime (LocalTime): Time when bidding closes
aoi (List<String>): JSON array of areas of interest tags
bidStartDate (LocalDate): Date when bidding starts
isLive (Boolean): Whether the invention is currently live for bidding
isPaid (Boolean): Whether payment for the invention has been processed
```

### Mode of Sale Options
- `DIRECT`: Direct sale of the invention
- `SHARED`: Shared ownership model
- `PARTNERSHIP`: Partnership arrangement
- `LICENSE`: Licensing the invention

### Payment Package Options
- `STANDARD`: Basic package
- `PREMIUM`: Enhanced package with additional benefits
- `ENTERPRISE`: Comprehensive package for large-scale ventures

## External Service Integrations

The Product Service integrates with several external services:

1. **Investor Service**: Finds matching investors based on areas of interest and payment package
   - Endpoint: `http://localhost:5006/api/investors/match`

2. **Innovator Service**: Gets innovator details by ID
   - Endpoint: `http://localhost:5001/api/innovator/{innovatorId}`

3. **Notification System**: Sends bid notifications via Kafka
   - Topic: `new-bid`

## Resiliency Features

The service implements several resiliency patterns:

1. **Circuit Breaker**: Uses Resilience4j to manage failures when calling external services
2. **Retry Mechanism**: Automatically retries failed external service calls
3. **Timeout Handling**: Enforces timeouts for external service calls

## Setup Instructions

### Environment Configuration
Create a `.env` file in the project root with the following properties:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### Running the Application
```bash
./mvnw spring-boot:run
```

The service runs on port `5002` by default, and Swagger UI is available at `/swagger-ui.html`