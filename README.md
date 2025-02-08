# Factory Dashboard

A real-time production monitoring dashboard with React frontend and Spring Boot backend, connected via WebSocket for live updates.

## What it does

Simulates a factory floor with production lines showing:
- **Live status** — running, idle, maintenance, error
- **Throughput metrics** — units produced per hour
- **Alert feed** — real-time notifications when lines go down

The backend simulates production events every 3 seconds. The frontend renders them instantly via WebSocket (STOMP over SockJS).

## Architecture

```
React (TypeScript)  <--WebSocket-->  Spring Boot API  -->  PostgreSQL
       |                                   |
   Dashboard UI                    Event Simulator
   (live metrics,                  (generates production
    line status,                    events every few seconds)
    alert feed)
```

## Tech Stack

- **Frontend:** React 18, TypeScript, STOMP.js, SockJS
- **Backend:** Java 17, Spring Boot 3.2, Spring WebSocket, JPA
- **Database:** PostgreSQL 16
- **Infrastructure:** Docker Compose

## Running Locally

```bash
docker compose up
```

Or run separately:

```bash
# Backend
cd backend && ./gradlew bootRun

# Frontend
cd frontend && npm install && npm start
```

- Frontend: http://localhost:3000
- Backend API: http://localhost:8080/api/lines

## Design Decisions

- **WebSocket over polling** — sub-second latency for status changes
- **Server-side simulation** — demonstrates the full event pipeline without needing real PLCs
- **Simple domain model** — focuses on the real-time architecture pattern, not business complexity
