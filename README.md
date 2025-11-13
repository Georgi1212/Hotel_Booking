# 🏨 VoyageBooking – Hotel Reservation System  

[![Java](https://img.shields.io/badge/Backend-Java%20%2F%20Spring%20Boot-blue?logo=java)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Frontend-Angular%20(TypeScript)-red?logo=angular)](https://angular.io/)
[![MySQL](https://img.shields.io/badge/Database-MySQL-blue?logo=mysql)](https://www.mysql.com/)
[![PayPal](https://img.shields.io/badge/Payments-PayPal%20API-003087?logo=paypal)](https://developer.paypal.com/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](#)

> A full-stack hotel booking application built with **Spring Boot** and **Angular**, providing a seamless online booking experience for both customers and administrators.

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
- [Setup & Installation](#setup--installation)  
- [Screenshots](#screenshots)  
- [Author](#author)  
- [License](#license)  

---

## 🧾 About the Project  

**VoyageBooking** is a web-based **hotel reservation system** designed to streamline hotel management and booking operations.  
Developed as part of the *“E-Business Systems”* course at **Sofia University “St. Kliment Ohridski”**, it provides:  
- A centralized booking management platform  
- Real-time availability tracking  
- Secure online payments via PayPal  

Both **users** and **hotel administrators** can interact with the system efficiently through a modern web interface.

---

## 🏗️ Architecture  

The system follows the **Model–View–Controller (MVC)** pattern for modularity and scalability:  

- **Model** – Manages application data, logic, and rules.  
- **View** – Represents the frontend UI built with Angular.  
- **Controller** – Handles user input, routing, and API calls between frontend and backend.  

---

## 💻 Technologies Used  

| Layer | Technology | Description |
|-------|-------------|-------------|
| **Frontend** | Angular, TypeScript, HTML, CSS | Dynamic UI and responsive client-side logic |
| **Backend** | Java, Spring Boot, REST API | Business logic and service orchestration |
| **Database** | MySQL | Persistent data storage |
| **Integration** | PayPal API, Gmail API | Payment and email verification services |

---

## 🌟 Features  

### 👤 User Features  
- 🔐 Registration with email verification  
- 🔑 Login, logout, and password recovery via email  
- 🧳 Browse and search hotels by country, city, or date  
- 🏨 View hotel details, rooms, and photos  
- 💳 Online booking with PayPal integration  
- ❌ Cancel existing reservations  
- 👤 View profile and booking history  

### 🛎️ Admin Features  
- 🏗️ Add, edit, and delete hotels and rooms  
- 🖼️ Upload and manage hotel/room images  
- 📅 Manage availability and room categories  
- 🔍 View reservations by date range or room  
- 🧩 Maintain hotel and room metadata  

---

## 🧑‍💼 System Roles  

| Role | Permissions |
|------|--------------|
| **User** | Search, book, and manage personal reservations |
| **Admin** | Manage hotels, rooms, and monitor bookings |

---

## 🗄️ Database Structure  

**Entities Overview:**  
- `User` – Application users  
- `Hotel` – Hotel data and details  
- `Room` – Individual rooms linked to hotels  
- `Room_Size_Type` – Room categories (Single, Double, Triple, Apartment, Presidential)  
- `Room_Image` – Room photos  
- `Occupancy` – Room availability status  
- `Booking` – Reservation details  

**Relationships:**  
- `User` ↔ `Hotel` – Ownership  
- `Hotel` ↔ `Room` – Containment  
- `Room` ↔ `Booking` – Reservation linkage  
- `Booking` ↔ `Occupancy` – Availability tracking  

---

## 🔁 Booking Flow  

1. User searches for available hotels and rooms.  
2. Selects room and initiates booking.  
3. Proceeds to **PayPal** for payment.  
4. Upon successful payment, the booking is confirmed.  
5. The room is marked unavailable for the booked dates.  
6. Both user and admin can view the confirmed reservation.  

---

## 🌐 Integration with External Systems  

### 📧 Gmail API  
- Sends verification link upon registration.  
- Handles password reset requests via secure email link.  

### 💳 PayPal Sandbox API  
- Processes online room payments securely.  
- Confirms successful transactions before booking is finalized.  

---

## 🚀 Innovative Features  

- ✅ **Email Verification** – ensures account authenticity.  
- 🔒 **Real-Time Room Locking** – prevents overlapping reservations.  
- 💸 **Instant PayPal Payments** – secure and integrated.  
- 🧱 **MVC Modular Design** – easy maintenance and scalability.  
