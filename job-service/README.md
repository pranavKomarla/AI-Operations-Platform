# Job Service

## What This Service Does

Job Service manages and serves the job catalog.
It is the primary source for job listings used by career-advisor and hiring-service.

At a high level, it supports:

- Looking up a single job
- Looking up multiple jobs at once
- Searching jobs by skill keywords

## Main Endpoints

- GET /api/jobs/{id}
  - Purpose: fetch one job by id

- GET /api/jobs?ids=...
  - Purpose: fetch multiple jobs in one request
  - Used by: career-advisor for job comparison and by hiring-service for application enrichment

- GET /api/jobs?skills=...
  - Purpose: find jobs matching candidate skills
  - Used by: career-advisor job recommendation flow

## Run

- Start with: ./mvnw spring-boot:run
