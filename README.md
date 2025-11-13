# 🏨 VoyageBooking – Hotel Reservation System  

[![Java](https://img.shields.io/badge/Backend-Java%20%2F%20Spring%20Boot-blue?logo=java)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Frontend-Angular%20(TypeScript)-red?logo=angular)](https://angular.io/)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-336791?logo=postgresql)](https://www.postgresql.org/)
[![PayPal](https://img.shields.io/badge/Payments-PayPal%20API-003087?logo=paypal)](https://developer.paypal.com/)

> A full-stack hotel booking application built with **Spring Boot**, **Angular**, and **PostgreSQL**, providing a seamless online booking experience for both customers and administrators.

---

## 📖 Table of Contents  
- [About the Project](#about-the-project)  
- [Architecture](#architecture)  
- [Technologies Used](#technologies-used)  
- [Features](#features)  
- [System Roles](#system-roles)  
- [Database Structure](#database-structure)  
- [Booking Flow](#booking-flow)  
- [Integration with External Systems](#integration-with-external-systems)  
- [Innovative Features](#innovative-features)

---

## About the Project  

**VoyageBooking** is a web-based **hotel reservation system** designed to streamline hotel management and booking operations.  
Developed as part of the *“E-Business Systems”* course at **Sofia University “St. Kliment Ohridski”**, it provides:  
- A centralized booking management platform  
- Real-time room availability tracking  
- Secure online payments via **PayPal**  

Both **users** and **hotel administrators** can interact with the system efficiently through a modern, intuitive web interface.

---

## Architecture  

The system follows the **Model–View–Controller (MVC)** architecture pattern, promoting scalability and clean separation of concerns:  

- **Model** – Manages data, logic, and business rules.  
- **View** – Represents the Angular-based frontend UI.  
- **Controller** – Handles HTTP requests, coordinates between the model and the view.  

---

## Technologies Used  

| Layer | Technology | Description |
|-------|-------------|-------------|
| **Frontend** | Angular, TypeScript, HTML, CSS | Dynamic and responsive client-side interface |
| **Backend** | Java, Spring Boot, REST API | Handles business logic and service orchestration |
| **Database** | PostgreSQL | Stores user, hotel, room, and booking data |
| **Integrations** | PayPal API, Gmail API | Secure payments and email verification |

---

## Features  

### User Features  
- 🔐 **Registration** with email verification  
- 🔑 **Login**, logout, and password recovery via email  
- 🧳 **Search hotels** by country, city, and date range  
- 🏨 **View hotel details**, rooms, and photos  
- 💳 **Book rooms online** and pay via PayPal  
- ❌ **Cancel reservations** easily  
- 👤 **View personal profile** and booking history  

### Admin Features  
- 🏗️ Add, edit, and delete hotels and rooms  
- 🖼️ Upload and manage hotel or room images  
- 📅 Manage room availability and categories  
- 🔍 View reservations by date or room  
- 🧩 Maintain and update accommodation information  

---

## System Roles  

| Role | Permissions |
|------|--------------|
| **User** | Can search hotels, make reservations, and manage their bookings |
| **Admin** | Can create, edit, and manage hotels, rooms, and monitor reservations |

---

## Database Structure  

**Entities Overview:**  
- `User` – Registered users (guests and admins)  
- `Hotel` – Hotels listed on the platform  
- `Room` – Individual rooms linked to hotels  
- `Room_Size_Type` – Room categories (Single, Double, Triple, Apartment, Presidential)  
- `Room_Image` – Photos of rooms  
- `Occupancy` – Availability data for each room  
- `Booking` – Reservation records  

**Relationships:**  
- `User` ↔ `Hotel` – Ownership  
- `Hotel` ↔ `Room` – Containment  
- `Room` ↔ `Booking` – Reservation linkage  
- `Booking` ↔ `Occupancy` – Tracks booked periods  

---

## Booking Flow  

1. User searches for available hotels and rooms by filters.  
2. The system returns only available rooms for the selected dates.  
3. The user selects a room and proceeds to payment.  
4. Payment is handled securely through **PayPal**.  
5. Upon successful payment, booking data is saved to the database.  
6. The booked room is marked as unavailable for that date range.  
7. Both user and admin can view and manage the reservation.  

---

## Integration with External Systems  

### Gmail API  
- Sends account verification emails upon registration.  
- Handles password recovery via secure email link.  

### PayPal Sandbox API  
- Enables safe and fast online payments for bookings.  
- Confirms payment success before saving the booking in the system.  
