# product_service

## Overview
This service manages product inventions, handling creation, retrieval, and updates of invention data.

## API Endpoints

### Create Invention
- **Endpoint**: `POST /api/inventions`
- **Description**: Creates a new invention record
- **Request Body**: `InventionRequest` object with invention details
- **Response**: The saved invention entity

### Get Invention by ID
- **Endpoint**: `GET /api/inventions/{innovationId}`
- **Description**: Retrieves an invention by its ID
- **Path Variable**: `innovationId` - The ID of the invention to retrieve
- **Response**: The invention entity if found

### Update Bid Times
- **Endpoint**: `PUT /api/inventions/updateBidTimes`
- **Description**: Updates the bidding time window for an invention
- **Request Body**: `BidTimeUpdateRequest` object with new bid times
- **Response**: Success message or error if invention not found

### Get Innovator Details
- **Endpoint**: `GET /api/inventions/innovator-details/{inventionId}`
- **Description**: Retrieves details about the innovator of a specific invention
- **Path Variable**: `inventionId` - The ID of the invention
- **Response**: `InnovatorDetailsResponse` with innovator email and payment package

### Update Investor
- **Endpoint**: `PATCH /api/inventions/{inventionId}/update-investor`
- **Description**: Updates the investor associated with an invention
- **Path Variable**: `inventionId` - The ID of the invention to update
- **Request**: The investor ID can be provided either as:
  - Query parameter: `?investorId={investorId}`
  - JSON request body: `{"investorId": 123}`
- **Response**: Success message or error if invention not found

## Setup

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