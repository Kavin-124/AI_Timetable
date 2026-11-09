# 🎓 AI Timetable Generator

A full-stack college scheduling system built with Java 21, Spring Boot, and MySQL. It automates timetable generation, checks professor scheduling conflicts, manages availability, and exports schedules.

**Live Demo:** [AI Timetable Generator](https://ai-timetable-ph11.onrender.com)

> The demo requires valid credentials configured by the project owner. Credentials are not published in this repository.

## Features

- Automated timetable generation using a rotational balancing algorithm.
- Conflict checks to prevent a professor from being assigned to overlapping classes.
- Professor day-off handling, including substitute or self-study periods.
- Save and load semester configurations from MySQL.
- CSV and landscape PDF timetable exports.
- Responsive Bootstrap 5 interface.

## Technology

- **Backend:** Java 21, Spring Boot, Spring Web, Spring Data JPA, Spring Security, Maven
- **Database:** MySQL
- **Frontend:** HTML5, CSS3, JavaScript, Bootstrap 5
- **Deployment:** Render

## Run locally

1. Install Java 21 and MySQL.
2. Create a database for the application.
3. Configure the environment variables below in your IDE or shell.
4. Run the application with the Maven wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.`\mvnw.cmd spring-boot:run
```

### Required environment variables

| Variable | Purpose |
|---|---|
| `APP_ADMIN_USERNAME` | Login username |
| `APP_ADMIN_PASSWORD` | Strong login password |
| `APP_DB_URL` | JDBC URL, e.g. `jdbc:mysql://localhost:3306/timetable` |
| `APP_DB_USERNAME` | Database username |
| `APP_DB_PASSWORD` | Database password |
| `PORT` | Optional server port (defaults to 8080) |

Do not commit real credentials or place them in source files. Configure secrets in your local environment or hosting provider's secret/environment-variable settings.

## Project status

This repository contains the application source and Maven build configuration. Deployment availability and runtime behaviour depend on the environment variables and database service configured by the maintainer.
