# Hiring Service

## What This Service Does

Hiring Service manages the application lifecycle after a candidate chooses to apply.

At a high level, it:

- Accepts job applications
- Stores and serves submitted applications
- Enriches applications with AI-based evaluation asynchronously

## Main Endpoints

- POST /api/job-applications
  - Purpose: submit a new application or update an existing one
  - Behavior: accepts immediately, then triggers downstream evaluation work

- GET /api/job-applications?candidateId=...
  - Purpose: show a candidate all applications they have submitted

- GET /api/job-applications?jobId=...
  - Purpose: show all applications received for a specific job

## Background Processing

After submission, the service publishes an internal event and processes AI scoring asynchronously.
This keeps submission fast while still adding match insights to application records.

## Operational Note

By default this service uses port 8083 in the current config, which overlaps with career-advisor.
If running both at once, change one of the service ports.

## Run

- Start with: ./mvnw spring-boot:run
