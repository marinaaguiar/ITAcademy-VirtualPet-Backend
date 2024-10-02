# ITAcademy Virtual Pet - Backend

## Overview

This repository contains the **backend** for the ITAcademy Virtual Pet application, which is responsible for managing and storing the pet’s data. The backend is built using **Java** and exposes a RESTful API for the frontend, developed in SwiftUI, to interact with. This setup allows users to adopt a virtual pet and perform actions like feeding, playing, and monitoring its health, with all interactions being persisted and managed by the backend.

The mobile frontend for this project is available at the following link: [ITAcademy-VirtualPet Mobile App](https://github.com/marinaaguiar/ITAcademy-VirtualPet).

## Features

- **REST API**: Provides endpoints for managing pet data, including creating, updating, and retrieving a pet's status.
- **Data persistence**: The backend ensures that all interactions with the pet are stored and maintained.
- **Health and happiness management**: The backend tracks the pet’s health and happiness, ensuring that these attributes are dynamically updated as the user interacts with the pet via the mobile app.
- **Cross-platform communication**: Serves as the middle layer between the iOS app and the data storage, enabling seamless communication.

## Technologies Used

- **Language**: Java
- **Framework**: Spring Boot (for creating the RESTful API)
- **Database**: MongoDB
- **Tools**: Maven (for build automation)

## API Endpoints

Here are the primary endpoints exposed by the backend:

- `POST /api/auth/register`: Register a new user.
- `POST /api/auth/login`: Login an existing user.
- `POST /api/users/{userId}/addPet`: Add a new pet for a specific user.
- `GET /api/users/{userId}/pets`: Retrieves all the pets the user has.
- `GET /pets/{id}`: Retrieves the pet's details by ID.
- `PUT /pets/{id}`: Updates the pet’s details (e.g., health, happiness).
- `DELETE /pets/{id}`: Deletes the virtual pet.

## Installation

To run the backend locally, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/marinaaguiar/ITAcademy-VirtualPet-Backend.git


2. **Configure the database:**

- Ensure MySQL (or your chosen database) is installed and running.
- Update the application.properties file with your database credentials.

3. **Build and Run:**

- Use Maven to build and package the project:
   ```bash
    mvn clean install

- Run the application:

   ```bash
    mvn spring-boot:run

3. **Access the API:** The backend will run on http://localhost:8080 by default.

## Integration with Mobile Application

The backend is designed to work seamlessly with the mobile app, which is built using SwiftUI. The mobile app communicates with this backend through the provided RESTful API, allowing real-time updates and interactions with the virtual pet.

You can find the mobile app repository here: [ITAcademy-VirtualPet Mobile App](https://github.com/marinaaguiar/ITAcademy-VirtualPet).
