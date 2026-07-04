# 🎓 AI Timetable Generator

A full-stack, automated college scheduling system designed to eliminate manual timetable creation. Built with Java Spring Boot and MySQL, this application uses a smart algorithm to generate conflict-free schedules, manage professor availability, and export data seamlessly.

**Live Demo:** [AI Timetable Generator](https://ai-timetable-ph11.onrender.com)  
*(Note: The live demo is protected by Spring Security. Use credentials Username: `admin` / Password: `admin@123` to test it.)*

---

## 🚀 Features

* **Smart AI Generation:** Automatically assigns classes, teachers, and subjects using a rotational balancing algorithm.
* **Conflict Prevention:** Real-time checks ensure a professor is never assigned to two classes simultaneously.
* **Professor Availability (Days Off):** The system respects specific days off (e.g., "Fridays") and will automatically assign a substitute or a "Self Study" period.
* **Save & Load Configurations:** Save multiple semesters (e.g., "Fall 2026") directly to the database and instantly switch between them using the control dashboard.
* **Enterprise Security:** The entire application is locked behind a robust Spring Security authentication system.
* **One-Click Exports:** Download schedules as a formatted CSV (for Microsoft Excel) or a high-quality landscape PDF.
* **Modern UI/UX:** Responsive Bootstrap 5 design with automatic color-coding for lectures vs. study halls, and sleek glass-morphism loading animations.

---

## 🛠️ Tech Stack

**Backend:**
* Java 17
* Spring Boot (Web, Data JPA, Security)
* Maven

**Database:**
* MySQL (Hosted on Clever Cloud)

**Frontend:**
* HTML5, CSS3, Vanilla JavaScript
* Bootstrap 5
* html2pdf.js (For PDF rendering)

**Deployment:**
* Render (Web Service)

---
